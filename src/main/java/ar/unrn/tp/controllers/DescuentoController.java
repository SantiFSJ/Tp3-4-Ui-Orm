package ar.unrn.tp.controllers;

import ar.unrn.tp.api.DescuentoService;
import ar.unrn.tp.modelo.DescuentoDTO;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("descuentos")
public class DescuentoController {

    private DescuentoService descuentoService;

    public DescuentoController(DescuentoService descuentoService) {
        this.descuentoService = descuentoService;
    }

    @PostMapping("/crear/compra/{producto}")
    @Operation(summary = "Agregar un descuento por compra")
    public ResponseEntity<?> createDescuentoCompra(@PathVariable String producto,@RequestBody DescuentoDTO descuento) {
        this.descuentoService.crearDescuento(producto,descuento.getFechaInicio(),descuento.getFechaFin(),descuento.getPorcentaje());
        return ResponseEntity.status(OK).body("El descuento se creo con éxito!");
    }

    @PostMapping("/crear/producto/{tarjeta}")
    @Operation(summary = "Agregar un descuento por compra")
    public ResponseEntity<?> createDescuentoProducto(@PathVariable String tarjeta,@RequestBody DescuentoDTO descuento) {
        this.descuentoService.crearDescuentoSobreTotal(tarjeta,descuento.getFechaInicio(),descuento.getFechaFin(),descuento.getPorcentaje());
        return ResponseEntity.status(OK).body("El descuento se creo con éxito!");
    }

    @GetMapping("/listar")
    @Operation(summary = "Listar todos los descuentos")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.status(OK).body(this.descuentoService.recuperarDescuentos());
    }


}
