package ar.unrn.tp.modelo;

import ar.unrn.tp.excepciones.ClienteInvalidoExcepcion;
import ar.unrn.tp.excepciones.EmailInvalidoExcepcion;
import ar.unrn.tp.excepciones.FechaInvalidaExcepcion;
import ar.unrn.tp.excepciones.ProductoInvalidoExcepcion;
import org.junit.Assert;
import org.junit.Test;


import java.time.LocalDate;
import java.util.ArrayList;

public class VentasOnlineTest {
    /*@Test
    public void calcularMontoTotalSinDescuento(){
        // Inicialización
        try {
            ArrayList<TarjetaDeCredito> tarjetas = new ArrayList<TarjetaDeCredito>();
            Cliente santi = new Cliente("Santiago", "Fernández San Juan", "43217767", "santi@gmail.com", tarjetas);

            Marca comarca = new Marca("Comarca");
            Marca acme = new Marca("Acme");

            ProductoDisponible dinamita = new ProductoDisponible("1251", "Es dinamita", EXPLOSIVOS, acme, 250.0);
            ProductoDisponible ropa = new ProductoDisponible("1350", "Es ropa deportiva", DEPORTIVA, comarca, 500.0);

            ArrayList<ProductoDisponible> productos = new ArrayList<ProductoDisponible>();
            productos.add(dinamita);
            productos.add(ropa);

            PromocionDeProducto promocionActivaAcme = new PromocionDeProducto(LocalDate.now().minusDays(15), LocalDate.now().minusDays(5), acme);
            ArrayList<Promocion> promociones = new ArrayList<Promocion>();
            promociones.add(promocionActivaAcme);

            ServicioTarjetaDeCredito tarjetaServicio = new ServicioTarjetaDeCredito();

            Carrito miCarrito = new Carrito(santi, productos, promociones, tarjetaServicio);

            // Ejercitación
            double montoTotalSinDescuentosVigentes = miCarrito.calcularMontoConDescuentos(new TarjetaDeCredito());

            // Verificación
            Assert.assertEquals("true",750.0, montoTotalSinDescuentosVigentes,00);

        }catch(Exception e){
            System.out.println(e.getMessage());
        }

    }

    @Test
    public void calcularMontoTotalConDescuentoAcme(){
        // Inicialización
        try {
            ArrayList<TarjetaDeCredito> tarjetas = new ArrayList<TarjetaDeCredito>();
            Cliente santi = new Cliente("Santiago", "Fernández San Juan", "43217767", "santi@gmail.com", tarjetas);

            Marca comarca = new Marca("Comarca");
            Marca acme = new Marca("Acme");

            ProductoDisponible dinamita = new ProductoDisponible("1251", "Es dinamita", EXPLOSIVOS, acme, 250.0);
            ProductoDisponible ropa = new ProductoDisponible("1350", "Es ropa deportiva", DEPORTIVA, comarca, 500.0);

            ArrayList<ProductoDisponible> productos = new ArrayList<ProductoDisponible>();
            productos.add(dinamita);
            productos.add(ropa);

            PromocionDeProducto promocionActivaAcme = new PromocionDeProducto(LocalDate.now().minusDays(5), LocalDate.now().plusDays(5), acme);
            ArrayList<Promocion> promociones = new ArrayList<Promocion>();
            promociones.add(promocionActivaAcme);

            ServicioTarjetaDeCredito tarjetaServicio = new ServicioTarjetaDeCredito();

            Carrito miCarrito = new Carrito(santi, productos, promociones, tarjetaServicio);

            // Ejercitación
            double montoTotalConDescuentoAcme = miCarrito.calcularMontoConDescuentos(new TarjetaDeCredito());

            // Verificación
            Assert.assertEquals("true",737.5, montoTotalConDescuentoAcme,00);

        }catch(Exception e){
            System.out.println(e.getMessage());
        }

    }

    @Test
    public void calcularMontoTotalConDescuentoPorTarjeta(){
        try {
            // Inicialización
            ArrayList<TarjetaDeCredito> tarjetas = new ArrayList<TarjetaDeCredito>();
            Cliente santi = new Cliente("Santiago", "Fernández San Juan", "43217767", "santi@gmail.com", tarjetas);

            Marca comarca = new Marca("Comarca");
            Marca acme = new Marca("Acme");

            ProductoDisponible dinamita = new ProductoDisponible("1251", "Es dinamita", EXPLOSIVOS, acme, 250.0);
            ProductoDisponible ropa = new ProductoDisponible("1350", "Es ropa deportiva", DEPORTIVA, comarca, 500.0);

            ArrayList<ProductoDisponible> productos = new ArrayList<ProductoDisponible>();
            productos.add(dinamita);
            productos.add(ropa);

            TarjetaDeCredito miTarjeta = new TarjetaDeCredito();

            PromocionDeCompra promocionMemeCard = new PromocionDeCompra(LocalDate.now().minusDays(5), LocalDate.now().plusDays(5), miTarjeta);
            ArrayList<Promocion> promociones = new ArrayList<Promocion>();
            promociones.add(promocionMemeCard);

            ServicioTarjetaDeCredito tarjetaServicio = new ServicioTarjetaDeCredito();

            Carrito miCarrito = new Carrito(santi, productos, promociones, tarjetaServicio);

            // Ejercitación
            double montoTotalConDescuentoPorTarjeta = miCarrito.calcularMontoConDescuentos(miTarjeta);

            // Verificación
            Assert.assertEquals("true",690.0, montoTotalConDescuentoPorTarjeta,00);

        }catch(Exception e){
            System.out.println(e.getMessage());
        }

    }

    @Test
    public void calcularMontoTotalConMultiplesDescuentos(){
        try {
            // Inicialización
            ArrayList<TarjetaDeCredito> tarjetas = new ArrayList<TarjetaDeCredito>();
            Cliente santi = new Cliente("Santiago", "Fernández San Juan", "43217767", "santi@gmail.com", tarjetas);

            Marca comarca = new Marca("Comarca");
            Marca acme = new Marca("Acme");

            ProductoDisponible dinamita = new ProductoDisponible("1251", "Es dinamita", EXPLOSIVOS, acme, 250.0);
            ProductoDisponible ropa = new ProductoDisponible("1350", "Es ropa deportiva", DEPORTIVA, comarca, 500.0);

            ArrayList<ProductoDisponible> productos = new ArrayList<ProductoDisponible>();
            productos.add(dinamita);
            productos.add(ropa);

            TarjetaDeCredito miTarjeta = new TarjetaDeCredito();

            PromocionDeCompra promocionMemeCard = new PromocionDeCompra(LocalDate.now().minusDays(5), LocalDate.now().plusDays(5), miTarjeta);
            PromocionDeProducto promocionActivaComarca = new PromocionDeProducto(LocalDate.now().minusDays(5), LocalDate.now().plusDays(5), comarca);
            ArrayList<Promocion> promociones = new ArrayList<Promocion>();
            promociones.add(promocionMemeCard);
            promociones.add(promocionActivaComarca);

            ServicioTarjetaDeCredito tarjetaServicio = new ServicioTarjetaDeCredito();

            Carrito miCarrito = new Carrito(santi, productos, promociones, tarjetaServicio);

            // Ejercitación
            double montoTotalConDescuentoPorTarjeta = miCarrito.calcularMontoConDescuentos(miTarjeta);

            // Verificación
            Assert.assertEquals("true",665.0, montoTotalConDescuentoPorTarjeta,00);

        }catch(Exception e){
            System.out.println(e.getMessage());
        }

    }

    @Test
    public void generarVenta(){
        try {
            // Inicialización
            ArrayList<TarjetaDeCredito> tarjetas = new ArrayList<TarjetaDeCredito>();
            Cliente santi = new Cliente("Santiago", "Fernández San Juan", "43217767", "santi@gmail.com", tarjetas);

            Marca acme = new Marca("Acme");

            ProductoDisponible dinamita = new ProductoDisponible("1251", "Es dinamita", EXPLOSIVOS, acme, 250.0);

            ArrayList<ProductoDisponible> productos = new ArrayList<ProductoDisponible>();
            productos.add(dinamita);

            ArrayList<Promocion> promociones = new ArrayList<Promocion>();


            ServicioTarjetaDeCredito tarjetaServicio = new ServicioTarjetaDeCredito();

            Carrito miCarrito = new Carrito(santi, productos, promociones, tarjetaServicio);

            // Ejercitación
            Venta miCompra = miCarrito.realizarCompra(new TarjetaDeCredito());

            // Verificación
            Assert.assertEquals("true",250.0, miCompra.montoTotal(),00);
            Assert.assertEquals("true",1, miCompra.cantidadDeProductos(),00);

        }catch(Exception e){
            System.out.println(e.getMessage());
        }

    }

    @Test
    public void generarProductoInvalido(){
        // Inicialización
        Marca acme = new Marca("Acme");
        boolean saltoExcepcion = false;

        // Ejercitación
        try{
            new ProductoDisponible(null, "Es dinamita", EXPLOSIVOS, acme, 250.0);
        }catch(ProductoInvalidoExcepcion e){
            //System.out.println(e.getMessage());
            saltoExcepcion = true;
        }
        // Verificación
        Assert.assertEquals("true",true, saltoExcepcion);

    }

    @Test
    public void generarClienteInvalido(){
        // Inicialización
        boolean saltoExcepcion = false;
        Cliente santi;

        // Ejercitación
        try{
            new Cliente("", "Fernández San Juan", "43217767", "santi@gmail.com", null);
        }catch(ClienteInvalidoExcepcion | EmailInvalidoExcepcion e){
            //System.out.println(e.getMessage());
            saltoExcepcion = true;
        }

        // Verificación
        Assert.assertEquals("true",true, saltoExcepcion);

    }

    @Test
    public void generarClienteConEmailInvalido(){
        // Inicialización
        boolean saltoExcepcion = false;

        // Ejercitación
        try{
            new Cliente("Santiago", "Fernández San Juan", "43217767", "emailInvalido", null);
        }catch(ClienteInvalidoExcepcion | EmailInvalidoExcepcion e){
            //System.out.println(e.getMessage());
            saltoExcepcion = true;
        }
        // Verificación
        Assert.assertEquals("true",true, saltoExcepcion);

    }

    @Test
    public void generarPromocionConFechaInvalida(){
        // Inicialización
        boolean saltoExcepcion = false;

        // Ejercitación
        try{
            new PromocionDeCompra(LocalDate.now().plusDays(5),LocalDate.now().minusDays(5) , null);
        } catch (FechaInvalidaExcepcion e) {
            //System.out.println(e.getMessage());
            saltoExcepcion = true;
        }

        // Verificación
        Assert.assertEquals("true",true, saltoExcepcion);

    }*/

}
