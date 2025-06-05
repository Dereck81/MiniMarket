package pe.edu.utp.marketapi.Controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.utp.marketapi.Domain.Pedido.DataRequestRegisterPedido;
import pe.edu.utp.marketapi.Domain.Pedido.DataRequestUpdatePedido;
import pe.edu.utp.marketapi.Domain.Pedido.PedidoService;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {
    @Autowired
    private PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<?> save(@RequestBody @Valid DataRequestRegisterPedido pedido){
        if (pedidoService.save(pedido)) return new ResponseEntity<>(HttpStatus.CREATED);
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        return new ResponseEntity<>(pedidoService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return new ResponseEntity<>(
                pedidoService.findById(id), HttpStatus.OK
        );
    }

    @GetMapping("/cliente/{id}")
    public ResponseEntity<?> getByClienteId(@PathVariable Long id) {
        return new ResponseEntity<>(pedidoService.findByUserId(id), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody @Valid DataRequestUpdatePedido pedido){
        if (pedidoService.update(pedido)) return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        if (pedidoService.delete(id)) return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
