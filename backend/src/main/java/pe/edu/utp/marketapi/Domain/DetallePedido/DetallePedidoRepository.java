package pe.edu.utp.marketapi.Domain.DetallePedido;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pe.edu.utp.marketapi.Domain.Pedido.Pedido;

import java.util.List;

public interface DetallePedidoRepository extends JpaRepository<DetallePedido, Long> {
    List<DetallePedido> findByPedido(@NotNull Pedido id);

    @Query("""
            SELECT sum(d.cantidad)
            FROM DetallePedido d
            WHERE d.inventario.producto.idProducto = :idProducto
    """)
    int countByProducto(Long idProducto);
}
