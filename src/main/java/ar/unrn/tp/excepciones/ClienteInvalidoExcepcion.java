package ar.unrn.tp.excepciones;

public class ClienteInvalidoExcepcion extends Exception{
    public ClienteInvalidoExcepcion(){
        super("Los datos del cliente deben ser validos");
    }

}
