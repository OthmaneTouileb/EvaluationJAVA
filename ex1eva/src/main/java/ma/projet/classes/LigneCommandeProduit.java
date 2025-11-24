package ma.projet.classes;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ligne_commande_produit")
public class LigneCommandeProduit implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(name = "quantite", nullable = false)
    private int quantite;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "produit_id", nullable = false)
    private Produit produit;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "commande_id", nullable = false)
    private Commande commande;
    
    public LigneCommandeProduit() {
    }
    
    public LigneCommandeProduit(int quantite, Produit produit, Commande commande) {
        this.quantite = quantite;
        this.produit = produit;
        this.commande = commande;
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getQuantite() {
        return quantite;
    }
    
    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }
    
    public Produit getProduit() {
        return produit;
    }
    
    public void setProduit(Produit produit) {
        this.produit = produit;
    }
    
    public Commande getCommande() {
        return commande;
    }
    
    public void setCommande(Commande commande) {
        this.commande = commande;
    }
    
    @Override
    public String toString() {
        return "LigneCommandeProduit{" +
                "id=" + id +
                ", quantite=" + quantite +
                ", produit=" + produit +
                ", commande=" + commande +
                '}';
    }
}


