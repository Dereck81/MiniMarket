package pe.edu.utp.marketapi.Domain.Producto;


import jakarta.validation.constraints.NotNull;

public record DataRegisterProducto(
        @NotNull Long idCategoria,
        @NotNull String nombre,
        @NotNull String descripcion,
        @NotNull String imagen
) {
}
