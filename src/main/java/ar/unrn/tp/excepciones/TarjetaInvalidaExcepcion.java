package ar.unrn.tp.excepciones;

public class TarjetaInvalidaExcepcion extends Exception {
    public TarjetaInvalidaExcepcion(){
        super("La tarjeta debe ser valida");
    }

}
