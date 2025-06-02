package pe.edu.utp.marketapi.Controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.utp.marketapi.Domain.Usuario.DataUpdateUsuarioRol;
import pe.edu.utp.marketapi.Infra.Security.DataLogin;
import pe.edu.utp.marketapi.Domain.Usuario.DataRegisterUsuario;
import pe.edu.utp.marketapi.Domain.Usuario.DataUpdateUsuario;
import pe.edu.utp.marketapi.Domain.Usuario.UsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<?> save(@RequestBody @Valid DataRegisterUsuario usuario){
        if (usuarioService.save(usuario)) return new ResponseEntity<>(HttpStatus.CREATED);
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping
    public ResponseEntity<?> getAll(){
        return new ResponseEntity<>(usuarioService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return new ResponseEntity<>(
                usuarioService.findById(id), HttpStatus.OK
        );
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody @Valid DataUpdateUsuario usuario){
        return new ResponseEntity<>(
                usuarioService.update(usuario), HttpStatus.OK
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        if (usuarioService.delete(id)) return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<?> getByEmail(@PathVariable String email) {
        return new ResponseEntity<>(
                usuarioService.findByEmail(email), HttpStatus.OK
        );
    }

    @PutMapping("/rol")
    public ResponseEntity<?> updateRol(@RequestBody @Valid DataUpdateUsuarioRol data){
        return new ResponseEntity<>(
                usuarioService.updateRol(data), HttpStatus.OK
        );
    }
}
