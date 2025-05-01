package pe.edu.utp.minimarket.Domain.Usuario;

import jakarta.transaction.Transactional;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.utp.minimarket.Domain.Rol.Rol;
import pe.edu.utp.minimarket.Domain.Rol.RolRepository;
import java.util.List;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    @Transactional
    public boolean save(DataRegisterUsuario usuario){
        existUsuario(usuario);
        Usuario user = new Usuario(usuario);
        usuarioRepository.save(user);
        return true;
    }

    @Transactional
    public boolean update(@NotNull DataUpdateUsuario usuario){
        Usuario usuarioToUpdate = usuarioRepository.findById(usuario.id())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        usuarioToUpdate.update(usuario);
        usuarioToUpdate = usuarioRepository.save(usuarioToUpdate);

        Rol rol = rolRepository.findById(usuarioToUpdate.getRol().getId())
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        usuarioToUpdate.setRol(rol);

        return true;
    }

    @Transactional
    public boolean delete(Long id){
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        usuario.setEstado(!usuario.getEstado());
        usuarioRepository.save(usuario);
        return true;
    }

    public DataListUsuario findById(Long id){
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return new DataListUsuario(usuario);
    }

    public DataListUsuario findByEmail(String email){
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return new DataListUsuario(usuario);
    }

    public List<DataListUsuario> getAll(){
        return usuarioRepository.findAll()
                .stream()
                .map(DataListUsuario::new)
                .toList();
    }

    public void existById(Long id){
        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("Usuario no encontrado");
        }
    }

    public DataListUsuario updateRol(DataUpdateUsuarioRol dni){
        Usuario usuario = usuarioRepository.findById(dni.idUsuario())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Rol rol = rolRepository.findById(dni.idRol())
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

        usuario.setRol(rol);
        usuario = usuarioRepository.save(usuario);
        return new DataListUsuario(usuario);
    }

    private void existUsuario(DataRegisterUsuario usuario){
        if (usuarioRepository.existsByDniOrEmail(usuario.dni(), usuario.email())) {
            throw new RuntimeException("Correo o Dni ya registrado");
        }
    }
}
