package pe.edu.utp.marketapi.Domain.Usuario;

import jakarta.validation.constraints.NotNull;

public record DataUpdateUsuario(
        @NotNull Long id,
        String nombre,
        String apellidos,
        String email,
        String contrasena,
        Long rol,
        String dni,
        String telefono,
        String imagen
) {
}
