package pe.edu.utp.marketapi.Domain.ComprobanteVenta;

import jakarta.validation.constraints.NotNull;

public record DataRegisterComprobante(
        @NotNull Long idPedido,
        @NotNull Double total
) {
}
