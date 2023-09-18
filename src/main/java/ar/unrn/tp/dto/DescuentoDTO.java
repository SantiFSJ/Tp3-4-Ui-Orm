package ar.unrn.tp.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class DescuentoDTO  {
    private Long id;
    private String tipo;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private float porcentaje;



}
