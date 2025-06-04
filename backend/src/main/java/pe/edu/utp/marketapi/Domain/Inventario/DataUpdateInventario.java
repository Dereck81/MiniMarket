package pe.edu.utp.marketapi.Domain.Inventario;

import jakarta.validation.constraints.NotNull;

public record DataUpdateInventario(
        @NotNull Long idInventario,
        Long idProducto,
        Long idProveedor,
        Integer stock,
        Double precioVenta
) {
}
