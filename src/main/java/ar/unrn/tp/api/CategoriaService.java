package ar.unrn.tp.api;

import java.util.List;

public interface CategoriaService {
    void crearCategoria(String nombre);

    List listar();

}
