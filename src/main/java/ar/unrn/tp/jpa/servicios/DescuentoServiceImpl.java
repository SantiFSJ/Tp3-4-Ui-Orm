package ar.unrn.tp.jpa.servicios;

import ar.unrn.tp.api.DescuentoService;
import ar.unrn.tp.excepciones.FechaInvalidaExcepcion;
import ar.unrn.tp.modelo.DescuentoDTO;
import ar.unrn.tp.modelo.DescuentoDeCompra;
import ar.unrn.tp.modelo.DescuentoDeProducto;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
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
    public List<DescuentoDTO> recuperarDescuentos() {
        List<DescuentoDTO> descuentoDTOS= new ArrayList<>();
        inTransactionExecute((em)->{
            List<DescuentoDeCompra> descuentosT = new ArrayList<>();
            List<DescuentoDeProducto> descuentosD = new ArrayList<>();
            LocalDate fechaActual= LocalDate.now();

            TypedQuery<DescuentoDeCompra> t = em.createQuery("SELECT d FROM DescuentoDeCompra d WHERE :fechaActual BETWEEN d.fechaInicio AND d.fechaFin", DescuentoDeCompra.class);
            t.setParameter("fechaActual", fechaActual);
            descuentosT.addAll(t.getResultList());

            for(DescuentoDeCompra d: descuentosT){
                descuentoDTOS.add(new DescuentoDTO(d.getId(),"Tarjeta",d.getFechaInicio(),d.getFechaFin(),d.getPorcentaje(),d.getMarcaDeTarjeta()));
            }

            TypedQuery<DescuentoDeProducto> p = em.createQuery("select p from DescuentoDeProducto p WHERE :fechaActual BETWEEN p.fechaInicio AND p.fechaFin", DescuentoDeProducto.class);
            p.setParameter("fechaActual", fechaActual);
            descuentosD.addAll(p.getResultList());
            for(DescuentoDeProducto d: descuentosD){
                descuentoDTOS.add(new DescuentoDTO(d.getId(),"Marca",d.getFechaInicio(),d.getFechaFin(),d.getPorcentaje(),d.getMarcaDeProducto()));
            }

        });
        return descuentoDTOS;

    }


}
