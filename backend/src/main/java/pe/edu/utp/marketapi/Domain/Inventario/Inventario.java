package pe.edu.utp.marketapi.Domain.Inventario;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import pe.edu.utp.marketapi.Domain.Producto.Producto;
import pe.edu.utp.marketapi.Domain.Proveedor.Proveedor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "inventarios")
public class Inventario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_inventario")
    private Long idInventario;

    @ManyToOne
    @JoinColumn(name = "id_producto")
    private Producto producto;

    @ManyToOne
    @JoinColumn(name = "id_proveedor")
    private Proveedor proveedor;

    private int stock;

    @Column(name = "precio_venta")
    private double precioVenta;

    @Column(name = "fecha_movimiento")
    private Timestamp fechaMovimiento;

    public Inventario(DataRegisterInventario data){
        this.producto = Producto.builder().idProducto(data.idProducto()).build();
        this.proveedor = Proveedor.builder().idProveedor(data.idProveedor()).build();
        this.stock = data.stock();
        this.precioVenta = data.precioVenta();
        this.fechaMovimiento = new Timestamp(System.currentTimeMillis());
    }

    public void update(DataUpdateInventario data){
        if (data.idProducto() != null) this.producto = Producto.builder().idProducto(data.idProducto()).build();
        if (data.idProveedor() != null) this.proveedor = Proveedor.builder().idProveedor(data.idProveedor()).build();
        if (data.stock() != null) this.stock = data.stock();
        if (data.precioVenta() != null) this.precioVenta = data.precioVenta();
    }
}
