package ma.projet.beans;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "mariage")
public class Mariage {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Temporal(TemporalType.DATE)
    @Column(name = "date_debut")
    private Date dateDebut;
    
    @Temporal(TemporalType.DATE)
    @Column(name = "date_fin")
    private Date dateFin;
    
    @Column(name = "nbr_enfant")
    private int nbrEnfant;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "homme_id")
    private Homme homme;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "femme_id")
    private Femme femme;
    
    public Mariage() {
    }
    
    public Mariage(Date dateDebut, Date dateFin, int nbrEnfant, Homme homme, Femme femme) {
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.nbrEnfant = nbrEnfant;
        this.homme = homme;
        this.femme = femme;
    }
    
    // Getters and Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public Date getDateDebut() {
        return dateDebut;
    }
    
    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }
    
    public Date getDateFin() {
        return dateFin;
    }
    
    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }
    
    public int getNbrEnfant() {
        return nbrEnfant;
    }
    
    public void setNbrEnfant(int nbrEnfant) {
        this.nbrEnfant = nbrEnfant;
    }
    
    public Homme getHomme() {
        return homme;
    }
    
    public void setHomme(Homme homme) {
        this.homme = homme;
    }
    
    public Femme getFemme() {
        return femme;
    }
    
    public void setFemme(Femme femme) {
        this.femme = femme;
    }
    
    public boolean isEnCours() {
        return dateFin == null;
    }
}

