package pe.edu.utp.marketapi.Domain.Producto;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.utp.marketapi.Domain.Categoria.Categoria;

import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    List<Producto> findByNombre(String nombre);

    boolean existsByNombre(String nombre);

    List<Producto> findByCategoria(Categoria id);
}
