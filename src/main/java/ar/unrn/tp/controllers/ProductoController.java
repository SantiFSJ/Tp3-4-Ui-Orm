package ar.unrn.tp.controllers;

import ar.unrn.tp.api.ProductoService;
import ar.unrn.tp.modelo.ProductoDisponible;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("productos")
public class ProductoController {

    private ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @PostMapping("/crear")
    @Operation(summary = "Agregar un producto")
    public ResponseEntity<?> create(@RequestBody ProductoDisponible producto) {
        this.productoService.crearProducto(producto.getCodigo(),producto.getDescripcion(),producto.getPrecio(),producto.getCategoria().getId(),producto.getMarca().getId());
        return ResponseEntity.status(OK).body("El producto se añadió con éxito!");
    }

    @PutMapping("/actualizar")
    @Operation(summary = "Actualizar un producto")
    public ResponseEntity<?> update(@RequestBody ProductoDisponible producto) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        this.productoService.modificarProducto(producto.getId(),producto.getCodigo(),producto.getDescripcion(),producto.getPrecio(),producto.getCategoria().getId(),producto.getMarca().getId());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/listar")
    @Operation(summary = "Listar todos los productos")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.status(OK).body(this.productoService.listarProductos());
    }

    @GetMapping("/listar/{id}")
    @Operation(summary = "Hallar un producto en base a su id")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        return ResponseEntity.status(OK).body(this.productoService.hallarProducto(id));
    }

}
