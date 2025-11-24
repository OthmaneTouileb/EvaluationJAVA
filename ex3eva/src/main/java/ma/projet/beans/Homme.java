package ma.projet.beans;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("HOMME")
public class Homme extends Personne {
    
    @OneToMany(mappedBy = "homme", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Mariage> mariages = new ArrayList<>();
    
    public Homme() {
        super();
    }
    
    public Homme(String nom, String prenom, String telephone, String adresse, java.util.Date dateNaissance) {
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
        mariage.setHomme(this);
    }
}

