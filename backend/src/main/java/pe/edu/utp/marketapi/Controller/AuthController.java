package pe.edu.utp.marketapi.Controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.edu.utp.marketapi.Domain.Usuario.DataRegisterUsuario;
import pe.edu.utp.marketapi.Domain.Usuario.UsuarioService;
import pe.edu.utp.marketapi.Infra.Security.DataLogin;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid DataLogin dataLogin){
        return ResponseEntity.ok(usuarioService.authenticate(dataLogin));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid DataRegisterUsuario data){
        return new ResponseEntity<>(usuarioService.registerLogin(data), HttpStatus.CREATED);
    }
}
