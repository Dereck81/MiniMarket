package pe.edu.utp.marketapi.Domain.Pago;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.edu.utp.marketapi.Domain.Pedido.Pedido;

import java.util.Optional;

public interface PagoRepository extends JpaRepository<Pago, Long> {
    Optional<Pago> findByPedido(@NotNull Pedido id);

    @Query("""
            SELECT p
            FROM Pago p
            WHERE p.pedido.idPedido = :idPedido
    """)
    Optional<Pago> findByPedidoId(Long idPedido);

    @Query("""
            SELECT COUNT(p)
            FROM Pago p
            WHERE p.metodoPago.idMetodoPago = :idMetodoPago
    """)
    int countByMetodoPago(Long idMetodoPago);

    @Query("""
        SELECT SUM(p.montoPagado)
        FROM Pago p
        WHERE FUNCTION('MONTH', p.fechaPago) = :mes
    """)
    Double sumTotalVentasByMes(@Param("mes") Integer mes);

}
