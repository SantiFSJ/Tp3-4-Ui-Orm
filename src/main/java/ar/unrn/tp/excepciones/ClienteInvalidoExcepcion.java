package ar.unrn.tp.excepciones;

public class ClienteInvalidoExcepcion extends Exception{
    public ClienteInvalidoExcepcion(){
        super("Los datos del cliente deben ser validos");
    }

    @Override
    public String toString() {
        return getMessage();
    }

}
