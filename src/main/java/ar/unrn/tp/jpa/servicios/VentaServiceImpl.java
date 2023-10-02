package ar.unrn.tp.jpa.servicios;

import ar.unrn.tp.api.DescuentoService;
import ar.unrn.tp.api.VentaService;
import ar.unrn.tp.excepciones.ProductoInvalidoExcepcion;
import ar.unrn.tp.excepciones.TarjetaInvalidaExcepcion;
import ar.unrn.tp.modelo.*;

import ar.unrn.tp.modelo.DescuentoDTO;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.LockModeType;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class VentaServiceImpl extends GenericServiceImpl implements VentaService {

    private ServicioValidadorDeTarjetas servicioValidadorTarjetas;

    private DescuentoService descuentoService;

    public VentaServiceImpl(ServicioValidadorDeTarjetas servicioValidadorTarjetas, DescuentoService descuentoService, EntityManagerFactory emf){
        super(emf);
        this.descuentoService = descuentoService;
        this.servicioValidadorTarjetas = servicioValidadorTarjetas;
    }
    @Override
    public void realizarVenta(Long idCliente, List<Long> productos, Long idTarjeta) {
        inTransactionExecute((em) -> {



            Cliente cliente = em.find(Cliente.class,idCliente);
            TarjetaDeCredito tarjeta = em.find(TarjetaDeCredito.class,idTarjeta);
            if (cliente == null) {
                throw new RuntimeException("El cliente no existe");
            }
            if (tarjeta == null){
                throw new RuntimeException("No existe la tarjeta solicitada");
            }
            if (productos==null || productos.isEmpty()) {
                throw new RuntimeException("No hay productos para esta lista");
            }
            boolean existeTarjeta=false;

            for (TarjetaDeCredito tarjetaDeCredito: cliente.getTarjetasDeCredito()) {
                if (tarjetaDeCredito.getNumero().equalsIgnoreCase(tarjetaDeCredito.getNumero())) {
                    existeTarjeta = true;
                    break;
                }
            }
            if(existeTarjeta) {
                List<Descuento> promociones = new ArrayList<>();
                List<Descuento> promocionesDeCompra = em.createQuery("SELECT p FROM DescuentoDeCompra p", Descuento.class).getResultList();
                List<Descuento> promocionesDeProducto = em.createQuery("SELECT p FROM DescuentoDeProducto p", Descuento.class).getResultList();
                DescuentoDeProducto descuento;
                if (!promocionesDeCompra.isEmpty()) {
                    promociones.addAll(promocionesDeCompra);
                }

                if (!promocionesDeProducto.isEmpty()) {
                    promociones.addAll(promocionesDeProducto);
                }

                List<ProductoDisponible> listaProductos = em.createQuery("SELECT o FROM ProductoDisponible o WHERE o.id IN :ids", ProductoDisponible.class).setParameter("ids", productos).getResultList();

                NumeroSiguiente nroSiguiente;

                try {

                    nroSiguiente = em.createQuery("SELECT n FROM NumeroSiguiente n", NumeroSiguiente.class).setLockMode(LockModeType.PESSIMISTIC_WRITE).getSingleResult();

                    if(nroSiguiente.getAño() != LocalDate.now().getYear()){
                        nroSiguiente = new NumeroSiguiente(0,LocalDate.now().getYear());
                        em.persist(nroSiguiente);
                    }

                } catch (NoResultException e) {
                    nroSiguiente = new NumeroSiguiente(0,LocalDate.now().getYear());
                    em.persist(nroSiguiente);
                }



                try {
                    Venta venta = new Carrito(em.getReference(Cliente.class, idCliente), listaProductos, promociones, servicioValidadorTarjetas).realizarCompra(em.getReference(TarjetaDeCredito.class, idTarjeta));
                    venta.setNumero(String.valueOf(nroSiguiente.recuperarSiguiente())+"-"+String.valueOf(nroSiguiente.getAño()));
                    em.persist(venta);
                    em.persist(nroSiguiente);
                } catch (TarjetaInvalidaExcepcion | ProductoInvalidoExcepcion e) {
                    throw new RuntimeException(e);
                }



            }else {
                throw new RuntimeException("La tarjeta no pertenece al cliente");
            }
        });
    }
    @Override
    public float calcularMonto(List<Long> productos, Long idTarjeta) {
        AtomicReference<Float> monto = new AtomicReference<>(0F);
        inTransactionExecute((em) -> {
            TarjetaDeCredito tarjetaCredito = em.find(TarjetaDeCredito.class, idTarjeta);
            List<ProductoDisponible> listaProductos = new ArrayList<>();

            for(Long idProducto: productos){
                listaProductos.add(em.find(ProductoDisponible.class,idProducto));
            }

            List<Descuento> descuentos = new ArrayList<>();
            LocalDate fechaActual= LocalDate.now();

            TypedQuery<DescuentoDeCompra> t = em.createQuery("SELECT d FROM DescuentoDeCompra d WHERE :fechaActual BETWEEN d.fechaInicio AND d.fechaFin", DescuentoDeCompra.class);
            t.setParameter("fechaActual", fechaActual);
            descuentos.addAll(t.getResultList());

            TypedQuery<DescuentoDeProducto> p = em.createQuery("select p from DescuentoDeProducto p WHERE :fechaActual BETWEEN p.fechaInicio AND p.fechaFin", DescuentoDeProducto.class);
            p.setParameter("fechaActual", fechaActual);
            descuentos.addAll(p.getResultList());

            monto.set((float) new Carrito(listaProductos,descuentos,servicioValidadorTarjetas).calcularMontoConDescuentos(em.getReference(TarjetaDeCredito.class,idTarjeta)));
        });
        return monto.get();

    }

    @Override
    public List<Venta> ventas() {
        List<Venta> ventas = new ArrayList<>();
        inTransactionExecute((em) -> {
            ventas.addAll(em.createQuery("SELECT v FROM Venta v", Venta.class).getResultList());
        });
        return ventas;
    }
}
