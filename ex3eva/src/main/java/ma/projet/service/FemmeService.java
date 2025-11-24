package ma.projet.service;

import ma.projet.beans.Femme;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.List;

public class FemmeService implements IDao<Femme> {
    
    @Override
    public boolean create(Femme o) {
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
    public boolean update(Femme o) {
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
    public boolean delete(Femme o) {
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
    public Femme findById(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.get(Femme.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }
    
    @Override
    public List<Femme> findAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.createQuery("FROM Femme", Femme.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }
    
    /**
     * Requête native nommée retournant le nombre d'enfants d'une femme entre deux dates
     */
    public long getNombreEnfantsEntreDates(int femmeId, Date dateDebut, Date dateFin) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            NativeQuery<?> query = session.getNamedNativeQuery("Femme.countEnfantsBetweenDates");
            query.setParameter("femmeId", femmeId);
            query.setParameter("dateDebut", dateDebut);
            query.setParameter("dateFin", dateFin);
            List<?> results = query.getResultList();
            if (results != null && !results.isEmpty() && results.get(0) != null) {
                return ((Number) results.get(0)).longValue();
            }
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        } finally {
            session.close();
        }
    }
    
    /**
     * Requête nommée retournant les femmes mariées au moins deux fois
     */
    public List<Femme> getFemmesMarieesAuMoinsDeuxFois() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Query<Femme> query = session.createNamedQuery("Femme.findMarriedAtLeastTwice", Femme.class);
            List<Femme> femmes = query.list();
            // Initialiser les collections pour éviter LazyInitializationException
            for (Femme f : femmes) {
                f.getMariages().size(); // Force l'initialisation de la collection
            }
            return femmes;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }
    
    /**
     * API Criteria pour afficher le nombre d'hommes mariés à quatre femmes entre deux dates
     */
    public long getNombreHommesMariesQuatreFemmesEntreDates(Date dateDebut, Date dateFin) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            
            // Sous-requête pour trouver les hommes avec exactement 4 mariages
            CriteriaQuery<Integer> subQuery = cb.createQuery(Integer.class);
            Root<ma.projet.beans.Mariage> subMariage = subQuery.from(ma.projet.beans.Mariage.class);
            Join<ma.projet.beans.Mariage, ma.projet.beans.Homme> subHomme = subMariage.join("homme");
            
            subQuery.select(subHomme.get("id"))
                    .where(
                        cb.and(
                            cb.greaterThanOrEqualTo(subMariage.get("dateDebut"), dateDebut),
                            cb.or(
                                cb.isNull(subMariage.get("dateFin")),
                                cb.lessThanOrEqualTo(subMariage.get("dateFin"), dateFin)
                            )
                        )
                    )
                    .groupBy(subHomme.get("id"))
                    .having(cb.equal(cb.count(subMariage.get("id")), 4L));
            
            // Requête principale pour compter les hommes
            CriteriaQuery<Long> mainQuery = cb.createQuery(Long.class);
            Root<ma.projet.beans.Mariage> mariage = mainQuery.from(ma.projet.beans.Mariage.class);
            Join<ma.projet.beans.Mariage, ma.projet.beans.Homme> homme = mariage.join("homme");
            
            mainQuery.select(cb.countDistinct(homme.get("id")))
                     .where(homme.get("id").in(subQuery));
            
            Query<Long> query = session.createQuery(mainQuery);
            Long result = query.uniqueResult();
            return result != null ? result : 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        } finally {
            session.close();
        }
    }
    
    /**
     * Trouver la femme la plus âgée
     */
    public Femme getFemmeLaPlusAgee() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Query<Femme> query = session.createQuery(
                "FROM Femme ORDER BY dateNaissance ASC", 
                Femme.class
            );
            query.setMaxResults(1);
            return query.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }
}

