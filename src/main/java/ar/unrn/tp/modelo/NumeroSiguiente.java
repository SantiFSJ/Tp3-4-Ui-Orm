package ar.unrn.tp.modelo;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class NumeroSiguiente extends ModeloGenerico{
    private int año;
    private int nroActual;

    public NumeroSiguiente(int nro,int año){
        this.año = año;
        this.nroActual = nro;
    }
    public int recuperarSiguiente(){
        this.nroActual += 1;
        return this.nroActual + 1;
    }


}
