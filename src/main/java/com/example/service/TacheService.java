package com.example.service;

import com.example.util.HibernateUtil;
import com.example.classes.Tache;
import com.example.classes.EmployeTache;
import org.hibernate.Session;
import org.hibernate.query.Query;
import java.util.Date;
import java.util.List;

public class TacheService extends Service<Tache> {

    public TacheService() {
        super(Tache.class);
    }

    // 1. Tâches avec prix > 1000 DH (requête nommée)
    public List<Tache> getTachesPrixSuperieurA1000() {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Query<Tache> query = session.createNamedQuery("Tache.findByPrixSuperieurA", Tache.class);
            query.setParameter("prix", 1000.0);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (session != null) session.close();
        }
    }

    // 2. Tâches réalisées entre deux dates - VERSION CORRIGÉE
    public List<Tache> getTachesRealiseesBetweenDates(Date startDate, Date endDate) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Query<Tache> query = session.createQuery(
                    "SELECT DISTINCT et.tache FROM EmployeTache et " +
                            "WHERE et.dateDebutReelle BETWEEN :startDate AND :endDate " +
                            "OR et.dateFinReelle BETWEEN :startDate AND :endDate", Tache.class);
            query.setParameter("startDate", startDate);
            query.setParameter("endDate", endDate);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (session != null) session.close();
        }
    }
}