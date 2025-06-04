package pe.edu.utp.marketapi.Domain.MetodoPago;

import jakarta.validation.constraints.NotNull;

public record DataUpdateMetodoPago(
        @NotNull Long idMetodoPago,
        String nombreMetodo
) {
}
