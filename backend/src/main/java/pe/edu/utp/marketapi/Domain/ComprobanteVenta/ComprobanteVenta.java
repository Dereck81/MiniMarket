package pe.edu.utp.marketapi.Domain.ComprobanteVenta;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import pe.edu.utp.marketapi.Domain.Pedido.Pedido;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "comprobante_venta")
public class ComprobanteVenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_comprobante")
    private Long idComprobante;

    @ManyToOne
    @JoinColumn(name = "id_pedido")
    private Pedido pedido;

    @Column(name = "fecha_emision")
    private Timestamp fechaEmision;

    private double total;

    public ComprobanteVenta(DataRegisterComprobante data){
        this.pedido = Pedido.builder().idPedido(data.idPedido()).build();
        this.fechaEmision = Timestamp.valueOf(LocalDateTime.now());
        this.total = data.total();
    }

    public void updateData(DataUpdateComprobante data){
        if (data.idPedido() != null) this.pedido = Pedido.builder().idPedido(data.idPedido()).build();
        if (data.total() != null) this.total = data.total();
    }
}
