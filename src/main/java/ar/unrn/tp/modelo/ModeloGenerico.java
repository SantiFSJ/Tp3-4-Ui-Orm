package ar.unrn.tp.modelo;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Getter
@MappedSuperclass
public abstract class ModeloGenerico {
    @Id
    @GeneratedValue
    private Long id;

}
