package org.example.DAO;

import org.example.model.Coche;
import org.hibernate.Session;

import java.util.List;

public class CocheDaoImpl implements CocheDAO {

    @Override
    public  boolean crearCoche(Session session, Coche coche) {
        boolean crearcoche=true;
        try{
            session.beginTransaction();
            session.save(coche);
            session.getTransaction().commit();
        }catch (Exception e) {
            crearcoche=false;
            if (session.getTransaction() != null) {
                session.getTransaction().rollback(); // Revertir cambios en caso de error
            }
        }
        return crearcoche;
    }

   @Override
    public  boolean eliminarCoche(Session session, Coche coche) {
        boolean eliminarcoche=true;
        try {
            session.beginTransaction();
            session.delete(coche);
            session.getTransaction().commit();
        }catch (Exception e) {
            eliminarcoche=false;
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();// Revertir cambios en caso de error
            }
        }
        return eliminarcoche;
    }//eliminarCoche

  @Override
    public  boolean actualizarCoche(Session session, Coche coche) {
        boolean actualizarcoche=true;
        try {
            session.beginTransaction();
            session.update(coche);
            session.getTransaction().commit();
        }catch (Exception e) {
            actualizarcoche=false;
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();// Revertir cambios en caso de error
            }
        }
        return actualizarcoche;

    }

   @Override
    public  List<Coche> listarCoches(Session session) {
        session.beginTransaction();
        List<Coche> listaCoches;
        listaCoches = session.createQuery("from Coche ", Coche.class).list();
        session.getTransaction().commit();

        return listaCoches;
    }

    /*

    public Coche listarCochePorMatricula(Session session,String matricula){
        return (Coche) session.createQuery("FROM Coche WHERE matricula = "+matricula).uniqueResult();

    }

     */
}
