package pe.edu.utp.marketapi.Domain.Usuario;

import jakarta.validation.constraints.NotNull;

public record DataRegisterUsuario(
        @NotNull String nombre,
        @NotNull String apellidos,
        @NotNull String email,
        @NotNull String contrasena,
        @NotNull Long rolId,
        @NotNull String dni,
        @NotNull String telefono,
        @NotNull String imagen
) {
}
