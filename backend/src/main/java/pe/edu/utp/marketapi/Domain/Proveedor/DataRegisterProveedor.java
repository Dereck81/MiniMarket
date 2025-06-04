package pe.edu.utp.marketapi.Domain.Proveedor;

public record DataRegisterProveedor (
        String nombreProveedor,
        String telefono,
        String email,
        String direccion,
        String ruc
) {
}
