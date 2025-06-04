package pe.edu.utp.marketapi.Domain.Pedido;

import jakarta.validation.constraints.NotNull;
import pe.edu.utp.marketapi.Domain.DetallePedido.DataUpdateDetallePedido;

import java.util.List;

public record DataRequestUpdatePedido(
        @NotNull DataUpdatePedido pedido,
        @NotNull Long idModoPago,
        @NotNull List<DataUpdateDetallePedido> detallePedido
) {
}
