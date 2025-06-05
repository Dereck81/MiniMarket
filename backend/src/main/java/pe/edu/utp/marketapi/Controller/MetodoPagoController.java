package pe.edu.utp.marketapi.Controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.utp.marketapi.Domain.MetodoPago.DataRegisterMetodoPago;
import pe.edu.utp.marketapi.Domain.MetodoPago.DataUpdateMetodoPago;
import pe.edu.utp.marketapi.Domain.MetodoPago.MetodoPagoService;

@RestController
@RequestMapping("/metodos-pago")
public class MetodoPagoController {
    @Autowired
    private MetodoPagoService metodoPagoService;

    @PostMapping
    public ResponseEntity<?> save(@RequestBody @Valid DataRegisterMetodoPago metodoPago){
        if (metodoPagoService.save(metodoPago)) return new ResponseEntity<>(HttpStatus.CREATED);
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping
    public ResponseEntity<?> getAll(){
        return new ResponseEntity<>(metodoPagoService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return new ResponseEntity<>(
                metodoPagoService.findById(id), HttpStatus.OK
        );
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody @Valid DataUpdateMetodoPago metodoPago){
        if (metodoPagoService.update(metodoPago)) return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        if (metodoPagoService.delete(id)) return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
