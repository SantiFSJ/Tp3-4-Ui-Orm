package ar.unrn.tp.modelo;

import ar.unrn.tp.excepciones.ProductoInvalidoExcepcion;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Venta extends ModeloGenerico{
    LocalDateTime fechaYHora;
    @ManyToOne
    Cliente cliente;
    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    List<ProductoVendido> productoVendidos;
    @ManyToOne
    @JoinColumn(name = "tarjeta_id")
    TarjetaDeCredito tarjetaDeCredito;
    double montoTotal;
    String numero;

    public Venta(LocalDateTime fechaYHora, Cliente cliente, List<ProductoDisponible> productoVendidos, double montoTotal, TarjetaDeCredito tarjetaDeCredito, String numero) throws ProductoInvalidoExcepcion {
        this.fechaYHora = fechaYHora;
        this.cliente = cliente;
        this.productoVendidos = new ArrayList<ProductoVendido>();
        this.montoTotal = montoTotal;
        this.tarjetaDeCredito = tarjetaDeCredito;
        this.numero = numero;
        this.parseProductos(productoVendidos);
    }

    public double montoTotal(){
        return this.montoTotal;
    }

    public int cantidadDeProductos(){
        return this.productoVendidos.size();
    }

    private void parseProductos(List<ProductoDisponible> productoVendidos) throws ProductoInvalidoExcepcion {
        for(ProductoDisponible producto: productoVendidos){
            this.productoVendidos.add(producto.obtenerVendido());
        }
    }
}
