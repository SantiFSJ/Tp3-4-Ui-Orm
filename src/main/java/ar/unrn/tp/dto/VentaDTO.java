package ar.unrn.tp.dto;

import ar.unrn.tp.modelo.Cliente;
import ar.unrn.tp.modelo.ProductoVendido;
import ar.unrn.tp.modelo.TarjetaDeCredito;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class VentaDTO {
    public Long id;
    public LocalDateTime fechaYHora;
    public ClienteDTO cliente;
    public List<ProductoDTO> productoVendidos;
    public TarjetaDeCreditoDTO tarjetaDeCredito;
    public double montoTotal;

    public VentaDTO(Long id, LocalDateTime fechaYHora, ClienteDTO cliente, List<ProductoDTO> productoVendidos, TarjetaDeCreditoDTO tarjetaDeCredito, double montoTotal) {
        this.id = id;
        this.fechaYHora = fechaYHora;
        this.cliente = cliente;
        this.productoVendidos = productoVendidos;
        this.tarjetaDeCredito = tarjetaDeCredito;
        this.montoTotal = montoTotal;
    }
}
