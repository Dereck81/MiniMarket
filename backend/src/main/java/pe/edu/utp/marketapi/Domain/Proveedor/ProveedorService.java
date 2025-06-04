package pe.edu.utp.marketapi.Domain.Proveedor;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProveedorService {
    @Autowired
    private ProveedorRepository proveedorRepository;

    @Transactional
    public boolean save(DataRegisterProveedor proveedor){
        existProveedor(proveedor);

        proveedorRepository.save(new Proveedor(proveedor));
        return true;
    }

    @Transactional
    public boolean update(DataUpdateProveedor proveedor){
        Proveedor proveedorToUpdate = proveedorRepository.findById(proveedor.id())
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));

        proveedorToUpdate.updateProveedor(proveedor);
        proveedorRepository.save(proveedorToUpdate);
        return true;
    }

    @Transactional
    public boolean delete(Long id){
        Proveedor proveedor = proveedorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));
        proveedor.setEstado(!proveedor.getEstado());
        proveedorRepository.save(proveedor);
        return true;
    }

    public DataListProveedor  findById(Long id){
        Proveedor proveedor = proveedorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));
        return new DataListProveedor(proveedor);
    }

    public List<DataListProveedor> getAll(){
        return proveedorRepository.findAll()
                .stream()
                .map(DataListProveedor::new)
                .toList();
    }

    public List<DataListProveedor> findByNombre(String nombre){
        List<Proveedor> proveedors = proveedorRepository.findByNombreProveedor(nombre);
        return proveedors
                .stream()
                .map(DataListProveedor::new)
                .toList();
    }

    public boolean existById(Long id){
        return proveedorRepository.existsById(id);
    }

    private boolean existProveedor(DataRegisterProveedor proveedor){
        if (proveedorRepository.existsByNombreProveedorOrRuc(proveedor.nombreProveedor(), proveedor.ruc())){
            throw new RuntimeException("Proveedor ya existe");
        }
        return true;
    }
}
