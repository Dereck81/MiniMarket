package pe.edu.utp.marketapi.Domain.Pedido;

import pe.edu.utp.marketapi.Domain.DetallePedido.DataListDetallePedido;
import pe.edu.utp.marketapi.Domain.Pago.DataListPago;

import java.util.List;

public record DataResponsePedido (
        DataListPedido pedido,
        List<DataListDetallePedido> detallePedido,
        DataListPago pago
) {
}
