package pe.edu.utp.marketapi.Domain.Proveedor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "proveedores")
public class Proveedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_proveedor")
    private Long idProveedor;

    @Column(name = "nombre_proveedor", length = 15)
    private String nombreProveedor;

    @Column(length = 9)
    private String telefono;

    @Column(length = 25)
    private String email;

    @Column(length = 30)
    private String direccion;

    @Column(length = 30)
    private String ruc;

    private Boolean estado = true;

    public Proveedor( DataRegisterProveedor data){
        this.nombreProveedor = data.nombreProveedor();
        this.telefono = data.telefono();
        this.email = data.email();
        this.direccion = data.direccion();
        this.ruc = data.ruc();
        this.estado = true;
    }

    public void updateProveedor(DataUpdateProveedor data){
        if (data.nombreProveedor() != null) this.nombreProveedor = data.nombreProveedor();
        if (data.telefono() != null) this.telefono = data.telefono();
        if (data.email() != null) this.email = data.email();
        if (data.direccion() != null) this.direccion = data.direccion();
        if (data.ruc() != null) this.ruc = data.ruc();
    }
}
