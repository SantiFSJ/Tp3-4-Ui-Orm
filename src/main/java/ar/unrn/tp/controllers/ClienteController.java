package ar.unrn.tp.controllers;

import ar.unrn.tp.api.ClienteService;
import ar.unrn.tp.dto.ClienteDTO;
import ar.unrn.tp.dto.TarjetaDeCreditoDTO;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("clientes")
public class ClienteController {
    @Autowired
    private ClienteService clienteService;

    public ClienteController(){}

    @PostMapping("/crear")
    @Operation(summary = "Agregar un cliente")
    public ResponseEntity<?> create(@RequestBody ClienteDTO cliente) {
        this.clienteService.crearCliente(cliente.nombre,cliente.apellido,cliente.dni,cliente.email);
        return ResponseEntity.status(OK).body("El cliente se añadió con éxito!");
    }

    @PutMapping("/actualizar")
    @Operation(summary = "Actualizar un cliente")
    public ResponseEntity<?> update(@RequestBody ClienteDTO cliente) {
        this.clienteService.modificarCliente(cliente.id,cliente.nombre);
        return ResponseEntity.status(OK).body("El cliente se actualizó con éxito!");
    }

    @PutMapping("/agregar-tarjeta/{id}")
    @Operation(summary = "Añadir tarjeta a un cliente")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody TarjetaDeCreditoDTO tarjeta) {
        this.clienteService.agregarTarjeta(id,tarjeta.getNumero(),tarjeta.getNombre());
        return ResponseEntity.status(OK).body("La tarjeta se añadió con éxito!");
    }

    @GetMapping("/listar-tarjetas/{id}")
    @Operation(summary = "Obtener tarjetas de un cliente")
    public ResponseEntity<?> findAllTarjetasDeCreditoDeCliente(@PathVariable Long id) {
        return ResponseEntity.status(OK).body(this.clienteService.listarTarjetas(id));
    }

    @GetMapping("/listar")
    @Operation(summary = "Listar todos los clientes")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.status(OK).body(this.clienteService.listarClientes());
    }

}