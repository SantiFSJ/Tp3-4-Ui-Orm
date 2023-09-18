package ar.unrn.tp.modelo;


import java.time.LocalDate;
import java.util.List;


public abstract class Descuento extends ModeloGenerico {
    protected LocalDate fechaInicio;
    protected LocalDate fechaFin;
    protected float porcentaje;



    protected Boolean esValida(){
        return null;
    }

    public double aplicarDescuento(List<ProductoDisponible> productos, TarjetaDeCredito tarjeta){
        return 0.0;
    }
}
