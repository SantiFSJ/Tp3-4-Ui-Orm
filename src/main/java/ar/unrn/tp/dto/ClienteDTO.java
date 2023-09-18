package ar.unrn.tp.dto;

import lombok.Data;


public record ClienteDTO() {
    public static Long id;
    public static String nombre;
    public static String apellido;
    public static String dni;
    public static String email;

}
