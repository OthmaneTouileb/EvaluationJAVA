package ma.projet.service;

import ma.projet.beans.Homme;
import ma.projet.beans.Mariage;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class HommeService implements IDao<Homme> {
    
    @Override
    public boolean create(Homme o) {
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
    public boolean update(Homme o) {
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
    public boolean delete(Homme o) {
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
    public Homme findById(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.get(Homme.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }
    
    @Override
    public List<Homme> findAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.createQuery("FROM Homme", Homme.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }
    
    /**
     * Afficher les épouses d'un homme entre deux dates
     */
    public List<Mariage> getEpousesEntreDates(int hommeId, Date dateDebut, Date dateFin) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Homme homme = session.get(Homme.class, hommeId);
            if (homme == null) return null;
            
            return session.createQuery(
                "FROM Mariage m WHERE m.homme.id = :hommeId " +
                "AND m.dateDebut >= :dateDebut " +
                "AND (m.dateFin IS NULL OR m.dateFin <= :dateFin)",
                Mariage.class
            )
            .setParameter("hommeId", hommeId)
            .setParameter("dateDebut", dateDebut)
            .setParameter("dateFin", dateFin)
            .list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }
    
    /**
     * Afficher les mariages d'un homme avec tous les détails
     */
    public void afficherMariagesAvecDetails(int hommeId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Homme homme = session.get(Homme.class, hommeId);
            if (homme == null) {
                System.out.println("Homme non trouvé!");
                return;
            }
            
            System.out.println("Nom : " + homme.getNom() + " " + homme.getPrenom());
            
            // Récupérer tous les mariages
            List<Mariage> mariages = session.createQuery(
                "FROM Mariage m WHERE m.homme.id = :hommeId ORDER BY m.dateDebut",
                Mariage.class
            )
            .setParameter("hommeId", hommeId)
            .list();
            
            // Séparer les mariages en cours et échoués
            List<Mariage> mariagesEnCours = mariages.stream()
                .filter(Mariage::isEnCours)
                .collect(Collectors.toList());
            
            List<Mariage> mariagesEchoues = mariages.stream()
                .filter(m -> !m.isEnCours())
                .collect(Collectors.toList());
            
            // Afficher les mariages en cours
            System.out.println("\nMariages En Cours :");
            int index = 1;
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
            for (Mariage m : mariagesEnCours) {
                String nomComplet = m.getFemme().getNom() + " " + m.getFemme().getPrenom();
                String dateDebut = sdf.format(m.getDateDebut());
                // Formatage exact comme dans l'exemple
                System.out.println(index + ". Femme : " + nomComplet + "   Date Début : " + dateDebut + "    Nbr Enfants : " + m.getNbrEnfant());
                index++;
            }
            
            // Afficher les mariages échoués
            System.out.println("\nMariages échoués :");
            index = 1;
            for (Mariage m : mariagesEchoues) {
                String nomComplet = m.getFemme().getNom() + " " + m.getFemme().getPrenom();
                String dateDebut = sdf.format(m.getDateDebut());
                String dateFin = sdf.format(m.getDateFin());
                // Formatage exact comme dans l'exemple
                System.out.println(index + ". Femme : " + nomComplet + "  Date Début : " + dateDebut + "    Date Fin : " + dateFin + "    Nbr Enfants : " + m.getNbrEnfant());
                index++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}

