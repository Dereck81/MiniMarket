package pe.edu.utp.marketapi.Domain.ComprobanteVenta;

import jakarta.transaction.Transactional;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.utp.marketapi.Domain.Pedido.Pedido;
import pe.edu.utp.marketapi.Domain.Pedido.PedidoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ComprobanteVentaService {
    @Autowired
    private ComprobanteVentaRepository comprobanteVentaRepository;
    @Autowired
    private PedidoRepository pedidoRepository;

    @Transactional
    public boolean save(@NotNull DataRegisterComprobante comprobanteVenta){
        validIds(new ComprobanteVenta(comprobanteVenta));

        ComprobanteVenta newComprobanteVenta = new ComprobanteVenta(comprobanteVenta);
        comprobanteVentaRepository.save(newComprobanteVenta);
        return true;
    }

    @Transactional
    public boolean update(@NotNull DataUpdateComprobante comprobanteVenta) {
        ComprobanteVenta newComprobanteVenta = comprobanteVentaRepository.findById(comprobanteVenta.idComprobante())
                .orElseThrow(() -> new RuntimeException("Comprobante no encontrado"));

        if (comprobanteVenta.idPedido() != null) {
            Pedido pedido = pedidoRepository.findById(comprobanteVenta.idPedido())
                    .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
            newComprobanteVenta.setPedido(pedido);
        }
        newComprobanteVenta.updateData(comprobanteVenta);
        comprobanteVentaRepository.save(newComprobanteVenta);
        return true;
    }

    @Transactional
    public boolean delete(Long id) {
        ComprobanteVenta comprobanteVenta = comprobanteVentaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comprobante no encontrado"));

        comprobanteVentaRepository.delete(comprobanteVenta);
        return true;
    }

    public List<DataListComprobante> getAll() {
        return comprobanteVentaRepository.findAll()
                .stream()
                .map(DataListComprobante::new)
                .toList();
    }

    public DataListComprobante findById(Long id) {
        return comprobanteVentaRepository.findById(id)
                .map(DataListComprobante::new)
                .orElseThrow(() -> new RuntimeException("Comprobante no encontrado"));
    }

    public List<DataListComprobante> findByPedidoId(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
        return comprobanteVentaRepository.findByPedido(pedido)
                .stream()
                .map(DataListComprobante::new)
                .toList();
    }

    private void validIds(ComprobanteVenta comprobanteVenta) {
        Pedido pedido = pedidoRepository.findById(comprobanteVenta.getPedido().getIdPedido())
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
        Optional.of(comprobanteVentaRepository.findByPedido(pedido))
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
    }
}
