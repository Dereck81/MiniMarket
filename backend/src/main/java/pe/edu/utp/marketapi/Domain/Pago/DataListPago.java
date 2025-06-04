package pe.edu.utp.marketapi.Domain.Pago;

import pe.edu.utp.marketapi.Domain.MetodoPago.DataListMetodoPago;
import pe.edu.utp.marketapi.Domain.Pedido.DataListPedido;
import pe.edu.utp.marketapi.Domain.Producto.DataListProducto;

public record DataListPago(
        Long idPago,
        DataListMetodoPago metodoPago,
        DataListPedido pedido,
        Double monto
) {
    public DataListPago(Pago pago){
        this(
                pago.getIdPago(),
                pago.getMetodoPago() != null ? new DataListMetodoPago(pago.getMetodoPago()) : null,
                pago.getPedido() != null ? new DataListPedido(pago.getPedido()) : null,
                pago.getMontoPagado()
        );
    }
}
