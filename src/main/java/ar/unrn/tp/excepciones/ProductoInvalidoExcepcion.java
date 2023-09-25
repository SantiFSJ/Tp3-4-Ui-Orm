package ar.unrn.tp.excepciones;

public class ProductoInvalidoExcepcion extends Exception{
    public ProductoInvalidoExcepcion(){
        super("Los datos de producto deben ser validos");
    }

    @Override
    public String toString() {
        return getMessage();
    }

}
