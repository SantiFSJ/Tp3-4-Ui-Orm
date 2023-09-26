package ar.unrn.tp.servicios;

import ar.unrn.tp.api.DescuentoService;
import ar.unrn.tp.excepciones.FechaInvalidaExcepcion;
import ar.unrn.tp.jpa.servicios.DescuentoServiceImpl;
import ar.unrn.tp.modelo.DescuentoDeCompra;
import ar.unrn.tp.modelo.DescuentoDeCompra;
import ar.unrn.tp.modelo.DescuentoDeProducto;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertTrue;

public class DescuentoServiceTest extends GenericServiceTest{

    @Test
    public void crearDescuentoSobreTotal(){
        DescuentoService descuentoService = new DescuentoServiceImpl(this.emf);
        descuentoService.crearDescuentoSobreTotal("Patagonia", LocalDate.now().minusDays(5),LocalDate.now().plusDays(5),5);
        try {
            DescuentoDeCompra promocionResultado = new DescuentoDeCompra(LocalDate.now().minusDays(5),LocalDate.now().plusDays(5),"Patagonia",5F);
            inTransactionExecute(
                    (em) -> {
                        DescuentoDeCompra descuento = em.find(DescuentoDeCompra.class, 1L);
                        assertTrue(descuento.sosIgual(promocionResultado));

                    }
            );
        } catch (FechaInvalidaExcepcion e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    public void crearDescuento(){
        DescuentoService descuentoService = new DescuentoServiceImpl(this.emf);
        descuentoService.crearDescuento("ACME", LocalDate.now().minusDays(5),LocalDate.now().plusDays(5),5);
        try {
            DescuentoDeProducto promocionResultado = new DescuentoDeProducto(LocalDate.now().minusDays(5),LocalDate.now().plusDays(5),"ACME",5F);
            inTransactionExecute(
                    (em) -> {
                        DescuentoDeProducto descuento = em.find(DescuentoDeProducto.class, 1L);
                        assertTrue(descuento.sosIgual(promocionResultado));

                    }
            );
        } catch (FechaInvalidaExcepcion e) {
            throw new RuntimeException(e);
        }
    }

}
