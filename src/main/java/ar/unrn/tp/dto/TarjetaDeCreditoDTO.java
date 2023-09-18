package ar.unrn.tp.dto;

import lombok.Data;

@Data
public class TarjetaDeCreditoDTO {
    private Long id;
    private String nombre;
    private String numero;

    public TarjetaDeCreditoDTO(Long id, String numero, String nombre) {
        this.id = id;
        this.numero = numero;
        this.nombre = nombre;
    }
}
