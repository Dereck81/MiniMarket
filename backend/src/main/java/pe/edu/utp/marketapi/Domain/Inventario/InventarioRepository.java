package pe.edu.utp.marketapi.Domain.Inventario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pe.edu.utp.marketapi.Domain.Categoria.Categoria;

import java.util.List;

public interface InventarioRepository extends JpaRepository<Inventario, Long> {
    @Query("""

            SELECT i FROM Inventario i
            WHERE i.producto.nombre LIKE %:nombre%
            """)
    List<Inventario> findByNombre(String nombre);

    @Query("""
            SELECT i FROM Inventario i
            WHERE i.producto.categoria = :categoria
        """)
    List<Inventario> findByCategory(Categoria categoria);
}
