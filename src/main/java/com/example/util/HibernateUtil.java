package com.example.util;

import com.example.classes.*;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();

                // Configuration via application.properties
                configuration.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
                configuration.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/gestion_projets");
                configuration.setProperty("hibernate.connection.username", "root");
                configuration.setProperty("hibernate.connection.password", "");
                configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
                configuration.setProperty("hibernate.show_sql", "true");
                configuration.setProperty("hibernate.hbm2ddl.auto", "update");

                // Ajouter toutes les classes entités
                configuration.addAnnotatedClass(Employe.class);
                configuration.addAnnotatedClass(Projet.class);
                configuration.addAnnotatedClass(Tache.class);
                configuration.addAnnotatedClass(EmployeTache.class);

                sessionFactory = configuration.buildSessionFactory();
                System.out.println("✅ Hibernate configuré avec succès !");
            } catch (Exception e) {
                System.err.println("❌ Erreur configuration Hibernate: " + e.getMessage());
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
        return sessionFactory;
    }

    public static void shutdown() {
        System.out.println("🔚 Arrêt d'Hibernate...");
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}