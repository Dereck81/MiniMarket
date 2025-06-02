package pe.edu.utp.marketapi.Controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.service.annotation.PutExchange;
import pe.edu.utp.marketapi.Domain.Rol.DataRegisterRol;
import pe.edu.utp.marketapi.Domain.Rol.DataUpdateRol;
import pe.edu.utp.marketapi.Domain.Rol.RolService;

@RestController
@RequestMapping("/roles")
public class RolController {
    @Autowired
    private RolService rolService;

    @PostMapping
    public ResponseEntity<?> register(@RequestBody @Valid DataRegisterRol data){
        return ResponseEntity.ok(rolService.save(data));
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody @Valid DataUpdateRol data){
        return ResponseEntity.ok(rolService.update(data));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        return ResponseEntity.ok(rolService.delete(id));
    }

    @GetMapping
    public ResponseEntity<?> findAll(){
        return ResponseEntity.ok(rolService.findAll());
    }
}
