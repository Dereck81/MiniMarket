package pe.edu.utp.minimarket.Domain.Rol;

import jakarta.validation.constraints.NotNull;

public record DataRegisterRol (
        @NotNull String nombreRol
) {
}
