package com.example.service;

import com.example.classes.Projet;
import com.example.classes.Tache;
import com.example.classes.EmployeTache;
import com.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;
import java.util.List;

public class ProjetService extends Service<Projet> {

    public ProjetService() {
        super(Projet.class);
    }

    // 1. Liste des tâches planifiées pour un projet
    public List<Tache> getTachesPlanifieesByProjet(Projet projet) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Query<Tache> query = session.createQuery(
                    "FROM Tache t WHERE t.projet = :projet", Tache.class);
            query.setParameter("projet", projet);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (session != null) session.close();
        }
    }

    // 2. Liste des tâches réalisées avec dates réelles
    public List<Object[]> getTachesRealiseesWithDatesReelles(Projet projet) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Query<Object[]> query = session.createQuery(
                    "SELECT et.tache, et.dateDebutReelle, et.dateFinReelle " +
                            "FROM EmployeTache et WHERE et.tache.projet = :projet", Object[].class);
            query.setParameter("projet", projet);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (session != null) session.close();
        }
    }

    // Méthode pour afficher les détails comme dans l'exemple
    public void afficherDetailsProjet(Projet projet) {
        List<Object[]> tachesAvecDates = getTachesRealiseesWithDatesReelles(projet);

        System.out.println("Projet : " + projet.getId() + "\tNom : " + projet.getNom() +
                "\tDate début : " + projet.getDateDebut());
        System.out.println("Liste des tâches:");
        System.out.println("Num\tNom\t\tDate Début Réelle\tDate Fin Réelle");

        for (Object[] result : tachesAvecDates) {
            Tache tache = (Tache) result[0];
            java.util.Date dateDebutReelle = (java.util.Date) result[1];
            java.util.Date dateFinReelle = (java.util.Date) result[2];

            System.out.println(tache.getId() + "\t" + tache.getNom() + "\t\t" +
                    dateDebutReelle + "\t" + dateFinReelle);
        }
    }
}