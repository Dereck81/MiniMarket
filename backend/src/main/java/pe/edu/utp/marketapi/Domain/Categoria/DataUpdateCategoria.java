package pe.edu.utp.marketapi.Domain.Categoria;

import jakarta.validation.constraints.NotNull;

public record DataUpdateCategoria(
        @NotNull(message = "Id no puede ser nul")  Long id,
        String nombre,
        String descripcion
) {
}
