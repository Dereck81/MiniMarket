package pe.edu.utp.marketapi.Domain.Producto;

import pe.edu.utp.marketapi.Domain.Categoria.DataListCategoria;

public record DataListProducto(
        Long idProducto,
        String nombre,
        String descripcion,
        String imagen,
        DataListCategoria categoria,
        Boolean estado
) {
    public DataListProducto(Producto producto){
        this(
                (long) producto.getIdProducto(),
                producto.getNombre(),
                producto.getDescripcion(),
                producto.getImagen(),
                producto.getCategoria() == null ? null : new DataListCategoria(producto.getCategoria()),
                producto.getEstado()
        );
    }
}
