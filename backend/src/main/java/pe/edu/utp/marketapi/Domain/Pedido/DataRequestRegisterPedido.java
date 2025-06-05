package pe.edu.utp.marketapi.Domain.Pedido;

import jakarta.validation.constraints.NotNull;
import pe.edu.utp.marketapi.Domain.DetallePedido.DataRegisterDetallePedido;

import java.util.List;

public record DataRequestRegisterPedido(
        @NotNull  DataRegisterPedido pedido,
        List<DataRegisterDetallePedido> detallePedido,
        @NotNull Long idModoPago
) {
}
