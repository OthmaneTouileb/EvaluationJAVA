package ma.projet.service;

import ma.projet.classes.Tache;
import ma.projet.classes.EmployeTache;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.Date;
import java.util.List;

public class TacheService implements IDao<Tache> {
    
    @Override
    public boolean create(Tache o) {
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
    public boolean update(Tache o) {
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
    public boolean delete(Tache o) {
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
    public Tache findById(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.get(Tache.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }
    
    @Override
    public List<Tache> findAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.createQuery("FROM Tache", Tache.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }
    
    // Afficher les tâches dont le prix est supérieur à 1000 DH (requête nommée)
    public void afficherTachesPrixSuperieur(double prix) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Query<Tache> query = session.createNamedQuery("Tache.findByPrixSuperieur", Tache.class);
            query.setParameter("prix", prix);
            List<Tache> taches = query.list();
            
            System.out.println("Tâches dont le prix est supérieur à " + prix + " DH :");
            System.out.println("Num\tNom\t\tDate Début\tDate Fin\tPrix");
            
            for (Tache tache : taches) {
                System.out.println(tache.getId() + "\t" + tache.getNom() + "\t\t" + 
                                 tache.getDateDebut() + "\t" + tache.getDateFin() + "\t" + tache.getPrix());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
    
    // Afficher les tâches réalisées entre deux dates
    public void afficherTachesEntreDates(Date dateDebut, Date dateFin) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Query<EmployeTache> query = session.createQuery(
                "SELECT et FROM EmployeTache et WHERE et.dateDebutReelle >= :dateDebut AND et.dateFinReelle <= :dateFin",
                EmployeTache.class
            );
            query.setParameter("dateDebut", dateDebut);
            query.setParameter("dateFin", dateFin);
            List<EmployeTache> employeTaches = query.list();
            
            System.out.println("Tâches réalisées entre " + dateDebut + " et " + dateFin + " :");
            System.out.println("Num\tNom\t\tEmployé\t\tDate Début Réelle\tDate Fin Réelle");
            
            for (EmployeTache et : employeTaches) {
                Tache tache = et.getTache();
                System.out.println(tache.getId() + "\t" + tache.getNom() + "\t\t" + 
                                 et.getEmploye().getNom() + " " + et.getEmploye().getPrenom() + "\t\t" + 
                                 et.getDateDebutReelle() + "\t" + et.getDateFinReelle());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}

