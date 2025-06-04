package pe.edu.utp.marketapi.Domain.ComprobanteVenta;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.utp.marketapi.Domain.Pedido.Pedido;

import java.util.Collection;
import java.util.List;

public interface ComprobanteVentaRepository extends JpaRepository<ComprobanteVenta, Long> {
    List<ComprobanteVenta> findByPedido(Pedido id);
}
