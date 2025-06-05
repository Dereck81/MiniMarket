package pe.edu.utp.marketapi.Domain.Producto;

import jakarta.validation.constraints.NotNull;

public record DataUpdateProducto(
        @NotNull Long id,
        Long idCategoria,
        String nombre,
        String descripcion,
        String imagen
) {
}
