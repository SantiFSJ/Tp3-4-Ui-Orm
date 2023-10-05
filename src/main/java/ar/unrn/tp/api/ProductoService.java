package ar.unrn.tp.api;

import ar.unrn.tp.modelo.ProductoDisponible;

import java.util.List;

public interface ProductoService {
    //validar que sea una categoría existente y que codigo no se repita
    void crearProducto(String codigo, String descripcion, double precio, Long idCategoría, Long idMarca);


    //validar que sea un producto existente
    void modificarProducto(Long idProducto, String codigo, String descripcion, double precio, Long idCategoría, Long idMarca, Long version);
    //Devuelve todos los productos
    List listarProductos();

    ProductoDisponible hallarProducto(Long idProducto);

}
