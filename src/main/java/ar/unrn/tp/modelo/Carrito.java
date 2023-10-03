package ar.unrn.tp.modelo;

import ar.unrn.tp.excepciones.ProductoInvalidoExcepcion;
import ar.unrn.tp.excepciones.TarjetaInvalidaExcepcion;

import java.time.LocalDateTime;
import java.util.List;

public class Carrito {
    private Cliente cliente;
    private List<ProductoDisponible> productos;
    private List<Descuento> promociones;
    private ServicioValidadorDeTarjetas servicioTarjetas;

    public Carrito(Cliente cliente, List<ProductoDisponible> productos, List<Descuento> promociones, ServicioValidadorDeTarjetas servicioTarjetas) {
        this.cliente = cliente;
        this.productos = productos;
        this.promociones = promociones;
        this.servicioTarjetas = servicioTarjetas;
    }

    public Carrito(List<ProductoDisponible> productos, List<Descuento> promociones, ServicioValidadorDeTarjetas servicioTarjetas) {
        this.productos = productos;
        this.promociones = promociones;
        this.servicioTarjetas = servicioTarjetas;
    }

    public double calcularMontoTotal(){
        double sumaPrecios = 0.0;
        for(ProductoDisponible producto: productos){
            sumaPrecios += producto.precio();
        }
        return sumaPrecios;
    }

    public double calcularMontoConDescuentos(TarjetaDeCredito tarjeta){
        double montoTotal = this.calcularMontoTotal();
        for(Descuento descuento : this.promociones){
            montoTotal -= descuento.aplicarDescuento(this.productos,tarjeta);
        }
        return montoTotal;

    }
    public Venta realizarCompra(TarjetaDeCredito tarjeta, String numeroDeVenta) throws TarjetaInvalidaExcepcion, ProductoInvalidoExcepcion {
        if(this.servicioTarjetas.validar(tarjeta))
            return new Venta(LocalDateTime.now(),this.cliente,this.productos,calcularMontoConDescuentos(tarjeta), tarjeta, numeroDeVenta);
        else
            throw new TarjetaInvalidaExcepcion();

    }
}
