package ar.unrn.tp.jpa.servicios;

import ar.unrn.tp.api.CategoriaService;
import ar.unrn.tp.excepciones.ClienteInvalidoExcepcion;
import ar.unrn.tp.excepciones.EmailInvalidoExcepcion;
import ar.unrn.tp.modelo.Categoria;

import ar.unrn.tp.modelo.ProductoDisponible;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoriaServiceImpl extends GenericServiceImpl implements CategoriaService{

    public CategoriaServiceImpl(EntityManagerFactory emf){
        super(emf);
    }

    @Override
    public void crearCategoria(String nombre) {
        inTransactionExecute((em) -> {
            em.persist(new Categoria(nombre));
        });
    }

    public List<Categoria> listar(){
        List<Categoria> categorias = new ArrayList<>();
        inTransactionExecute((em) -> {
            categorias.addAll(em.createQuery("SELECT c FROM Categoria c", Categoria.class).getResultList());
        });
        return categorias;
    }

}
