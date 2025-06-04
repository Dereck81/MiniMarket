package pe.edu.utp.marketapi.Domain.Categoria;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    List<Categoria> findByNombreCategoria(String nombre);

    boolean existsByNombreCategoria(@NotNull String nombre);
}
