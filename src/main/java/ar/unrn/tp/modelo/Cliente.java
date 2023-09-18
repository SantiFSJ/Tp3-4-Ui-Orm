package ar.unrn.tp.modelo;

import ar.unrn.tp.excepciones.ClienteInvalidoExcepcion;
import ar.unrn.tp.excepciones.EmailInvalidoExcepcion;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "Cliente", uniqueConstraints = @UniqueConstraint(columnNames = "dni"))
public class Cliente extends ModeloGenerico {
    private String nombre;
    private String apellido;
    @Column( name="dni",unique = true)
    private String dni;
    private String email;

    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private List<TarjetaDeCredito> tarjetasDeCredito;


    public Cliente(String nombre, String apellido, String dni, String email) throws ClienteInvalidoExcepcion, EmailInvalidoExcepcion {
        this.validarCliente(nombre,apellido,dni,email);
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.email = email;
        this.tarjetasDeCredito = new ArrayList<TarjetaDeCredito>();

    }
    public Cliente(String nombre, String apellido, String dni, String email, List<TarjetaDeCredito> tarjetasDeCredito) throws ClienteInvalidoExcepcion, EmailInvalidoExcepcion {
        this.validarCliente(nombre,apellido,dni,email);
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.email = email;
        this.tarjetasDeCredito = tarjetasDeCredito;
    }

    public void añadirTarjeta(TarjetaDeCredito tarjetaDeCredito){
        this.tarjetasDeCredito.add(tarjetaDeCredito);
    }

    public List<TarjetaDeCredito> tarjetasDeCredito(){
        return this.tarjetasDeCredito;
    }

    public String nombre(){
        return this.nombre;
    }

    public Boolean tieneEstaTarjeta(TarjetaDeCredito tarjeta){
        boolean contieneTarjeta = false;
        for(TarjetaDeCredito tarjetaDeCredito: this.tarjetasDeCredito){
            if(tarjetaDeCredito.equals(tarjeta))
                contieneTarjeta = true;
        }
        return contieneTarjeta;
    }
    private void validarCliente(String nombre, String apellido, String dni, String email) throws ClienteInvalidoExcepcion, EmailInvalidoExcepcion {
        if (nombre == null
                || nombre.equals("")
                || apellido == null
                || apellido.equals("")
                || dni == null
                || dni.equals("")
                || email == null
                || email.equals("")
        ) {
            throw new ClienteInvalidoExcepcion();
        }else{
            if(!this.validarEmail(email)){
                throw new EmailInvalidoExcepcion();
            }
        }

    }

    public void cambiarNombre(String nombre){
        this.nombre = nombre;
    }

    public boolean sos(Cliente cliente){
        return this.equals(cliente);
    }

    private boolean validarEmail(String email){
        Pattern patronCorreo = Pattern.compile("^(.+)@(\\S+)$");
        Matcher comparador = patronCorreo.matcher(email);
        return comparador.matches();
    }
}
