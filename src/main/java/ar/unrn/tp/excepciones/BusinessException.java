package ar.unrn.tp.excepciones;

public class BusinessException extends Exception{
    public BusinessException(String message){
        super(message);
    }

    @Override
    public String toString() {
        return getMessage();
    }

}