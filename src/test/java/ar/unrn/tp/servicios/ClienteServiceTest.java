package ar.unrn.tp.servicios;

import ar.unrn.tp.api.ClienteService;
import ar.unrn.tp.jpa.servicios.ClienteServiceImpl;
import ar.unrn.tp.modelo.Cliente;
import ar.unrn.tp.modelo.TarjetaDeCredito;


import org.junit.jupiter.api.Test;
import jakarta.persistence.Persistence;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ClienteServiceTest extends GenericServiceTest {

    @Test
    public void crearCliente(){
        ClienteService clienteService = new ClienteServiceImpl(this.emf);
        clienteService.crearCliente("Santiago", "Fernández San Juan", "43217700", "santi@gmail.com");
        inTransactionExecute(
                (em) -> {
                    Cliente cliente = em.find(Cliente.class, 1L);
                    assertTrue(cliente.sos(cliente));
                }
        );
    }

    @Test
    public void modificarCliente(){
        ClienteService clienteService = new ClienteServiceImpl(this.emf);
        clienteService.crearCliente("Santiago", "Fernández San Juan", "43217700", "santi@gmail.com");
        clienteService.modificarCliente(1L, "Santiago Jose");
        inTransactionExecute(
                (em) -> {
                    Cliente cliente = em.find(Cliente.class, 1L);
                    assertEquals("true",cliente.nombre(),"Santiago Jose");

                }
        );
    }


    @Test
    public void agregarTarjeta(){
        ClienteService clienteService = new ClienteServiceImpl(this.emf);
        clienteService.crearCliente("Santiago", "Fernández San Juan", "43217700", "santi@gmail.com");
        clienteService.agregarTarjeta(1L, "2945 5154 1562","Patagonia");
        TarjetaDeCredito tarjetaPatagonia = new TarjetaDeCredito("Patagonia","2945 5154 1562");
        inTransactionExecute(
                (em) -> {
                    Cliente cliente = em.find(Cliente.class, 1L);
                    assertEquals(true,cliente.tieneEstaTarjeta(tarjetaPatagonia));

                }
        );
    }

    @Test
    public void listarTarjetas(){
        this.emf = Persistence.createEntityManagerFactory("objectdb:TestingFile.tmp;drop");
        ClienteService clienteService = new ClienteServiceImpl(this.emf);
        clienteService.crearCliente("Santiago", "Fernández San Juan", "43217700", "santi@gmail.com");
        clienteService.agregarTarjeta(1L, "2945 5154 1562","Patagonia");
        List<TarjetaDeCredito> tarjetas = clienteService.listarTarjetas(1L);
        inTransactionExecute(
                (em) -> {
                    assertEquals(true,!tarjetas.isEmpty());
                }
        );
    }

}
