package ma.projet.classes;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "employe_tache")
public class EmployeTache {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Temporal(TemporalType.DATE)
    @Column(name = "date_debut_reelle")
    private Date dateDebutReelle;
    
    @Temporal(TemporalType.DATE)
    @Column(name = "date_fin_reelle")
    private Date dateFinReelle;
    
    // Relation avec Employe
    @ManyToOne
    @JoinColumn(name = "employe_id")
    private Employe employe;
    
    // Relation avec Tache
    @ManyToOne
    @JoinColumn(name = "tache_id")
    private Tache tache;
    
    public EmployeTache() {
    }
    
    public EmployeTache(Date dateDebutReelle, Date dateFinReelle, Employe employe, Tache tache) {
        this.dateDebutReelle = dateDebutReelle;
        this.dateFinReelle = dateFinReelle;
        this.employe = employe;
        this.tache = tache;
    }
    
    // Getters et Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public Date getDateDebutReelle() {
        return dateDebutReelle;
    }
    
    public void setDateDebutReelle(Date dateDebutReelle) {
        this.dateDebutReelle = dateDebutReelle;
    }
    
    public Date getDateFinReelle() {
        return dateFinReelle;
    }
    
    public void setDateFinReelle(Date dateFinReelle) {
        this.dateFinReelle = dateFinReelle;
    }
    
    public Employe getEmploye() {
        return employe;
    }
    
    public void setEmploye(Employe employe) {
        this.employe = employe;
    }
    
    public Tache getTache() {
        return tache;
    }
    
    public void setTache(Tache tache) {
        this.tache = tache;
    }
    
    @Override
    public String toString() {
        return "EmployeTache{" +
                "id=" + id +
                ", dateDebutReelle=" + dateDebutReelle +
                ", dateFinReelle=" + dateFinReelle +
                '}';
    }
}

