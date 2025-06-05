package pe.edu.utp.marketapi.Domain.Proveedor;

public record DataListProveedor(
        Long idProveedor,
        String nombreProveedor,
        String telefono,
        String email,
        String direccion,
        String ruc,
        boolean estado
) {
    public DataListProveedor(Proveedor proveedor){
        this(
                (long) proveedor.getIdProveedor(),
                proveedor.getNombreProveedor(),
                proveedor.getTelefono(),
                proveedor.getEmail(),
                proveedor.getDireccion(),
                proveedor.getRuc(),
                proveedor.getEstado()
        );
    }
}
