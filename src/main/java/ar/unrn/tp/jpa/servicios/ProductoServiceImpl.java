package ar.unrn.tp.jpa.servicios;

import ar.unrn.tp.api.ProductoService;
import ar.unrn.tp.excepciones.BusinessException;
import ar.unrn.tp.excepciones.ProductoInvalidoExcepcion;
import ar.unrn.tp.modelo.*;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.OptimisticLockException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class ProductoServiceImpl extends GenericServiceImpl implements ProductoService {

    public ProductoServiceImpl(EntityManagerFactory emf){
        super(emf);
    }

    @Override
    public void crearProducto(String codigo, String descripcion, double precio, Long idCategoría, Long idMarca) {
        inTransactionExecute((em) -> {
            try {
                em.persist(new ProductoDisponible(codigo,descripcion
                        ,em.getReference(Categoria.class,idCategoría)
                        ,em.getReference(Marca.class,idMarca),precio));
            } catch (ProductoInvalidoExcepcion e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void modificarProducto(Long idProducto, String codigo, String descripcion, double precio, Long idCategoría, Long idMarca) {
        inTransactionExecute((em) -> {
            try {
                Marca marca = em.getReference(Marca.class, idMarca);
                Categoria categoria = em.getReference(Categoria.class, idCategoría);
                ProductoDisponible producto = em.getReference(ProductoDisponible.class, idProducto);
                producto.actualizarDescripcion(descripcion);
                producto.actualizarCodigo(codigo);
                producto.actualizarCategoria(categoria);
                producto.actualizarMarca(marca);
                producto.actualizarPrecio(precio);

                try{
                    em.persist(producto);
                }catch( OptimisticLockException e){
                    throw new BusinessException("Ups! sucedió un error concurrente :(");
                }

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public List<ProductoDisponible> listarProductos() {
        List<ProductoDisponible> productos = new ArrayList<>();
        inTransactionExecute((em) -> {
            productos.addAll(em.createQuery("SELECT p FROM ProductoDisponible p", ProductoDisponible.class).getResultList());
        });
        return productos;
    }

    public ProductoDisponible hallarProducto(Long idProducto){
        AtomicReference<ProductoDisponible> producto = new AtomicReference<>();
        inTransactionExecute((em) -> {
            producto.set(em.find(ProductoDisponible.class, idProducto));

        });
        return producto.get();
    }

}
