package pe.edu.utp.marketapi.Domain.Categoria;

import jakarta.validation.constraints.NotNull;

public record DataRegisterCategoria(
    @NotNull String nombre,
    @NotNull String descripcion
) {
}
