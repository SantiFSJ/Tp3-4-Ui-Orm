package ar.unrn.tp.modelo;

import ar.unrn.tp.excepciones.ProductoInvalidoExcepcion;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@Entity
@NoArgsConstructor
@Table(name = "Producto", uniqueConstraints = @UniqueConstraint(columnNames = "codigo"))
public class ProductoDisponible extends ModeloGenerico {

    @Column( name="codigo",unique = true)
    private String codigo;
    private String descripcion;
    @ManyToOne
    private Categoria categoria;
    @ManyToOne
    private Marca marca;
    private Double precio;

    @Version
    private Long version;

    public ProductoDisponible(String codigo, String descripcion, Categoria categoria, Marca marca, Double precio) throws RuntimeException, ProductoInvalidoExcepcion {
        this.validarProducto(codigo,descripcion,categoria,marca,precio);
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.marca = marca;
        this.precio = precio;
    }
    private void validarProducto(String codigo, String descripcion, Categoria categoria, Marca marca, Double precio) throws RuntimeException, ProductoInvalidoExcepcion {
        if(codigo == null
                || codigo.equals("")
                || descripcion == null
                || descripcion.equals("")
                || categoria == null
                || marca == null
                || precio == null){
            throw new ProductoInvalidoExcepcion();
        }
    }
    public double precio(){
        return this.precio;
    }
    private String codigo() {
        return codigo;
    }

    public void actualizarDescripcion(String descripcion){
        this.descripcion = descripcion;
    }

    public void actualizarCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void actualizarCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public void actualizarMarca(Marca marca) {
        this.marca = marca;
    }

    public void actualizarPrecio(Double precio) {
        this.precio = precio;
    }

    public ProductoVendido obtenerVendido() throws ProductoInvalidoExcepcion {
        return new ProductoVendido(this.codigo,this.descripcion,this.categoria,this.marca,this.precio);
    }

    private String descripcion(){
        return this.descripcion;
    }
    public boolean sosIgualA(ProductoDisponible producto){
        return this.codigo.equals(producto.codigo()) &&
                this.precio == producto.precio() &&
                this.descripcion .equals(producto.descripcion());
    }

    public boolean esDeMarca(String marca){
        return this.marca.equals(marca);
    }
}
