package ar.unrn.tp.excepciones;

public class EmailInvalidoExcepcion extends Exception{
    public EmailInvalidoExcepcion(){
        super("El email debe ser valido");
    }

}
