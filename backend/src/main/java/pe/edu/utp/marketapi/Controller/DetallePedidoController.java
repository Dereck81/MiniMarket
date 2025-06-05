package pe.edu.utp.marketapi.Controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.utp.marketapi.Domain.DetallePedido.DataRegisterDetallePedido;
import pe.edu.utp.marketapi.Domain.DetallePedido.DataUpdateDetallePedido;
import pe.edu.utp.marketapi.Domain.DetallePedido.DetallePedidoService;

@RestController
@RequestMapping("/detalle-pedidos")
public class DetallePedidoController {
    @Autowired
    private DetallePedidoService detallePedidoService;

    @PostMapping
    public ResponseEntity<?> save(@RequestBody @Valid DataRegisterDetallePedido detallePedido){
        if (detallePedidoService.save(detallePedido)) return new ResponseEntity<>(HttpStatus.CREATED);
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/pedido/{id}")
    public ResponseEntity<?> getByPedidoId(@PathVariable Long id) {
        return new ResponseEntity<>(detallePedidoService.findByPedidoId(id), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return new ResponseEntity<>(detallePedidoService.findById(id), HttpStatus.OK);
    }

    @GetMapping("/top-vendidos")
    public ResponseEntity<?> getTopVendidos() {
        return new ResponseEntity<>(detallePedidoService.getTopVendidos(), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody @Valid DataUpdateDetallePedido detallePedido){
        if (detallePedidoService.update(detallePedido)) return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        if (detallePedidoService.delete(id)) return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
