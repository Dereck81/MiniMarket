package pe.edu.utp.marketapi.Domain.Usuario;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import pe.edu.utp.marketapi.Domain.Rol.Rol;
import pe.edu.utp.marketapi.Domain.Rol.RolRepository;
import pe.edu.utp.marketapi.Infra.Security.DataLogin;
import pe.edu.utp.marketapi.Infra.Security.DataResponseLogin;
import pe.edu.utp.marketapi.Infra.Security.TokenService;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenService tokenService;
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
    public DataResponseLogin update(@NotNull DataUpdateUsuario usuario){
        Usuario usuarioToUpdate = usuarioRepository.findById(usuario.id())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        usuarioToUpdate.update(usuario);
        usuarioToUpdate = usuarioRepository.save(usuarioToUpdate);
        Rol rol = rolRepository.findById(usuarioToUpdate.getRol().getId())
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        usuarioToUpdate.setRol(rol);
        return new DataResponseLogin(new DataListUsuario(usuarioToUpdate), tokenService.generateToken(usuarioToUpdate));
    }

    @Transactional
    public boolean delete(Long id){
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        usuario.setEstado(!usuario.getEstado());
        usuarioRepository.save(usuario);
        return true;
    }

    @Transactional
    public DataResponseLogin registerLogin(DataRegisterUsuario usuario){
        existUsuario(usuario);

        Usuario user = new Usuario(usuario);
        Rol rol = rolRepository.findByNombreRol("CLIENTE")
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        user.setRol(rol);
        user = usuarioRepository.save(user);

        System.out.println(user);

        var token = tokenService.generateToken(user);
        return new DataResponseLogin(new DataListUsuario(user), token);
    }

    public DataResponseLogin authenticate(@Valid @NotNull DataLogin data) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var authentication = authenticationManager.authenticate(authenticationToken);
        var user = (Usuario) authentication.getPrincipal();

        Usuario usuario = usuarioRepository.findByEmailWithRol(data.email())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!usuario.getEstado()) throw new RuntimeException("Usuario deshabilitado");

        System.out.println(usuario);
        var token = tokenService.generateToken(user);
        return new DataResponseLogin(new DataListUsuario(usuario), token);
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
        Optional.of(usuarioRepository.existsById(id)).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
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
        if (usuarioRepository.existsByDniOrEmail(usuario.dni(), usuario.email())) throw new RuntimeException("Correo o Dni ya registrado");
    }
}
