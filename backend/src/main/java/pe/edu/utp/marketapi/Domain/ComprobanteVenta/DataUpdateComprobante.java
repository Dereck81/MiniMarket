package pe.edu.utp.marketapi.Domain.ComprobanteVenta;

import jakarta.validation.constraints.NotNull;

public record DataUpdateComprobante(
        @NotNull Long idComprobante,
        Long idPedido,
        Double total
) {
}
