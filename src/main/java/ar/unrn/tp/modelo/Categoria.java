package ar.unrn.tp.modelo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Categoria extends ModeloGenerico{
    private String nombre;

    public Categoria(String nombre){
        this.nombre = nombre;
    }

}
