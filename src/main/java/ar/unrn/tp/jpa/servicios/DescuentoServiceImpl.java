package ar.unrn.tp.jpa.servicios;

import ar.unrn.tp.api.DescuentoService;
import ar.unrn.tp.excepciones.FechaInvalidaExcepcion;
import ar.unrn.tp.modelo.Descuento;
import ar.unrn.tp.modelo.DescuentoDeCompra;
import ar.unrn.tp.modelo.DescuentoDeProducto;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class DescuentoServiceImpl extends GenericServiceImpl implements DescuentoService {

    public DescuentoServiceImpl(EntityManagerFactory emf){
        super(emf);
    }

    @Override
    public void crearDescuentoSobreTotal(String marcaTarjeta, LocalDate fechaDesde, LocalDate fechaHasta, float porcentaje) {
        inTransactionExecute((em) -> {
            try {
                em.persist(new DescuentoDeCompra(fechaDesde,fechaHasta,marcaTarjeta,porcentaje));
            } catch (FechaInvalidaExcepcion e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void crearDescuento(String marcaProducto, LocalDate fechaDesde, LocalDate fechaHasta, float porcentaje) {
        inTransactionExecute((em) -> {
            try {
                em.persist(new DescuentoDeProducto(fechaDesde,fechaHasta,marcaProducto,porcentaje));
            } catch (FechaInvalidaExcepcion e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public List<Descuento> recuperarDescuentos() {
        List<Descuento> descuentos = new ArrayList<>();
        inTransactionExecute((em) -> {
            List<Descuento> promocionesDeCompra = em.createQuery("SELECT p FROM DescuentoDeCompra p", Descuento.class).getResultList();
            List<Descuento> promocionesDeProducto = em.createQuery("SELECT p FROM PromocionDeProducto p", Descuento.class).getResultList();

            if (!promocionesDeCompra.isEmpty()) {
                descuentos.addAll(promocionesDeCompra);
            }

            if (!promocionesDeProducto.isEmpty()) {
                descuentos.addAll(promocionesDeProducto);
            }
        });
        return descuentos;

    }


}
