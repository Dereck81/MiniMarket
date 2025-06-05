package pe.edu.utp.marketapi.Domain.Producto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import pe.edu.utp.marketapi.Domain.Categoria.Categoria;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto")
    private Long idProducto;

    @Column(nullable = false, length = 30)
    private String nombre;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String descripcion;

    private String imagen;

    @ManyToOne
    @JoinColumn(name = "id_categoria", nullable = false)
    private Categoria categoria;

    private Boolean estado = true;

    public Producto(DataRegisterProducto data){
        this.nombre = data.nombre();
        this.descripcion = data.descripcion();
        this.imagen = data.imagen();
        this.categoria = Categoria.builder().idCategoria(data.idCategoria()).build();
        this.estado = true;
    }

    public void updateProducto(DataUpdateProducto data){
        if (data.idCategoria() != null) this.categoria = Categoria.builder().idCategoria(data.idCategoria()).build();
        if (data.nombre() != null) this.nombre = data.nombre();
        if (data.descripcion() != null) this.descripcion = data.descripcion();
        if (data.imagen() != null) this.imagen = data.imagen();
    }
}
