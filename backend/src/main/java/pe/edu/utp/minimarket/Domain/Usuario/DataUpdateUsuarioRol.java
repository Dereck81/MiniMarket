package pe.edu.utp.minimarket.Domain.Usuario;

import jakarta.validation.constraints.NotNull;

public record DataUpdateUsuarioRol(
        @NotNull Long idUsuario,
        @NotNull Long idRol
) {
}
