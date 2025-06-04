package pe.edu.utp.marketapi.Domain.Proveedor;

import jakarta.validation.constraints.NotNull;

public record DataUpdateProveedor(
        @NotNull Long id,
        String nombreProveedor,
        String telefono,
        String email,
        String direccion,
        String ruc
) {
}
