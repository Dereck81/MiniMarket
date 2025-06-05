package pe.edu.utp.marketapi.Domain.Categoria;

import jakarta.transaction.Transactional;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {
    @Autowired
    private CategoriaRepository categoriaRepository;

    @Transactional
    public boolean save(DataRegisterCategoria categoria) {
        if (categoriaRepository.existsByNombreCategoria(categoria.nombre()))
            throw new RuntimeException("Categoria ya existe");

        categoriaRepository.save(new Categoria(categoria));
        return true;
    }

    @Transactional
    public boolean update(@NotNull DataUpdateCategoria categoria) {
        Categoria categoriaUpdate = categoriaRepository.findById(categoria.id())
                .orElseThrow(() -> new RuntimeException("Categoria no encontrada"));

        categoriaUpdate.updateCategoria(categoria);
        categoriaRepository.save(categoriaUpdate);
        return true;
    }

    @Transactional
    public boolean delete(Long id) {
        Categoria categoriaDelete = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria no encontrada"));
        categoriaDelete.setEstado(!categoriaDelete.getEstado());
        categoriaRepository.save(categoriaDelete);
        return true;
    }

    public List<DataListCategoria> getAll() {
        List<Categoria> categorias = categoriaRepository.findAll();
        return  categorias.stream().map(DataListCategoria::new).toList();
    }

    public List<DataListCategoria> findById(Long id) {
        return categoriaRepository.findById(id).stream().map(DataListCategoria::new).toList();
    }

    public List<DataListCategoria> findByNombre(String nombre) {
        return categoriaRepository.findByNombreCategoria(nombre).stream().map(DataListCategoria::new).toList();
    }

    public void existById(Long aLong) {
        Optional.of(categoriaRepository.existsById(aLong))
                .orElseThrow(() -> new RuntimeException("Categoria no encontrada"));
    }
}
