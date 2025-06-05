package pe.edu.utp.marketapi.Controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.utp.marketapi.Domain.Inventario.DataRegisterInventario;
import pe.edu.utp.marketapi.Domain.Inventario.DataUpdateInventario;
import pe.edu.utp.marketapi.Domain.Inventario.InventarioService;

import java.io.IOException;

@RestController
@RequestMapping("/inventarios")
public class InventarioController {
    @Autowired
    private InventarioService inventarioService;

    @PostMapping
    public ResponseEntity<?> save(@RequestBody @Valid DataRegisterInventario inventario){
        if (inventarioService.save(inventario)) return new ResponseEntity<>(HttpStatus.CREATED);
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping
    public ResponseEntity<?> getAll(){
        return new ResponseEntity<>(inventarioService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<?> getByNombre(@PathVariable String nombre){
        return new ResponseEntity<>(inventarioService.findByNombre(nombre), HttpStatus.OK);
    }

    @GetMapping("/categoria/{id}")
    public ResponseEntity<?> getByCategoria(@PathVariable Long id){
        return new ResponseEntity<>(inventarioService.findByCategoria(id), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return new ResponseEntity<>(inventarioService.findById(id), HttpStatus.OK);
    }

    @GetMapping("/stock-productos")
    public ResponseEntity<?> getStockProductos(){
        return new ResponseEntity<>(inventarioService.getStockProductos(), HttpStatus.OK);
    }

    @GetMapping("/reporte")
    public ResponseEntity<byte[]> downloadInventarioReport() {
        byte[] excelData = inventarioService.generateInventarioReport();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "reporte_inventario.xlsx");

        return ResponseEntity.ok().headers(headers).body(excelData);

    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody @Valid DataUpdateInventario inventario){
        if (inventarioService.update(inventario)) return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping
    public ResponseEntity<?> delete(@RequestParam Long id){
        if (inventarioService.delete(id)) return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}