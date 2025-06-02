package pe.edu.utp.marketapi.Controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.utp.marketapi.Domain.Categoria.CategoriaService;
import pe.edu.utp.marketapi.Domain.Categoria.DataRegisterCategoria;
import pe.edu.utp.marketapi.Domain.Categoria.DataUpdateCategoria;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {
    @Autowired
    private CategoriaService categoriaService;

    @PostMapping
    public ResponseEntity<?> save(@RequestBody @Valid DataRegisterCategoria categoria){
        if (categoriaService.save(categoria)) return new ResponseEntity<>(HttpStatus.CREATED);
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping
    public ResponseEntity<?> getAll(){
        return new ResponseEntity<>(categoriaService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return new ResponseEntity<>(
                categoriaService.findById(id), categoriaService.findById(id)
                .isEmpty() ? HttpStatus.NOT_FOUND : HttpStatus.OK
        );
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<?> getByNombre(@PathVariable String nombre) {
        return new ResponseEntity<>(
                categoriaService.findByNombre(nombre), categoriaService.findByNombre(nombre)
                .isEmpty() ? HttpStatus.NOT_FOUND : HttpStatus.OK
        );
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody @Valid DataUpdateCategoria categoria){
        if (categoriaService.update(categoria)) return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        if (categoriaService.delete(id)) return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
