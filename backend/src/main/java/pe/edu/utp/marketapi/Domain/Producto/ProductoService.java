package pe.edu.utp.marketapi.Domain.Producto;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.utp.marketapi.Domain.Categoria.Categoria;
import pe.edu.utp.marketapi.Domain.Categoria.CategoriaRepository;
import pe.edu.utp.marketapi.Domain.Inventario.InventarioRepository;

import java.util.List;

@Service
public class ProductoService {
    @Autowired
    private ProductoRepository productoRepository;
    @Autowired
    private CategoriaRepository categoriaRepository;
    @Autowired
    private InventarioRepository inventarioRepository;

    @Transactional
    public boolean save(DataRegisterProducto producto){
        if (productoRepository.existsByNombre(producto.nombre()))
            throw new RuntimeException("Producto ya existe");

        categoriaRepository.findById(producto.idCategoria())
                .orElseThrow(() -> new RuntimeException("Categoria no encontrada"));

        Producto newProducto = new Producto(producto);
        productoRepository.save(newProducto);
        return true;
    }

    @Transactional
    public boolean update(DataUpdateProducto producto){
        Producto newProducto = productoRepository.findById(producto.id())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        newProducto.updateProducto(producto);
        productoRepository.save(newProducto);
        return true;
    }

    @Transactional
    public boolean delete(Long id){
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        producto.setEstado(!producto.getEstado());
        productoRepository.save(producto);
        return true;
    }

    public DataListProducto findById(Long id){
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        return new DataListProducto(producto);
    }

    public List<DataListProducto> getAll(){
        List<Producto> productos = productoRepository.findAll();
        return productos.stream()
                .map(DataListProducto::new)
                .toList();
    }

    public List<DataListProducto> findByNombre(String nombre){
        List<Producto> productos = productoRepository.findByNombre(nombre);
        return productos.stream()
                .map(DataListProducto::new)
                .toList();
    }

    public boolean existById(Long id){
        return id != null && !productoRepository.existsById(id);
    }

    public List<DataListProducto> findByCategoriaId(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria no encontrada"));
        List<Producto> productos = productoRepository.findByCategoria(categoria);
        return productos.stream()
                .map(DataListProducto::new)
                .toList();
    }
}
