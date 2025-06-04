package pe.edu.utp.marketapi.Domain.Pedido;

import jakarta.transaction.Transactional;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.utp.marketapi.Domain.DetallePedido.*;
import pe.edu.utp.marketapi.Domain.Inventario.Inventario;
import pe.edu.utp.marketapi.Domain.Inventario.InventarioRepository;
import pe.edu.utp.marketapi.Domain.MetodoPago.MetodoPago;
import pe.edu.utp.marketapi.Domain.MetodoPago.MetodoPagoRepository;
import pe.edu.utp.marketapi.Domain.Pago.DataListPago;
import pe.edu.utp.marketapi.Domain.Pago.Pago;
import pe.edu.utp.marketapi.Domain.Pago.PagoRepository;
import pe.edu.utp.marketapi.Domain.Usuario.Usuario;
import pe.edu.utp.marketapi.Domain.Usuario.UsuarioRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class PedidoService {
    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private DetallePedidoService detallePedidoService;
    @Autowired
    private InventarioRepository inventarioRepository;
    @Autowired
    private PagoRepository pagoRepository;
    @Autowired
    private MetodoPagoRepository metodoPagoRepository;
    @Autowired
    private DetallePedidoRepository detallePedidoRepository;

    @Transactional
    public boolean save(@NotNull DataRequestRegisterPedido dataRequestPedido) {
        Pedido pedido = new Pedido(dataRequestPedido.pedido());
        pedidoRepository.save(pedido);

        double total = manejarDetallesRegistro(dataRequestPedido.detallePedido(), pedido);
        pedido.setTotal(total);
        pedidoRepository.save(pedido);

        registrarPago(dataRequestPedido.idModoPago(), pedido);
        return true;
    }

    @Transactional
    public boolean update(@NotNull DataRequestUpdatePedido dataUpdatePedido) {
        Pedido pedido = pedidoRepository.findById(dataUpdatePedido.pedido().idPedido())
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));

        if (dataUpdatePedido.pedido().idUsuario() != null) {
            Usuario usuario = usuarioRepository.findById(dataUpdatePedido.pedido().idUsuario())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            pedido.setUsuario(usuario);
        }
        Pago pago = pagoRepository.findByPedidoId(pedido.getIdPedido())
                .orElseThrow(() -> new RuntimeException("Pago no encontrado"));

        MetodoPago metodoPago = metodoPagoRepository.findById(dataUpdatePedido.idModoPago())
                .orElseThrow(() -> new RuntimeException("Metodo de pago no encontrado"));

        pago.setMetodoPago(metodoPago);
        pagoRepository.save(pago);

        revertirStockPedido(pedido);

        double total = manejarDetallesActualizacion(dataUpdatePedido.detallePedido(), pedido);
        pedido.setTotal(total);

        pedidoRepository.save(pedido);

        actualizarPago(pedido);
        return true;
    }

    private double manejarDetallesRegistro(List<DataRegisterDetallePedido> detalles, Pedido pedido) {
        double total = 0.0;
        for (DataRegisterDetallePedido detalle : detalles) {
            Inventario inventario = inventarioRepository.findById(detalle.idInventario())
                    .orElseThrow(() -> new RuntimeException("Inventario no encontrado"));

            if (inventario.getStock() < detalle.cantidad()) {
                throw new RuntimeException("Stock insuficiente para el producto " + inventario.getProducto().getNombre());
            }

            DetallePedido detallePedido = new DetallePedido(detalle);
            detallePedido.setPedido(pedido);
            detallePedido.setPrecioUnitario(detalle.cantidad() * inventario.getPrecioVenta());
            detallePedidoService.save(detallePedido);

            total += detallePedido.getPrecioUnitario();

            inventario.setStock(inventario.getStock() - detalle.cantidad());
            inventarioRepository.save(inventario);
        }
        return total;
    }

    private double manejarDetallesActualizacion(List<DataUpdateDetallePedido> detalles, Pedido pedido) {
        double total = 0.0;

        for (DataUpdateDetallePedido detalle : detalles) {
            DetallePedido detallePedido;

            if (detalle.idDetalle() == null) {
                detallePedido = new DetallePedido(new DataRegisterDetallePedido(detalle));
                detallePedido.setPedido(pedido);
            } else {
                detallePedido = detallePedidoRepository.findById(detalle.idDetalle())
                        .orElseThrow(() -> new RuntimeException("Detalle del pedido no encontrado"));
            }

            Inventario inventario = inventarioRepository.findById(detalle.idInventario())
                    .orElseThrow(() -> new RuntimeException("Inventario no encontrado"));

            if (inventario.getStock() < detalle.cantidad()) {
                throw new RuntimeException("Stock insuficiente para el producto " + inventario.getProducto().getNombre());
            }

            detallePedido.setCantidad(detalle.cantidad());
            detallePedido.setPrecioUnitario(inventario.getPrecioVenta());

            detallePedidoService.save(detallePedido);

            total += detallePedido.getCantidad() * detallePedido.getPrecioUnitario();

            inventario.setStock(inventario.getStock() - detalle.cantidad());
            inventarioRepository.save(inventario);
        }

        return total;
    }

    private void revertirStockPedido(Pedido pedido) {
        for (DetallePedido detalle : detallePedidoRepository.findByPedido(pedido)) {
            Inventario inventario = inventarioRepository.findById(detalle.getInventario().getIdInventario())
                    .orElseThrow(() -> new RuntimeException("Inventario no encontrado"));

            inventario.setStock(inventario.getStock() + detalle.getCantidad());
            inventarioRepository.save(inventario);
        }
    }

    private void registrarPago(Long idModoPago, Pedido pedido) {
        MetodoPago metodoPago = metodoPagoRepository.findById(idModoPago)
                .orElseThrow(() -> new RuntimeException("Metodo de pago no encontrado"));

        Pago pago = new Pago(pedido.getIdPedido(), metodoPago.getIdMetodoPago(), pedido.getTotal());
        pagoRepository.save(pago);
    }

    private void actualizarPago(Pedido pedido) {
        Pago pago = pagoRepository.findByPedidoId(pedido.getIdPedido())
                .orElseThrow(() -> new RuntimeException("Pago no encontrado"));
        pago.setMontoPagado(pedido.getTotal());
        pagoRepository.save(pago);
    }

    @Transactional
    public boolean delete(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
        pedido.setEstado(!pedido.isEstado());
        pedidoRepository.save(pedido);
        return true;
    }

    public DataResponsePedido findById(@NotNull Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));

        List<DataListDetallePedido> detalles = detallePedidoService.findByPedidoId(pedido.getIdPedido());

        Pago pago = pagoRepository.findByPedidoId(pedido.getIdPedido())
                .orElseThrow(() -> new RuntimeException("Pago no encontrado"));

        DataListPago pagoDTO = new DataListPago(pago);
        return new DataResponsePedido(new DataListPedido(pedido), detalles, pagoDTO);
    }

    public List<DataResponsePedido> findByUserId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        List<Pedido> pedidos = pedidoRepository.findByUsuario(usuario);

        return pedidos.stream().map(pedido -> {
            List<DataListDetallePedido> detalles = detallePedidoService.findByPedidoId(pedido.getIdPedido());

            Pago pago = pagoRepository.findByPedidoId(pedido.getIdPedido())
                    .orElseThrow(() -> new RuntimeException("Pago no encontrado"));

            DataListPago pagoDTO = new DataListPago(pago);

            return new DataResponsePedido(new DataListPedido(pedido), detalles, pagoDTO);
        }).toList();
    }

    public void existsById(Long id) {
        pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
    }

    public List<DataResponsePedido> findAll() {
        List<DataResponsePedido> pedidosResponse = new ArrayList<>();

        List<DataListPedido> pedidos = pedidoRepository.findAll()
                .stream().map(DataListPedido::new).toList();

        pedidos.forEach(pedido -> {
            List<DataListDetallePedido> detalles = detallePedidoService.findByPedidoId(pedido.idPedido());

            Pago pago = pagoRepository.findByPedidoId(pedido.idPedido())
                    .orElseThrow(() -> new RuntimeException("Pago no encontrado"));

            DataListPago pagoDTO = new DataListPago(pago);

            pedidosResponse.add(new DataResponsePedido(pedido, detalles, pagoDTO));
        });

        return pedidosResponse;
    }

}
