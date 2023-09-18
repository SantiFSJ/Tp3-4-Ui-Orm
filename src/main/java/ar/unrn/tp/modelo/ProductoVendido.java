package ar.unrn.tp.modelo;

import ar.unrn.tp.excepciones.ProductoInvalidoExcepcion;
import lombok.Getter;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ProductoVendido extends ModeloGenerico {

    private String codigo;
    private String descripcion;
    @ManyToOne
    private Categoria categoria;
    @ManyToOne
    private Marca marca;
    private Double precio;

    public ProductoVendido(String codigo, String descripcion, Categoria categoria, Marca marca, Double precio) throws ProductoInvalidoExcepcion {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.marca = marca;
        this.precio = precio;
    }
}
