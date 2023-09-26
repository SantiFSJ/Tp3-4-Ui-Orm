package ar.unrn.tp.servicios;

import ar.unrn.tp.api.CategoriaService;
import ar.unrn.tp.api.DescuentoService;
import ar.unrn.tp.api.MarcaService;
import ar.unrn.tp.api.ProductoService;
import ar.unrn.tp.excepciones.FechaInvalidaExcepcion;
import ar.unrn.tp.excepciones.ProductoInvalidoExcepcion;
import ar.unrn.tp.jpa.servicios.CategoriaServiceImpl;
import ar.unrn.tp.jpa.servicios.DescuentoServiceImpl;
import ar.unrn.tp.jpa.servicios.MarcaServiceImpl;
import ar.unrn.tp.jpa.servicios.ProductoServiceImpl;
import ar.unrn.tp.modelo.Categoria;
import ar.unrn.tp.modelo.Marca;
import ar.unrn.tp.modelo.ProductoDisponible;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class ProductoServiceTest extends GenericServiceTest{

    @Test
    public void crearProducto(){
        ProductoService productoService = new ProductoServiceImpl(this.emf);
        CategoriaService categoriaService = new CategoriaServiceImpl(this.emf);
        MarcaService marcaService = new MarcaServiceImpl(this.emf);
        categoriaService.crearCategoria("Explosivos");
        marcaService.crearMarca("ACME");
        productoService.crearProducto("152","Es dinamita",650D,1L,2L);
        inTransactionExecute(
                (em) -> {
                    Marca marca = em.getReference(Marca.class,2L);
                    Categoria categoria = em.getReference(Categoria.class,1L);
                    try {
                        ProductoDisponible productoResultado = new ProductoDisponible("152","Es dinamita",categoria,marca,650D);
                        ProductoDisponible producto = em.find(ProductoDisponible.class, 3L);
                        assertTrue(producto.sosIgualA(productoResultado));
                    } catch (ProductoInvalidoExcepcion e) {
                        throw new RuntimeException(e);
                    }
                }
            );
    }

    @Test
    public void modificarProducto(){
        ProductoService productoService = new ProductoServiceImpl(this.emf);
        CategoriaService categoriaService = new CategoriaServiceImpl(this.emf);
        MarcaService marcaService = new MarcaServiceImpl(this.emf);
        categoriaService.crearCategoria("Explosivos");
        marcaService.crearMarca("ACME");
        productoService.crearProducto("152","explosivo C4",650D,1L,2L);
        productoService.modificarProducto(3L,"162","Es dinamita",650D,1L,2L);
        inTransactionExecute(
                (em) -> {
                    Marca marca = em.getReference(Marca.class,2L);
                    Categoria categoria = em.getReference(Categoria.class,1L);
                    try {
                        ProductoDisponible productoResultado = new ProductoDisponible("162","Es dinamita",categoria,marca,650D);
                        ProductoDisponible producto = em.find(ProductoDisponible.class, 3L);
                        assertTrue(producto.sosIgualA(productoResultado));
                    } catch (ProductoInvalidoExcepcion e) {
                        throw new RuntimeException(e);
                    }
                }
        );

    }

    @Test
    public void ListarProductos() {
        ProductoService productoService = new ProductoServiceImpl(this.emf);
        CategoriaService categoriaService = new CategoriaServiceImpl(this.emf);
        MarcaService marcaService = new MarcaServiceImpl(this.emf);
        categoriaService.crearCategoria("Explosivos");
        marcaService.crearMarca("ACME");
        productoService.crearProducto("152", "explosivo C4", 650D, 1L, 2L);
        productoService.crearProducto("159", "Ford Focus", 900D, 1L, 2L);

        List<ProductoDisponible> productos = productoService.listarProductos();


        Assert.assertEquals("true",2, productos.size(),00);


    }

}
