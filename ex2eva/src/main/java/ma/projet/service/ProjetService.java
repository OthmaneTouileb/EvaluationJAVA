package ma.projet.service;

import ma.projet.classes.Projet;
import ma.projet.classes.Tache;
import ma.projet.classes.EmployeTache;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class ProjetService implements IDao<Projet> {
    
    @Override
    public boolean create(Projet o) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(o);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
    }
    
    @Override
    public boolean update(Projet o) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.update(o);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
    }
    
    @Override
    public boolean delete(Projet o) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.delete(o);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
    }
    
    @Override
    public Projet findById(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.get(Projet.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }
    
    @Override
    public List<Projet> findAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.createQuery("FROM Projet", Projet.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }
    
    // Afficher la liste des tâches planifiées pour un projet
    public void afficherTachesPlanifiees(int projetId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Projet projet = session.get(Projet.class, projetId);
            if (projet != null) {
                System.out.println("Projet : " + projet.getId() + "\tNom : " + projet.getNom());
                System.out.println("Liste des tâches planifiées :");
                System.out.println("Num\tNom\t\tDate Début\tDate Fin\tPrix");
                
                for (Tache tache : projet.getTaches()) {
                    System.out.println(tache.getId() + "\t" + tache.getNom() + "\t\t" + 
                                     tache.getDateDebut() + "\t" + tache.getDateFin() + "\t" + tache.getPrix());
                }
            } else {
                System.out.println("Projet non trouvé !");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
    
    // Afficher la liste des tâches réalisées avec les dates réelles
    public void afficherTachesRealisees(int projetId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Projet projet = session.get(Projet.class, projetId);
            if (projet != null) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.FRENCH);
                SimpleDateFormat dateFormatSimple = new SimpleDateFormat("dd/MM/yyyy");
                
                System.out.println("Projet : " + projet.getId() + 
                                 "\tNom : " + projet.getNom() + 
                                 "\tDate début : " + dateFormat.format(projet.getDateDebut()));
                System.out.println("\nListe des tâches:");
                System.out.println("Num\tNom\t\t\tDate Début Réelle\tDate Fin Réelle");
                
                for (Tache tache : projet.getTaches()) {
                    for (EmployeTache et : tache.getEmployeTaches()) {
                        System.out.println(tache.getId() + "\t" + 
                                         String.format("%-15s", tache.getNom()) + "\t" + 
                                         dateFormatSimple.format(et.getDateDebutReelle()) + "\t\t" + 
                                         dateFormatSimple.format(et.getDateFinReelle()));
                    }
                }
            } else {
                System.out.println("Projet non trouvé !");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}

