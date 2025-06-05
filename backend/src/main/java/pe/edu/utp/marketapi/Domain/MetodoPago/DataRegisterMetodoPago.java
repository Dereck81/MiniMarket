package pe.edu.utp.marketapi.Domain.MetodoPago;

import jakarta.validation.constraints.NotNull;

public record DataRegisterMetodoPago(
        @NotNull String nombreMetodo
) {
}
