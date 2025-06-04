package pe.edu.utp.marketapi.Domain.Rol;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolService {
    @Autowired
    private RolRepository rolRepository;

    @Transactional
    public DataListRol save(DataRegisterRol data){
        Rol rol = new Rol(data);
        rol = rolRepository.save(rol);
        return new DataListRol(rol);
    }

    @Transactional
    public DataListRol update(DataUpdateRol data){
        Rol rol = rolRepository.findById(data.id())
                .orElseThrow(() -> new RuntimeException("Rol not fount"));
        rol.update(data);
        rol = rolRepository.save(rol);
        return new DataListRol(rol);
    }

    @Transactional
    public boolean delete(Long id){
        Rol rol = rolRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rol not fount"));
        rol.setEstado(!rol.getEstado());
        rolRepository.save(rol);
        return true;
    }

    public List<DataListRol> findAll(){
        return rolRepository.findAll().stream()
                .map(DataListRol::new)
                .toList();
    }
}
