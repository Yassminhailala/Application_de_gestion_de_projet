package com.example;

import com.example.classes.*;
import com.example.service.*;
import com.example.util.HibernateUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class App {
    public static void main(String[] args) {
        try {
            // Initialisation Hibernate
            HibernateUtil.getSessionFactory();

            // Création des services
            EmployeService employeService = new EmployeService();
            ProjetService projetService = new ProjetService();
            TacheService tacheService = new TacheService();
            EmployeTacheService employeTacheService = new EmployeTacheService();

            System.out.println("=== APPLICATION DE GESTION DE PROJETS ===");

            // TEST 1: Création d'employés
            System.out.println("\n1. CRÉATION D'EMPLOYÉS");
            Employe emp1 = new Employe("Dupont", "Jean", "0612345678");
            Employe emp2 = new Employe("Martin", "Marie", "0698765432");
            employeService.create(emp1);
            employeService.create(emp2);
            System.out.println("✅ Employés créés");

            // TEST 2: Création de projets
            System.out.println("\n2. CRÉATION DE PROJETS");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Projet proj1 = new Projet("Gestion de stock", sdf.parse("2025-01-14"), sdf.parse("2025-06-30"));
            Projet proj2 = new Projet("Site E-commerce", sdf.parse("2025-02-01"), sdf.parse("2025-08-15"));
            projetService.create(proj1);
            projetService.create(proj2);
            System.out.println("✅ Projets créés");

            // TEST 3: Création de tâches
            System.out.println("\n3. CRÉATION DE TÂCHES");
            Tache tache1 = new Tache("Analyse", sdf.parse("2025-02-10"), sdf.parse("2025-02-20"), 1500.0, proj1);
            Tache tache2 = new Tache("Conception", sdf.parse("2025-03-10"), sdf.parse("2025-03-15"), 1200.0, proj1);
            Tache tache3 = new Tache("Développement", sdf.parse("2025-04-10"), sdf.parse("2025-04-25"), 3000.0, proj1);
            Tache tache4 = new Tache("Test", sdf.parse("2025-05-01"), sdf.parse("2025-05-10"), 800.0, proj1);

            tacheService.create(tache1);
            tacheService.create(tache2);
            tacheService.create(tache3);
            tacheService.create(tache4);
            System.out.println("✅ Tâches créées");

            // TEST 4: Association employés-tâches
            System.out.println("\n4. ASSOCIATION EMPLOYÉS-TÂCHES");
            employeTacheService.createEmployeTache(sdf.parse("2025-02-10"), sdf.parse("2025-02-20"), emp1, tache1);
            employeTacheService.createEmployeTache(sdf.parse("2025-03-10"), sdf.parse("2025-03-15"), emp1, tache2);
            employeTacheService.createEmployeTache(sdf.parse("2025-04-10"), sdf.parse("2025-04-25"), emp2, tache3);
            System.out.println("✅ Associations créées");

            // TEST 5: Tâches réalisées par un employé
            System.out.println("\n5. TÂCHES RÉALISÉES PAR EMPLOYÉ (Jean Dupont)");
            List<Tache> tachesEmp1 = employeService.getTachesRealiseesByEmploye(emp1);
            for (Tache t : tachesEmp1) {
                System.out.println(" - " + t.getNom() + " (" + t.getPrix() + " DH)");
            }

            // TEST 6: Projets gérés par un employé
            System.out.println("\n6. PROJETS GÉRÉS PAR EMPLOYÉ (Jean Dupont)");
            List<Projet> projetsEmp1 = employeService.getProjetsByEmploye(emp1);
            for (Projet p : projetsEmp1) {
                System.out.println(" - " + p.getNom());
            }

            // TEST 7: Tâches planifiées pour un projet
            System.out.println("\n7. TÂCHES PLANIFIÉES POUR PROJET (Gestion de stock)");
            List<Tache> tachesProj1 = projetService.getTachesPlanifieesByProjet(proj1);
            for (Tache t : tachesProj1) {
                System.out.println(" - " + t.getNom() + " | " + t.getDateDebut() + " → " + t.getDateFin());
            }

            // TEST 8: Tâches avec prix > 1000 DH
            System.out.println("\n8. TÂCHES AVEC PRIX > 1000 DH");
            List<Tache> tachesChers = tacheService.getTachesPrixSuperieurA1000();
            for (Tache t : tachesChers) {
                System.out.println(" - " + t.getNom() + " : " + t.getPrix() + " DH");
            }

            // TEST 9: Affichage détail projet (comme dans l'exemple)
            System.out.println("\n9. DÉTAIL DU PROJET (Gestion de stock)");
            projetService.afficherDetailsProjet(proj1);

            System.out.println("\n=== TOUS LES TESTS RÉUSSIS ===");

        } catch (Exception e) {
            System.err.println("❌ Erreur : " + e.getMessage());
            e.printStackTrace();
        } finally {
            HibernateUtil.shutdown();
        }
    }
}