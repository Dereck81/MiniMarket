package pe.edu.utp.marketapi.Controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.utp.marketapi.Domain.ComprobanteVenta.ComprobanteVentaService;
import pe.edu.utp.marketapi.Domain.ComprobanteVenta.DataRegisterComprobante;
import pe.edu.utp.marketapi.Domain.ComprobanteVenta.DataUpdateComprobante;

@RestController
@RequestMapping("/comprobantes")
public class ComprobanteVentaController {
    @Autowired
    private ComprobanteVentaService comprobanteVentaService;

    @PostMapping
    public ResponseEntity<?> save(@RequestBody @Valid DataRegisterComprobante comprobanteVenta){
        if (comprobanteVentaService.save(comprobanteVenta)) return new ResponseEntity<>(HttpStatus.CREATED);
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping
    public ResponseEntity<?> getAll(){
        return new ResponseEntity<>(comprobanteVentaService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return new ResponseEntity<>( comprobanteVentaService.findById(id), HttpStatus.OK);
    }

    @GetMapping("/pedido/{id}")
    public ResponseEntity<?> getByPedidoId(@PathVariable Long id) {
        return new ResponseEntity<>(comprobanteVentaService.findByPedidoId(id), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody @Valid DataUpdateComprobante comprobanteVenta){
        if (comprobanteVentaService.update(comprobanteVenta)) return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        if (comprobanteVentaService.delete(id)) return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
