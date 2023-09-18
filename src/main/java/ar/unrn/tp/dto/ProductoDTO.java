package ar.unrn.tp.dto;

import ar.unrn.tp.modelo.Marca;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductoDTO{
    private Long id;
    private String codigo;
    private String descripcion;
    private CategoriaDTO categoria;
    private MarcaDTO marca;
    private Double precio;


}
