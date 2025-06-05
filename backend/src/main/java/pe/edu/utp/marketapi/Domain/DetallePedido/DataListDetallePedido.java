package pe.edu.utp.marketapi.Domain.DetallePedido;

import pe.edu.utp.marketapi.Domain.Inventario.DataListInventario;
import pe.edu.utp.marketapi.Domain.Pedido.DataListPedido;

public record DataListDetallePedido (
        Long idDetalle,
        DataListPedido pedido,
        DataListInventario inventario,
        int cantidad,
        double precioUnitario
) {
    public DataListDetallePedido(DetallePedido detallePedido){
        this(
                detallePedido.getIdDetalle(),
                detallePedido.getPedido() != null ? new DataListPedido(detallePedido.getPedido()) : null,
                detallePedido.getInventario() != null ? new DataListInventario(detallePedido.getInventario()) : null,
                detallePedido.getCantidad(),
                detallePedido.getPrecioUnitario()
        );
    }
}
