package org.example.DAO;

import org.example.model.Coche;
import org.example.model.Multa;
import org.hibernate.Session;

import java.util.List;

public class MultaDAOImpl implements MultaDAO{
    @Override
    public  boolean crearMulta(Session session, Multa multa) {
        boolean crearcoche=true;
        try{
            session.beginTransaction();
            session.save(multa);
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
    public  boolean eliminarMulta(Session session, Multa multa) {
        boolean eliminarcoche=true;
        try {
            session.beginTransaction();
            session.delete(multa);
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
    public  boolean actualizarMulta(Session session, Multa multa) {
        boolean actualizarcoche=true;
        try {
            session.beginTransaction();
            session.update(multa);
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
    public List<Multa> listarMultaCoche(Session session, String matricula) {
        session.beginTransaction();
        List<Multa> listamultas;
        listamultas = session.createQuery("from multas where matricula=" + matricula, Multa.class).list();
        session.getTransaction().commit();

        return listamultas;
    }
}
