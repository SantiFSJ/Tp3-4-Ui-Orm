package ar.unrn.tp.servicios;

import ar.unrn.tp.api.*;
import ar.unrn.tp.jpa.servicios.*;
import ar.unrn.tp.modelo.Cliente;
import ar.unrn.tp.modelo.ServicioTarjetaDeCredito;
import ar.unrn.tp.modelo.Venta;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class VentaServiceTest extends GenericServiceTest{

    @BeforeEach
    public void setUp(){

    }

    @Test
    public void realizarVenta(){
        VentaService ventaService = new VentaServiceImpl(new ServicioTarjetaDeCredito(), new DescuentoServiceImpl(this.emf),this.emf);
        ProductoService productoService = new ProductoServiceImpl(this.emf);
        CategoriaService categoriaService = new CategoriaServiceImpl(this.emf);
        MarcaService marcaService = new MarcaServiceImpl(this.emf);
        DescuentoService descuentoService = new DescuentoServiceImpl(this.emf);
        categoriaService.crearCategoria("Explosivos");
        marcaService.crearMarca("ACME");
        productoService.crearProducto("152","Es dinamita",650D,1L,2L);
        productoService.crearProducto("155","Minas antipersonales",950D,1L,2L);
        ClienteService clienteService = new ClienteServiceImpl(this.emf);
        clienteService.crearCliente("Santi","Fernández","42157849","santi@gmail.com");
        clienteService.agregarTarjeta(5L,"1542 4561 2514","Patagonia");
        descuentoService.crearDescuentoSobreTotal("Patagonia", LocalDate.now().minusDays(5),LocalDate.now().plusDays(5),5F);
        descuentoService.crearDescuento("ACME", LocalDate.now().minusDays(5),LocalDate.now().plusDays(5),5F);
        ventaService.realizarVenta(5L, List.of(3L,4L), 6L);
        inTransactionExecute(
                (em) -> {
                    Venta venta = em.find(Venta.class, 9L);
                    Assertions.assertEquals(2, venta.cantidadDeProductos());
                    Assertions.assertEquals(1472.0, venta.montoTotal());
                }
        );
    }

    @Test
    public void calcularMonto(){
        VentaService ventaService = new VentaServiceImpl(new ServicioTarjetaDeCredito(), new DescuentoServiceImpl(this.emf),this.emf);
        ProductoService productoService = new ProductoServiceImpl(this.emf);
        CategoriaService categoriaService = new CategoriaServiceImpl(this.emf);
        MarcaService marcaService = new MarcaServiceImpl(this.emf);
        DescuentoService descuentoService = new DescuentoServiceImpl(this.emf);
        categoriaService.crearCategoria("Explosivos");
        marcaService.crearMarca("ACME");
        productoService.crearProducto("152","Es dinamita",650D,1L,2L);
        productoService.crearProducto("155","Minas antipersonales",950D,1L,2L);
        ClienteService clienteService = new ClienteServiceImpl(this.emf);
        clienteService.crearCliente("Santi","Fernández","42157849","santi@gmail.com");
        clienteService.agregarTarjeta(5L,"1542 4561 2514","Patagonia");
        descuentoService.crearDescuentoSobreTotal("Patagonia", LocalDate.now().minusDays(5),LocalDate.now().plusDays(5),5F);
        descuentoService.crearDescuento("ACME", LocalDate.now().minusDays(5),LocalDate.now().plusDays(5),5F);
        inTransactionExecute(
                (em) -> {
                    Assertions.assertEquals(1472.0, ventaService.calcularMonto(List.of(3L,4L),6L));
                }
        );
    }

    @Test
    public void obtenerVentas(){
        VentaService ventaService = new VentaServiceImpl(new ServicioTarjetaDeCredito(), new DescuentoServiceImpl(this.emf),this.emf);
        ProductoService productoService = new ProductoServiceImpl(this.emf);
        CategoriaService categoriaService = new CategoriaServiceImpl(this.emf);
        MarcaService marcaService = new MarcaServiceImpl(this.emf);
        DescuentoService descuentoService = new DescuentoServiceImpl(this.emf);
        categoriaService.crearCategoria("Explosivos");
        marcaService.crearMarca("ACME");
        productoService.crearProducto("152","Es dinamita",650D,1L,2L);
        productoService.crearProducto("155","Minas antipersonales",950D,1L,2L);
        ClienteService clienteService = new ClienteServiceImpl(this.emf);
        clienteService.crearCliente("Santi","Fernández","42157849","santi@gmail.com");
        clienteService.agregarTarjeta(5L,"1542 4561 2514","Patagonia");
        descuentoService.crearDescuentoSobreTotal("Patagonia", LocalDate.now().minusDays(5),LocalDate.now().plusDays(5),5F);
        descuentoService.crearDescuento("ACME", LocalDate.now().minusDays(5),LocalDate.now().plusDays(5),5F);
        ventaService.realizarVenta(5L, List.of(3L,4L), 6L);
        inTransactionExecute(
                (em) -> {
                    Assertions.assertEquals(1, ventaService.ventas().size());
                }
        );
    }



}
