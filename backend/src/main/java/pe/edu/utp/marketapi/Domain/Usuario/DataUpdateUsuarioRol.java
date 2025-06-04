package pe.edu.utp.marketapi.Domain.Usuario;

import jakarta.validation.constraints.NotNull;

public record DataUpdateUsuarioRol(
        @NotNull Long idUsuario,
        @NotNull Long idRol
) {
}
