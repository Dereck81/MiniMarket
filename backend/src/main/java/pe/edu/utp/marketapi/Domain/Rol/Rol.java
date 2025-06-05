package pe.edu.utp.marketapi.Domain.Rol;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "roles")
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rol")
    private Long id;

    @Column(name = "nombre_rol")
    private String nombreRol;

    private Boolean estado = true;

    public Rol(DataRegisterRol data){
        this.nombreRol = data.nombreRol();
        this.estado = true;
    }

    public void update(DataUpdateRol data){
        if (data.nombreRol() != null) this.nombreRol = data.nombreRol();
    }
}
