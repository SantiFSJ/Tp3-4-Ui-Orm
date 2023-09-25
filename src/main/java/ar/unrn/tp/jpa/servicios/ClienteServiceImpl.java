package ar.unrn.tp.jpa.servicios;

import ar.unrn.tp.api.ClienteService;
import ar.unrn.tp.excepciones.ClienteInvalidoExcepcion;
import ar.unrn.tp.excepciones.EmailInvalidoExcepcion;
import ar.unrn.tp.modelo.Cliente;
import ar.unrn.tp.modelo.TarjetaDeCredito;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.List;

@Service
public class ClienteServiceImpl extends GenericServiceImpl implements ClienteService {

    public ClienteServiceImpl(EntityManagerFactory emf){
        super(emf);
    }

    @Override
    public void crearCliente(String nombre, String apellido, String dni, String email) {
        inTransactionExecute((em) -> {
            try {
                em.persist(new Cliente(nombre,apellido,dni,email));
            } catch (ClienteInvalidoExcepcion | EmailInvalidoExcepcion e) {
                throw new RuntimeException(e);
            }
        });

    }

    @Override
    public void modificarCliente(Long idCliente, String nombre) {
        inTransactionExecute((em) -> {
            try {
                Cliente cliente = em.getReference(Cliente.class, idCliente);
                cliente.cambiarNombre(nombre);
                em.persist(cliente);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void agregarTarjeta(Long idCliente, String nro, String marca) {
        inTransactionExecute((em) -> {
            try {
                Cliente cliente = em.getReference(Cliente.class, idCliente);
                cliente.añadirTarjeta(new TarjetaDeCredito(marca, nro));
                em.persist(cliente);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public List<TarjetaDeCredito> listarTarjetas(Long idCliente) {
        List<TarjetaDeCredito> tarjetaDTOS= new ArrayList<>();
        inTransactionExecute((em) -> {
            List<TarjetaDeCredito> tarjetas = new ArrayList<>();
            Cliente cliente=em.find(Cliente.class,idCliente);
            tarjetas= cliente.getTarjetasDeCredito();
            tarjetaDTOS.addAll(tarjetas);
        });


        return tarjetaDTOS;
    }

    @Override
    public List<Cliente> listarClientes() {
        List<Cliente> clientes = new ArrayList<Cliente>();
        inTransactionExecute((em) -> {
            clientes.addAll(em.createQuery("SELECT c FROM Cliente c", Cliente.class).getResultList());
        });
        return clientes;
    }
}
