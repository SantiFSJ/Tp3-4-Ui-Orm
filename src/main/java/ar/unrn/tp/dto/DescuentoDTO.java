package ar.unrn.tp.modelo;


import lombok.Data;

import java.time.LocalDate;

@Data
public class DescuentoDTO{
    private Long id;
    private String tipo;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private float porcentaje;
    private String marca;


    public DescuentoDTO(Long id, String tipo, LocalDate fechaInicio, LocalDate fechaFin, float porcentaje, String marca) {
        this.id = id;
        this.tipo = tipo;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.porcentaje = porcentaje;
        this.marca = marca;
    }

}