package pe.edu.utp.marketapi.Domain.DetallePedido;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import pe.edu.utp.marketapi.Domain.Inventario.Inventario;
import pe.edu.utp.marketapi.Domain.Pedido.Pedido;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "detalle_pedido")
public class DetallePedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle")
    private Long idDetalle;

    @ManyToOne
    @JoinColumn(name = "id_pedido")
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "id_inventario")
    private Inventario inventario;

    private int cantidad;

    @Column(name = "precio_unitario")
    private double precioUnitario;

    public DetallePedido(DataRegisterDetallePedido data){
        this.inventario = Inventario.builder().idInventario(data.idInventario()).build();
        this.cantidad = data.cantidad();
    }

    public void updateData(DataUpdateDetallePedido data){
        if (data.idPedido() != null) this.pedido = Pedido.builder().idPedido(data.idPedido()).build();
        if (data.idInventario() != null) this.inventario = Inventario.builder().idInventario(data.idInventario()).build();
        if (data.cantidad() != null) this.cantidad = data.cantidad();
    }
}

