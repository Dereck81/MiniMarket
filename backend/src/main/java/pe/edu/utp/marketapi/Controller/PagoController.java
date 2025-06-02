package pe.edu.utp.marketapi.Controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.utp.marketapi.Domain.Pago.DataRegisterPago;
import pe.edu.utp.marketapi.Domain.Pago.DataUpdatePago;
import pe.edu.utp.marketapi.Domain.Pago.PagoService;

@RestController
@RequestMapping("/pagos")
public class PagoController {
    @Autowired
    private PagoService pagoService;

    @PostMapping
    public ResponseEntity<?> save(@RequestBody @Valid DataRegisterPago pago){
        if (pagoService.save(pago)) return new ResponseEntity<>(HttpStatus.CREATED);
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return new ResponseEntity<>(
                pagoService.findById(id), HttpStatus.OK
        );
    }

    @GetMapping("/pedido/{id}")
    public ResponseEntity<?> getByPedidoId(@PathVariable Long id) {
        return new ResponseEntity<>(pagoService.findByPedidoId(id), HttpStatus.OK);
    }

    @GetMapping("/estadisticas-pagos")
    public ResponseEntity<?> getEstadisticasPagos() {
        return new ResponseEntity<>(pagoService.getEstadisticasPagos(), HttpStatus.OK);
    }

    @GetMapping("/ventas-mes")
    public ResponseEntity<?> getVentasMes() {
        return new ResponseEntity<>(pagoService.getVentasMes(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        if (pagoService.delete(id)) return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody @Valid DataUpdatePago pago){
        if (pagoService.update(pago)) return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
