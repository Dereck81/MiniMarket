package pe.edu.utp.marketapi.Domain.DetallePedido;

import jakarta.transaction.Transactional;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.utp.marketapi.Domain.Inventario.InventarioRepository;
import pe.edu.utp.marketapi.Domain.Pedido.Pedido;
import pe.edu.utp.marketapi.Domain.Pedido.PedidoRepository;
import pe.edu.utp.marketapi.Domain.Producto.Producto;
import pe.edu.utp.marketapi.Domain.Producto.ProductoRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DetallePedidoService {
    @Autowired
    private DetallePedidoRepository detallePedidoRepository;
    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    private InventarioRepository inventarioRepository;
    @Autowired
    private ProductoRepository productoRepository;

    @Transactional
    public boolean save(@NotNull DataRegisterDetallePedido detallePedido){
        validIds(new DetallePedido(detallePedido));

        DetallePedido newDetallePedido = new DetallePedido(detallePedido);
        detallePedidoRepository.save(newDetallePedido);
        return true;
    }

    @Transactional
    public boolean save(@NotNull DetallePedido detallePedido){
        validIds(detallePedido);

        detallePedidoRepository.save(detallePedido);
        return true;
    }

    @Transactional
    public boolean update(@NotNull DataUpdateDetallePedido detallePedido){
        DetallePedido updateDetallePedido = detallePedidoRepository.findById(detallePedido.idDetalle())
                .orElseThrow(() -> new RuntimeException("Detalle de pedido no encontrado"));

        if (detallePedido.idPedido() != null || detallePedido.idInventario() != null){
            validIds(updateDetallePedido);
        }
        updateDetallePedido.updateData(detallePedido);
        detallePedidoRepository.save(updateDetallePedido);
        return true;
    }

    @Transactional
    public boolean delete(@NotNull Long id){
        DetallePedido deleteDetallePedido = detallePedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Detalle de pedido no encontrado"));
        detallePedidoRepository.delete(deleteDetallePedido);
        return true;
    }

    public List<DataListDetallePedido> findByPedidoId(@NotNull Long id){
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
        List<DetallePedido> detallePedido = detallePedidoRepository.findByPedido(pedido);
        return detallePedido.stream()
                .map(DataListDetallePedido::new)
                .toList();
    }

    public DataListDetallePedido findById(@NotNull Long id){
        DetallePedido detallePedido = detallePedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Detalle de pedido no encontrado"));
        return new DataListDetallePedido(detallePedido);
    }

    private void validIds(DetallePedido detallePedido) {
        DetallePedido newDetallePedido = Optional.ofNullable(detallePedido)
                .orElseThrow(() -> new RuntimeException("Detalle de pedido no encontrado"));
        Optional.of(pedidoRepository.findByUsuario(newDetallePedido.getPedido().getUsuario()))
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Optional.of(inventarioRepository.findById(newDetallePedido.getInventario().getIdInventario()))
                .orElseThrow(() -> new RuntimeException("Inventario no encontrado"));
    }

    public List<TopProductosVentas> getTopVendidos() {
        List<TopProductosVentas> response = new ArrayList<>();
        List<Producto> productos = productoRepository.findAll();
        for (Producto producto : productos) {
            response.add(
                    new TopProductosVentas(
                            producto.getNombre(),
                            detallePedidoRepository.countByProducto(producto.getIdProducto())
                    )
            );
        }
        return response.stream()
                .sorted((p1, p2) -> p2.cantidadVendida().compareTo(p1.cantidadVendida()))
                .limit(5)
                .toList();
    }
}
