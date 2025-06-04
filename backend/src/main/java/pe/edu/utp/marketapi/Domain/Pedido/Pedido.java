package pe.edu.utp.marketapi.Domain.Pedido;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import pe.edu.utp.marketapi.Domain.Usuario.Usuario;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pedido")
    private Long idPedido;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @Column(name = "fecha_pedido")
    private Timestamp fechaPedido;

    private double total;

    private boolean estado = true;

    public Pedido(DataRegisterPedido data){
        this.usuario = Usuario.builder().idUsuario(data.idUsuario()).build();
        this.fechaPedido = new Timestamp(System.currentTimeMillis());
        this.estado = true;
    }

    public void updatePedido(DataUpdatePedido data){
        if (data.idUsuario() != null) this.usuario = Usuario.builder().idUsuario(data.idUsuario()).build();
        if (data.total() != null) this.total = data.total();
    }
}
