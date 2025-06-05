package pe.edu.utp.marketapi.Domain.Categoria;

public record DataListCategoria(
        Long idCategoria,
        String nombre,
        String descripcion,
        boolean estado
) {
    public DataListCategoria(Categoria categoria){
        this(
            categoria.getIdCategoria(),
            categoria.getNombreCategoria(),
            categoria.getDescripcion(),
            categoria.getEstado()
        );
    }
}
