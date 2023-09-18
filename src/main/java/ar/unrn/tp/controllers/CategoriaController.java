package ar.unrn.tp.controllers;

import ar.unrn.tp.api.CategoriaService;
import ar.unrn.tp.dto.CategoriaDTO;
import ar.unrn.tp.dto.ClienteDTO;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("categorias")
public class CategoriaController {

    private CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService){
        this.categoriaService = categoriaService;
    }

    @PostMapping("/crear")
    @Operation(summary = "Agregar una categoria")
    public ResponseEntity<?> create(@RequestBody CategoriaDTO categoria) {
        this.categoriaService.crearCategoria(categoria.getNombre());
        return ResponseEntity.status(OK).body("La categoria se añadió con éxito!");
    }

}
