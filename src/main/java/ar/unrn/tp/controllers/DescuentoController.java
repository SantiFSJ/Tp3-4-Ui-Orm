package ar.unrn.tp.controllers;

import ar.unrn.tp.api.DescuentoService;
import ar.unrn.tp.dto.ClienteDTO;
import ar.unrn.tp.dto.DescuentoDTO;
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

    @PostMapping("/crear/compra/{marca-producto}")
    @Operation(summary = "Agregar un descuento por compra")
    public ResponseEntity<?> createDescuentoCompra(@PathVariable String marcaProducto,@RequestBody DescuentoDTO descuento) {
        this.descuentoService.crearDescuento(marcaProducto,descuento.getFechaInicio(),descuento.getFechaFin(),descuento.getPorcentaje());
        return ResponseEntity.status(OK).body("El descuento se creo con éxito!");
    }

    @PostMapping("/crear/producto/{marca-tarjeta}")
    @Operation(summary = "Agregar un descuento por compra")
    public ResponseEntity<?> createDescuentoProducto(@PathVariable String marcaTarjeta,@RequestBody DescuentoDTO descuento) {
        this.descuentoService.crearDescuentoSobreTotal(marcaTarjeta,descuento.getFechaInicio(),descuento.getFechaFin(),descuento.getPorcentaje());
        return ResponseEntity.status(OK).body("El descuento se creo con éxito!");
    }

}
