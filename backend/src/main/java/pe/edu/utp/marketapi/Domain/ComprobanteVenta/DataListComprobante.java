package pe.edu.utp.marketapi.Domain.ComprobanteVenta;

import pe.edu.utp.marketapi.Domain.Pedido.DataListPedido;
import pe.edu.utp.marketapi.Domain.Pedido.Pedido;

public record DataListComprobante(
        Long idComprobante,
        DataListPedido Pedido,
        String fechaEmision,
        double total
) {
    public DataListComprobante(ComprobanteVenta comprobanteVenta){
        this(
                comprobanteVenta.getIdComprobante(),
                comprobanteVenta.getPedido() != null ? new DataListPedido(comprobanteVenta.getPedido()) : null,
                comprobanteVenta.getFechaEmision().toString(),
                comprobanteVenta.getTotal()
        );
    }
}
