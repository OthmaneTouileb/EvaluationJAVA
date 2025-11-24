package ma.projet.beans;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("FEMME")
@NamedQueries({
    @NamedQuery(
        name = "Femme.findMarriedAtLeastTwice",
        query = "SELECT f FROM Femme f WHERE SIZE(f.mariages) >= 2"
    )
})
@NamedNativeQueries({
    @NamedNativeQuery(
        name = "Femme.countEnfantsBetweenDates",
        query = "SELECT COALESCE(SUM(m.nbr_enfant), 0) as total FROM mariage m " +
                "WHERE m.femme_id = :femmeId " +
                "AND m.date_debut >= :dateDebut " +
                "AND (m.date_fin IS NULL OR m.date_fin <= :dateFin)"
    )
})
public class Femme extends Personne {
    
    @OneToMany(mappedBy = "femme", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Mariage> mariages = new ArrayList<>();
    
    public Femme() {
        super();
    }
    
    public Femme(String nom, String prenom, String telephone, String adresse, java.util.Date dateNaissance) {
        super(nom, prenom, telephone, adresse, dateNaissance);
    }
    
    public List<Mariage> getMariages() {
        return mariages;
    }
    
    public void setMariages(List<Mariage> mariages) {
        this.mariages = mariages;
    }
    
    public void addMariage(Mariage mariage) {
        mariages.add(mariage);
        mariage.setFemme(this);
    }
}

