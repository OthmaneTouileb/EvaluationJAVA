package ma.projet.service;

import ma.projet.classes.Commande;
import ma.projet.classes.LigneCommandeProduit;
import ma.projet.classes.Produit;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ProduitService implements IDao<Produit> {
    
    @Override
    public boolean create(Produit o) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(o);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
    }
    
    @Override
    public boolean update(Produit o) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.update(o);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
    }
    
    @Override
    public boolean delete(Produit o) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.delete(o);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
    }
    
    @Override
    public Produit findById(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.get(Produit.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }
    
    @Override
    public List<Produit> findAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.createQuery("FROM Produit", Produit.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }
    
    /**
     * Affiche la liste des produits par catégorie
     * @param categorieId L'ID de la catégorie
     * @return Liste des produits de la catégorie
     */
    public List<Produit> getProduitsByCategorie(int categorieId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.createQuery(
                "SELECT p FROM Produit p WHERE p.categorie.id = :categorieId",
                Produit.class
            ).setParameter("categorieId", categorieId).list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }
    
    /**
     * Affiche les produits commandés entre deux dates
     * @param dateDebut Date de début
     * @param dateFin Date de fin
     * @return Liste des produits commandés dans cette période
     */
    public List<Produit> getProduitsCommandesEntreDates(Date dateDebut, Date dateFin) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.createQuery(
                "SELECT DISTINCT lcp.produit FROM LigneCommandeProduit lcp " +
                "WHERE lcp.commande.date BETWEEN :dateDebut AND :dateFin",
                Produit.class
            )
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
     * Affiche les produits commandés dans une commande donnée
     * Format attendu:
     * Commande : 4     Date : 14 Mars 2013
     * Liste des produits :
     * Référence   Prix    Quantité
     * ES12        120 DH  7
     * ZR85        100 DH  14
     * EE85        200 DH  5
     * @param commandeId L'ID de la commande
     */
    public void afficherProduitsParCommande(int commandeId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Commande commande = session.get(Commande.class, commandeId);
            if (commande == null) {
                System.out.println("Commande introuvable avec l'ID: " + commandeId);
                return;
            }
            
            List<LigneCommandeProduit> lignes = session.createQuery(
                "FROM LigneCommandeProduit lcp WHERE lcp.commande.id = :commandeId",
                LigneCommandeProduit.class
            )
            .setParameter("commandeId", commandeId)
            .list();
            
            // Formatage de la date
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy", java.util.Locale.FRENCH);
            String dateFormatee = dateFormat.format(commande.getDate());
            
            System.out.println("Commande : " + commande.getId() + "     Date : " + dateFormatee);
            System.out.println("Liste des produits :");
            System.out.println("Référence   Prix    Quantité");
            
            for (LigneCommandeProduit ligne : lignes) {
                Produit produit = ligne.getProduit();
                System.out.printf("%-10s  %-6.0f DH  %d%n", 
                    produit.getReference(), 
                    produit.getPrix(), 
                    ligne.getQuantite());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
    
    /**
     * Affiche la liste des produits dont le prix est supérieur à 100 DH
     * en utilisant une requête nommée
     * @param prixMin Prix minimum (par défaut 100)
     * @return Liste des produits avec prix > prixMin
     */
    public List<Produit> getProduitsPrixSuperieur(double prixMin) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.createNamedQuery("Produit.findByPrixSuperieur", Produit.class)
                .setParameter("prix", (float) prixMin)
                .list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }
}

