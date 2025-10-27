package com.example.service;

import com.example.classes.Employe;
import com.example.classes.Tache;
import com.example.classes.EmployeTache;
import com.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.Date;
import java.util.List;

public class EmployeTacheService extends Service<EmployeTache> {

    public EmployeTacheService() {
        super(EmployeTache.class);
    }

    // Méthode spécifique pour créer une association employé-tâche
    public EmployeTache createEmployeTache(Date dateDebutReelle, Date dateFinReelle,
                                           Employe employe, Tache tache) {
        EmployeTache employeTache = new EmployeTache(dateDebutReelle, dateFinReelle, employe, tache);
        return create(employeTache);
    }
}