package ar.unrn.tp.modelo;

import ar.unrn.tp.excepciones.FechaInvalidaExcepcion;

import java.time.LocalDate;
import java.util.List;


public class DescuentoDeProducto extends Descuento {

    protected LocalDate fechaInicio;
    protected LocalDate fechaFin;
    protected float porcentaje;
    private String marcaDeProducto;
    public DescuentoDeProducto(LocalDate fechaInicio, LocalDate fechaFin, String marcaDeProducto, float porcentaje) throws FechaInvalidaExcepcion {
        this.validarFechas(fechaInicio,fechaFin);
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.porcentaje = porcentaje;
        this.marcaDeProducto = marcaDeProducto;
    }

    private void validarFechas(LocalDate fechaInicio, LocalDate fechaFin) throws FechaInvalidaExcepcion {
        if(fechaInicio.isAfter(fechaFin)){
            throw new FechaInvalidaExcepcion();
        }
    }

    @Override
    protected Boolean esValida(){
        return this.fechaInicio.isBefore(LocalDate.now()) && this.fechaFin.isAfter(LocalDate.now());
    }


    @Override
    public double aplicarDescuento(List<ProductoDisponible> productos, TarjetaDeCredito tarjeta){
        double montoADescontar = 0.0;
        if(this.esValida()){
            for(ProductoDisponible producto: productos){
                if(producto.esDeMarca(this.marcaDeProducto))
                    montoADescontar += producto.precio() * 0.05;
            }
        }
        return montoADescontar;

    }

    private String marcaDeProducto(){ return this.marcaDeProducto; }
    private LocalDate fechaDeInicio(){
        return this.fechaInicio;
    }
    private LocalDate fechaDeFin(){
        return this.fechaFin;
    }
    private float porcentaje(){
        return this.porcentaje;
    }


    public boolean sosIgual(DescuentoDeProducto promocion){
        return this.marcaDeProducto.equals(promocion.marcaDeProducto()) &&
                this.fechaInicio.equals(promocion.fechaDeInicio()) &&
                this.fechaFin.equals((promocion.fechaDeFin())) &&
                this.porcentaje == promocion.porcentaje();
    }


}
