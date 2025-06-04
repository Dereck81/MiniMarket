package pe.edu.utp.marketapi.Domain.Usuario;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import pe.edu.utp.marketapi.Domain.Rol.Rol;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "usuarios")
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long idUsuario;

    @Column(name = "nombres", length = 15, nullable = false)
    private String nombres;

    @Column(name = "apellidos", length = 15, nullable = false)
    private String apellidos;

    @Column(nullable = false, length = 25, unique = true)
    private String email;

    @Column(nullable = false, length = 100, name = "contraseña")
    private String contrasena;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_rol", nullable = false)
    private Rol rol;

    @Column(length = 8)
    private String dni;

    @Column(length = 9)
    private String telefono;

    private String imagen;

    @Column(name = "fecha_registro", nullable = false, updatable = false)
    private Timestamp fechaRegistro;

    @Column(name = "fecha_actualizar", nullable = false)
    private Timestamp fechaActualizar;

    private Boolean estado = true;

    public Usuario(DataRegisterUsuario data) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        this.nombres = data.nombre();
        this.apellidos = data.apellidos();
        this.email = data.email();
        this.contrasena = encoder.encode(data.contrasena());
        this.rol = Rol.builder().id(data.rolId()).build();
        this.dni = data.dni();
        this.telefono = data.telefono();
        this.fechaRegistro = new Timestamp(System.currentTimeMillis());
        this.imagen = data.imagen();
        this.estado = true;
    }

    public void update(DataUpdateUsuario data) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (data.nombre() != null) this.nombres = data.nombre();
        if (data.apellidos() != null) this.apellidos = data.apellidos();
        if (data.email() != null) this.email = data.email();
        if (data.contrasena() != null) this.contrasena = encoder.encode(data.contrasena());
        if (data.rol() != null) this.rol = Rol.builder().id(data.rol()).build();
        if (data.dni() != null) this.dni = data.dni();
        if (data.telefono() != null) this.telefono = data.telefono();
        if (data.imagen() != null) this.imagen = data.imagen();
        this.fechaActualizar = new Timestamp(System.currentTimeMillis());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE-" + rol.getNombreRol().toUpperCase())); // Dinámico según el rol
    }

    @Override
    public String getPassword() {
        return this.contrasena;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
