package pe.edu.utp.marketapi.Domain.Inventario;

import jakarta.validation.constraints.NotNull;

public record DataRegisterInventario (
        @NotNull Long idProducto,
        @NotNull Long idProveedor,
        @NotNull Integer stock,
        @NotNull Double precioVenta
) {
}
