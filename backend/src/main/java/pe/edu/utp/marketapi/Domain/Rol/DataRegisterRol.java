package pe.edu.utp.marketapi.Domain.Rol;

import jakarta.validation.constraints.NotNull;

public record DataRegisterRol (
        @NotNull String nombreRol
) {
}
