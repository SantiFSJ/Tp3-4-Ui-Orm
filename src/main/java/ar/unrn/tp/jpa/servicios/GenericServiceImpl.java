package ar.unrn.tp.jpa.servicios;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import java.util.function.Consumer;
import java.util.function.Function;

public abstract class GenericServiceImpl {
    protected EntityManagerFactory emf;


    public GenericServiceImpl(EntityManagerFactory emf){
        this.emf = emf;
    }
    private void setUp(){
        //this.emf = Persistence.createEntityManagerFactory("objectdb:myDbTestFile.tmp");
    }

    public void inTransactionExecute(Consumer<EntityManager> bloqueDeCodigo) {
        this.setUp();
        EntityManager em = this.emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            bloqueDeCodigo.accept(em);
            tx.commit();

        } catch (Exception e) {
            tx.rollback();
            throw e;
        } finally {
            if (em != null && em.isOpen())
                em.close();
        }
    }

    public void tearDown() {
        this.emf.close();
    }
}
