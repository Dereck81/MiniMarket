package pe.edu.utp.marketapi.Domain.Rol;

import jakarta.validation.constraints.NotNull;

public record DataUpdateRol(
        @NotNull Long id,
        String nombreRol
) {
}
