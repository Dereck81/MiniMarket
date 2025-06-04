package pe.edu.utp.marketapi.Domain.DetallePedido;

import jakarta.validation.constraints.NotNull;

public record DataRegisterDetallePedido(
        @NotNull Long idInventario,
        @NotNull Integer cantidad
) {
    public DataRegisterDetallePedido(DataUpdateDetallePedido detalle) {
        this(detalle.idInventario(), detalle.cantidad());
    }
}
