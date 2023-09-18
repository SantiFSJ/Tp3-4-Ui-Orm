package ar.unrn.tp.controllers;

import ar.unrn.tp.api.VentaService;
import ar.unrn.tp.dto.ClienteDTO;
import ar.unrn.tp.dto.ProductoDTO;
import ar.unrn.tp.dto.VentaDTO;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

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
    public ResponseEntity<?> create(@RequestBody VentaDTO venta) {
        List<Long> idList = new ArrayList<>();
        for(ProductoDTO producto: venta.productoVendidos){
            idList.add(producto.getId());
        }
        this.ventaService.realizarVenta(venta.cliente.id,idList,venta.tarjetaDeCredito.getId() );
        return ResponseEntity.status(OK).body("La venta se realizó con éxito!");
    }

}
