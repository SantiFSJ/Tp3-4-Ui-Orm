package ar.unrn.tp.modelo;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class TarjetaDeCredito extends ModeloGenerico{
    private String nombre;
    private String numero;

    public TarjetaDeCredito(String nombre, String numero) {
        this.nombre = nombre;
        this.numero = numero;
    }


    public boolean equals(TarjetaDeCredito tarjeta){
        return (this.nombre.equals(tarjeta.nombre()) && this.numero.equals(tarjeta.numero()));
    }

    private String nombre(){
        return this.nombre;
    }

    private String numero(){
        return this.numero;
    }

    public Boolean esDeMarca(String marcaDeTarjeta){
        return this.nombre.equals(marcaDeTarjeta);
    }

}
