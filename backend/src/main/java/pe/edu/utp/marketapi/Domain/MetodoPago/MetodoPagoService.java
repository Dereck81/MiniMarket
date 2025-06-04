package pe.edu.utp.marketapi.Domain.MetodoPago;

import jakarta.transaction.Transactional;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MetodoPagoService {
    @Autowired
    private MetodoPagoRepository metodoPagoRepository;

    @Transactional
    public boolean save(DataRegisterMetodoPago metodoPago){
        MetodoPago newMetodoPago = new MetodoPago(metodoPago);
        metodoPagoRepository.save(newMetodoPago);
        return true;
    }

    @Transactional
    public boolean update(@NotNull DataUpdateMetodoPago metodoPago){
        if (metodoPagoRepository.existsById(metodoPago.idMetodoPago())){
            MetodoPago updateMetodoPago = metodoPagoRepository.getById(metodoPago.idMetodoPago());
            updateMetodoPago.updateData(metodoPago);
            metodoPagoRepository.save(updateMetodoPago);
            return true;
        }
        return false;
    }

    @Transactional
    public boolean delete(Long id){
        MetodoPago metodoPago = metodoPagoRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("MetodoPago not found"));
        metodoPago.setEstado(!metodoPago.getEstado());
        metodoPagoRepository.save(metodoPago);
        return true;
    }

    public List<DataListMetodoPago> getAll(){
        return metodoPagoRepository.findAll().stream()
                .map(DataListMetodoPago::new)
                .toList();
    }

    public DataListMetodoPago findById(Long id){
        return metodoPagoRepository.findById(id)
                .map(DataListMetodoPago::new)
                .orElse(null);
    }

    public void existsById(Long aLong) {
        Optional.of(metodoPagoRepository.findById(aLong))
                .orElseThrow(() -> new RuntimeException("MetodoPago not found"));
    }
}
