package pe.edu.utp.marketapi.Domain.Pedido;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.utp.marketapi.Domain.Usuario.Usuario;

import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByUsuario(Usuario usuario);
}
