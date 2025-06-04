package pe.edu.utp.marketapi.Domain.Proveedor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProveedorRepository extends JpaRepository<Proveedor, Long> {

    boolean existsByNombreProveedorOrRuc (String nombre, String ruc);

    @Query("""
            SELECT p
            FROM Proveedor p
            WHERE p.nombreProveedor LIKE %:nombre%
    """  )
    List<Proveedor> findByNombreProveedor(String nombre);
}
