package ma.projet.test;

import ma.projet.classes.*;
import ma.projet.service.*;
import ma.projet.util.HibernateUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class TestApplication {
    
    public static void main(String[] args) {
        try {
            // Initialiser Hibernate
            HibernateUtil.getSessionFactory();
            
            // Créer les services
            EmployeService employeService = new EmployeService();
            ProjetService projetService = new ProjetService();
            TacheService tacheService = new TacheService();
            EmployeTacheService employeTacheService = new EmployeTacheService();
            
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            
            // Test 1: Créer des employés
            System.out.println("=== Test 1: Création d'employés ===");
            Employe emp1 = new Employe("Alami", "Ahmed", "0612345678");
            Employe emp2 = new Employe("Benali", "Fatima", "0623456789");
            employeService.create(emp1);
            employeService.create(emp2);
            System.out.println("Employés créés avec succès !\n");
            
            // Test 2: Créer des projets
            System.out.println("=== Test 2: Création de projets ===");
            Projet projet1 = new Projet("Gestion de stock", 
                                       sdf.parse("2013-01-14"), 
                                       sdf.parse("2013-06-30"), 
                                       emp1);
            Projet projet2 = new Projet("Système de paiement", 
                                       sdf.parse("2013-02-01"), 
                                       sdf.parse("2013-08-31"), 
                                       emp1);
            projetService.create(projet1);
            projetService.create(projet2);
            System.out.println("Projets créés avec succès !\n");
            
            // Test 3: Créer des tâches
            System.out.println("=== Test 3: Création de tâches ===");
            Tache tache1 = new Tache("Analyse", 
                                    sdf.parse("2013-01-15"), 
                                    sdf.parse("2013-02-20"), 
                                    1500.0, 
                                    projet1);
            Tache tache2 = new Tache("Conception", 
                                    sdf.parse("2013-02-21"), 
                                    sdf.parse("2013-03-15"), 
                                    2000.0, 
                                    projet1);
            Tache tache3 = new Tache("Développement", 
                                    sdf.parse("2013-03-16"), 
                                    sdf.parse("2013-04-25"), 
                                    3000.0, 
                                    projet1);
            Tache tache4 = new Tache("Test", 
                                    sdf.parse("2013-04-26"), 
                                    sdf.parse("2013-05-15"), 
                                    800.0, 
                                    projet1);
            tacheService.create(tache1);
            tacheService.create(tache2);
            tacheService.create(tache3);
            tacheService.create(tache4);
            System.out.println("Tâches créées avec succès !\n");
            
            // Test 4: Créer des associations EmployeTache
            System.out.println("=== Test 4: Création d'associations Employe-Tâche ===");
            EmployeTache et1 = new EmployeTache(sdf.parse("2013-02-10"), 
                                               sdf.parse("2013-02-20"), 
                                               emp1, 
                                               tache1);
            EmployeTache et2 = new EmployeTache(sdf.parse("2013-03-10"), 
                                               sdf.parse("2013-03-15"), 
                                               emp2, 
                                               tache2);
            EmployeTache et3 = new EmployeTache(sdf.parse("2013-04-10"), 
                                               sdf.parse("2013-04-25"), 
                                               emp1, 
                                               tache3);
            employeTacheService.create(et1);
            employeTacheService.create(et2);
            employeTacheService.create(et3);
            System.out.println("Associations créées avec succès !\n");
            
            // Test 5: Afficher les tâches réalisées par un employé
            System.out.println("=== Test 5: Tâches réalisées par un employé ===");
            employeService.afficherTachesRealisees(emp1.getId());
            System.out.println();
            
            // Test 6: Afficher les projets gérés par un employé
            System.out.println("=== Test 6: Projets gérés par un employé ===");
            employeService.afficherProjetsGeres(emp1.getId());
            System.out.println();
            
            // Test 7: Afficher les tâches planifiées pour un projet
            System.out.println("=== Test 7: Tâches planifiées pour un projet ===");
            projetService.afficherTachesPlanifiees(projet1.getId());
            System.out.println();
            
            // Test 8: Afficher les tâches réalisées avec dates réelles
            System.out.println("=== Test 8: Tâches réalisées avec dates réelles ===");
            projetService.afficherTachesRealisees(projet1.getId());
            System.out.println();
            
            // Test 9: Afficher les tâches dont le prix > 1000 DH
            System.out.println("=== Test 9: Tâches avec prix > 1000 DH ===");
            tacheService.afficherTachesPrixSuperieur(1000.0);
            System.out.println();
            
            // Test 10: Afficher les tâches réalisées entre deux dates
            System.out.println("=== Test 10: Tâches réalisées entre deux dates ===");
            tacheService.afficherTachesEntreDates(sdf.parse("2013-02-01"), sdf.parse("2013-03-31"));
            System.out.println();
            
            // Fermer Hibernate
            HibernateUtil.shutdown();
            
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

