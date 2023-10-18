package ar.unrn.tp.controllers;

import ar.unrn.tp.api.VentaService;
import ar.unrn.tp.modelo.ProductoVendido;
import ar.unrn.tp.modelo.Venta;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("ventas")
public class VentaController {

    VentaService ventaService;

    public VentaController(VentaService ventaService) {
        this.ventaService = ventaService;
    }

    @PostMapping("/crear")
    @Operation(summary = "Agregar una venta")
    public ResponseEntity<?> create(@RequestBody Venta venta) {
        List<Long> idList = new ArrayList<>();
        for(ProductoVendido producto: venta.getProductoVendidos()){
            idList.add(producto.getId());
        }
        this.ventaService.realizarVenta(venta.getCliente().getId(),idList,venta.getTarjetaDeCredito().getId() );
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/calcular-monto")
    @Operation(summary = "Calcular el monto de una venta")
    public ResponseEntity<?> calculateAmount(@RequestBody Venta venta) {
        List<Long> productosIds = venta.getProductoVendidos().stream().map(ProductoVendido::getId).collect(Collectors.toList());
        return ResponseEntity.status(OK).body(this.ventaService.calcularMonto(productosIds,venta.getTarjetaDeCredito().getId()));
    }

    @GetMapping("/listar/{idCliente}")
    @Operation(summary = "Listar las ventas recientes de un cliente")
    public ResponseEntity<?> findAllRecent(@PathVariable Long idCliente) {
        return ResponseEntity.status(OK).body(this.ventaService.ventasRecientesDeCliente(idCliente));
    }


}
