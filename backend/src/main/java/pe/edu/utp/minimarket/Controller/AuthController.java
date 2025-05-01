package pe.edu.utp.minimarket.Controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.utp.minimarket.Domain.Usuario.DataRegisterUsuario;
import pe.edu.utp.minimarket.Domain.Usuario.UsuarioService;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid DataRegisterUsuario data){
        boolean registrado = usuarioService.save(data);
        return new ResponseEntity<>(registrado, HttpStatus.CREATED);
    }
}
