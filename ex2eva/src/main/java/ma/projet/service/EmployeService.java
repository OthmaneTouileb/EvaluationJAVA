package ma.projet.service;

import ma.projet.classes.Employe;
import ma.projet.classes.EmployeTache;
import ma.projet.classes.Projet;
import ma.projet.classes.Tache;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class EmployeService implements IDao<Employe> {
    
    @Override
    public boolean create(Employe o) {
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
    public boolean update(Employe o) {
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
    public boolean delete(Employe o) {
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
    public Employe findById(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.get(Employe.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }
    
    @Override
    public List<Employe> findAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.createQuery("FROM Employe", Employe.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }
    
    // Afficher la liste des tâches réalisées par un employé
    public void afficherTachesRealisees(int employeId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Employe employe = session.get(Employe.class, employeId);
            if (employe != null) {
                System.out.println("Employé : " + employe.getNom() + " " + employe.getPrenom());
                System.out.println("Liste des tâches réalisées :");
                System.out.println("Num\tNom\t\tDate Début Réelle\tDate Fin Réelle");
                
                for (EmployeTache et : employe.getEmployeTaches()) {
                    Tache tache = et.getTache();
                    System.out.println(tache.getId() + "\t" + tache.getNom() + "\t\t" + 
                                     et.getDateDebutReelle() + "\t" + et.getDateFinReelle());
                }
            } else {
                System.out.println("Employé non trouvé !");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
    
    // Afficher la liste des projets gérés par un employé
    public void afficherProjetsGeres(int employeId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Employe employe = session.get(Employe.class, employeId);
            if (employe != null) {
                System.out.println("Employé : " + employe.getNom() + " " + employe.getPrenom());
                System.out.println("Liste des projets gérés :");
                System.out.println("Num\tNom\t\tDate Début\tDate Fin");
                
                for (Projet projet : employe.getProjets()) {
                    System.out.println(projet.getId() + "\t" + projet.getNom() + "\t\t" + 
                                     projet.getDateDebut() + "\t" + projet.getDateFin());
                }
            } else {
                System.out.println("Employé non trouvé !");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}

