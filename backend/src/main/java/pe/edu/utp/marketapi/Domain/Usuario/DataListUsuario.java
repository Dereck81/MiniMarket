package pe.edu.utp.marketapi.Domain.Usuario;


import pe.edu.utp.marketapi.Domain.Rol.DataListRol;

import java.sql.Timestamp;

public record DataListUsuario (
        Long id,
        String nombre,
        String apellidos,
        String email,
        DataListRol rol,
        String dni,
        String telefono,
        String imagen,
        Timestamp fechaRegistro,
        Timestamp fechaActualizar,
        Boolean estado
) {
    public DataListUsuario(Usuario usuario){
        this(
                usuario.getIdUsuario(),
                usuario.getNombres(),
                usuario.getApellidos(),
                usuario.getEmail(),
                new DataListRol(usuario.getRol()),
                usuario.getDni(),
                usuario.getTelefono(),
                usuario.getImagen(),
                usuario.getFechaRegistro(),
                usuario.getFechaActualizar(),
                usuario.getEstado()
        );
    }
}
