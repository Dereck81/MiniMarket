package pe.edu.utp.marketapi.Domain.Inventario;

import pe.edu.utp.marketapi.Domain.Producto.DataListProducto;
import pe.edu.utp.marketapi.Domain.Proveedor.DataListProveedor;

public record DataListInventario(
        Long idInventario,
        DataListProducto producto,
        DataListProveedor proveedor,
        Integer stock,
        double precioVenta,
        String fechaMovimiento
) {

    public  DataListInventario (Inventario inventario) {
        this(
                inventario.getIdInventario(),
                inventario.getProducto() != null ? new DataListProducto(inventario.getProducto()) : null,
                inventario.getProveedor() != null ? new DataListProveedor(inventario.getProveedor()) : null,
                inventario.getStock(),
                inventario.getPrecioVenta(),
                inventario.getFechaMovimiento().toString()
        );
    }
}
