package com.example.service;

import com.example.classes.Employe;
import com.example.classes.Projet;
import com.example.classes.Tache;
import com.example.classes.EmployeTache;
import com.example.util.HibernateUtil; // ✅ Import ajouté
import org.hibernate.Session;
import org.hibernate.query.Query;
import java.util.List;

public class EmployeService extends Service<Employe> {

    public EmployeService() {
        super(Employe.class);
    }

    // 1. Liste des tâches réalisées par un employé
    public List<Tache> getTachesRealiseesByEmploye(Employe employe) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession(); // ✅ Corrigé
            Query<Tache> query = session.createQuery(
                    "SELECT et.tache FROM EmployeTache et WHERE et.employe = :employe", Tache.class);
            query.setParameter("employe", employe);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (session != null) session.close();
        }
    }

    // 2. Liste des projets gérés par un employé
    public List<Projet> getProjetsByEmploye(Employe employe) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession(); // ✅ Corrigé
            Query<Projet> query = session.createQuery(
                    "SELECT DISTINCT et.tache.projet FROM EmployeTache et WHERE et.employe = :employe", Projet.class);
            query.setParameter("employe", employe);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (session != null) session.close();
        }
    }
}
