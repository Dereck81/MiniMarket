package pe.edu.utp.minimarket.Domain.Rol;

import jakarta.validation.constraints.NotNull;

public record DataUpdateRol(
        @NotNull Long id,
        String nombreRol
) {
}
