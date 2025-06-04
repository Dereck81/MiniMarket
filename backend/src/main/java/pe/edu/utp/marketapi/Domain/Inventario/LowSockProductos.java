package pe.edu.utp.marketapi.Domain.Inventario;

public record LowSockProductos(
        Long id,
        String nombre,
        Integer stock,
        String categoria,
        String proveedor
) {
}
