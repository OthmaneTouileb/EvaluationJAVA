package ma.projet.test;

import ma.projet.beans.Femme;
import ma.projet.beans.Homme;
import ma.projet.beans.Mariage;
import ma.projet.service.FemmeService;
import ma.projet.service.HommeService;
import ma.projet.service.MariageService;
import ma.projet.util.HibernateUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TestApplication {
    
    private static HommeService hommeService = new HommeService();
    private static FemmeService femmeService = new FemmeService();
    private static MariageService mariageService = new MariageService();
    
    public static void main(String[] args) {
        try {
            // Initialiser Hibernate
            HibernateUtil.getSessionFactory();
            
            System.out.println("=== DÉBUT DU PROGRAMME DE TEST ===\n");
            
            // 1. Créer 10 femmes et 5 hommes
            System.out.println("1. Création de 10 femmes et 5 hommes...");
            creerDonneesTest();
            System.out.println("✓ Données créées avec succès!\n");
            
            // 2. Afficher la liste des femmes
            System.out.println("2. Liste des femmes:");
            afficherListeFemmes();
            System.out.println();
            
            // 3. Afficher la femme la plus âgée
            System.out.println("3. Femme la plus âgée:");
            afficherFemmeLaPlusAgee();
            System.out.println();
            
            // 4. Afficher les épouses d'un homme donné
            System.out.println("4. Épouses d'un homme (ID=1) entre deux dates:");
            afficherEpousesHomme();
            System.out.println();
            
            // 5. Afficher le nombre d'enfants d'une femme entre deux dates
            System.out.println("5. Nombre d'enfants d'une femme (ID=1) entre deux dates:");
            afficherNombreEnfantsFemme();
            System.out.println();
            
            // 6. Afficher les femmes mariées deux fois ou plus
            System.out.println("6. Femmes mariées au moins deux fois:");
            afficherFemmesMarieesDeuxFois();
            System.out.println();
            
            // 7. Afficher les hommes mariés à quatre femmes entre deux dates
            System.out.println("7. Nombre d'hommes mariés à quatre femmes entre deux dates:");
            afficherHommesMariesQuatreFemmes();
            System.out.println();
            
            // 8. Afficher les mariages d'un homme avec tous les détails
            System.out.println("8. Mariages d'un homme (ID=1) avec tous les détails:");
            afficherMariagesHommeAvecDetails();
            System.out.println();
            
            System.out.println("=== FIN DU PROGRAMME DE TEST ===");
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            HibernateUtil.shutdown();
        }
    }
    
    private static void creerDonneesTest() {
        // Créer 5 hommes
        Homme h1 = new Homme("SAFI", "SAID", "0612345678", "Casablanca", createDate(1970, 1, 15));
        Homme h2 = new Homme("ALAMI", "AHMED", "0623456789", "Rabat", createDate(1965, 5, 20));
        Homme h3 = new Homme("BENNANI", "MOHAMED", "0634567890", "Fès", createDate(1975, 8, 10));
        Homme h4 = new Homme("ALAOUI", "HASSAN", "0645678901", "Marrakech", createDate(1968, 3, 25));
        Homme h5 = new Homme("IDRISSI", "YOUSSEF", "0656789012", "Tanger", createDate(1972, 11, 5));
        
        hommeService.create(h1);
        hommeService.create(h2);
        hommeService.create(h3);
        hommeService.create(h4);
        hommeService.create(h5);
        
        // Créer 10 femmes
        Femme f1 = new Femme("SALIMA", "RAMI", "0712345678", "Casablanca", createDate(1975, 2, 10));
        Femme f2 = new Femme("AMAL", "ALI", "0723456789", "Rabat", createDate(1980, 6, 15));
        Femme f3 = new Femme("WAFA", "ALAOUI", "0734567890", "Fès", createDate(1978, 9, 20));
        Femme f4 = new Femme("KARIMA", "ALAMI", "0745678901", "Marrakech", createDate(1972, 4, 5));
        Femme f5 = new Femme("FATIMA", "BENNANI", "0756789012", "Tanger", createDate(1985, 7, 12));
        Femme f6 = new Femme("SOUAD", "IDRISSI", "0767890123", "Casablanca", createDate(1976, 10, 18));
        Femme f7 = new Femme("NADIA", "SAFI", "0778901234", "Rabat", createDate(1982, 1, 22));
        Femme f8 = new Femme("LATIFA", "ALAOUI", "0789012345", "Fès", createDate(1974, 3, 8));
        Femme f9 = new Femme("SAMIRA", "BENNANI", "0790123456", "Marrakech", createDate(1983, 5, 30));
        Femme f10 = new Femme("KHADIJA", "ALAMI", "0701234567", "Tanger", createDate(1977, 8, 14));
        
        femmeService.create(f1);
        femmeService.create(f2);
        femmeService.create(f3);
        femmeService.create(f4);
        femmeService.create(f5);
        femmeService.create(f6);
        femmeService.create(f7);
        femmeService.create(f8);
        femmeService.create(f9);
        femmeService.create(f10);
        
        // Créer des mariages
        // Homme 1 (SAFI SAID) - 4 mariages
        Mariage m1 = new Mariage(createDate(1989, 9, 3), createDate(1990, 9, 3), 0, h1, f4); // Échoué
        Mariage m2 = new Mariage(createDate(1990, 9, 3), null, 4, h1, f1); // En cours
        Mariage m3 = new Mariage(createDate(1995, 9, 3), null, 2, h1, f2); // En cours
        Mariage m4 = new Mariage(createDate(2000, 11, 4), null, 3, h1, f3); // En cours
        
        mariageService.create(m1);
        mariageService.create(m2);
        mariageService.create(m3);
        mariageService.create(m4);
        
        // Homme 2 (ALAMI AHMED) - 2 mariages
        Mariage m5 = new Mariage(createDate(1992, 1, 10), null, 2, h2, f6);
        Mariage m6 = new Mariage(createDate(1998, 6, 15), null, 1, h2, f7);
        
        mariageService.create(m5);
        mariageService.create(m6);
        
        // Homme 3 (BENNANI MOHAMED) - 4 mariages
        Mariage m7 = new Mariage(createDate(2000, 1, 1), null, 3, h3, f5);
        Mariage m8 = new Mariage(createDate(2002, 3, 15), null, 2, h3, f8);
        Mariage m9 = new Mariage(createDate(2005, 7, 20), null, 1, h3, f9);
        Mariage m10 = new Mariage(createDate(2008, 9, 10), null, 4, h3, f10);
        
        mariageService.create(m7);
        mariageService.create(m8);
        mariageService.create(m9);
        mariageService.create(m10);
        
        // Homme 4 (ALAOUI HASSAN) - 1 mariage
        Mariage m11 = new Mariage(createDate(1995, 4, 12), null, 2, h4, f4);
        mariageService.create(m11);
        
        // Homme 5 (IDRISSI YOUSSEF) - 2 mariages
        Mariage m12 = new Mariage(createDate(2001, 2, 14), null, 1, h5, f1);
        Mariage m13 = new Mariage(createDate(2003, 8, 22), null, 2, h5, f2);
        
        mariageService.create(m12);
        mariageService.create(m13);
    }
    
    private static Date createDate(int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month - 1, day);
        return cal.getTime();
    }
    
    private static void afficherListeFemmes() {
        List<Femme> femmes = femmeService.findAll();
        if (femmes != null && !femmes.isEmpty()) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            for (Femme f : femmes) {
                System.out.println("ID: " + f.getId() + " - " + f.getNom() + " " + f.getPrenom() + 
                    " - Naissance: " + sdf.format(f.getDateNaissance()));
            }
        } else {
            System.out.println("Aucune femme trouvée.");
        }
    }
    
    private static void afficherFemmeLaPlusAgee() {
        Femme femme = femmeService.getFemmeLaPlusAgee();
        if (femme != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            System.out.println("ID: " + femme.getId() + " - " + femme.getNom() + " " + femme.getPrenom() + 
                " - Date de naissance: " + sdf.format(femme.getDateNaissance()));
        } else {
            System.out.println("Aucune femme trouvée.");
        }
    }
    
    private static void afficherEpousesHomme() {
        Date dateDebut = createDate(1990, 1, 1);
        Date dateFin = createDate(2005, 12, 31);
        
        List<Mariage> mariages = hommeService.getEpousesEntreDates(1, dateDebut, dateFin);
        if (mariages != null && !mariages.isEmpty()) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            for (Mariage m : mariages) {
                System.out.println("Femme: " + m.getFemme().getNom() + " " + m.getFemme().getPrenom() + 
                    " - Date début: " + sdf.format(m.getDateDebut()) + 
                    " - Nombre d'enfants: " + m.getNbrEnfant());
            }
        } else {
            System.out.println("Aucune épouse trouvée pour cet homme dans cette période.");
        }
    }
    
    private static void afficherNombreEnfantsFemme() {
        Date dateDebut = createDate(1990, 1, 1);
        Date dateFin = createDate(2000, 12, 31);
        
        long nombreEnfants = femmeService.getNombreEnfantsEntreDates(1, dateDebut, dateFin);
        System.out.println("Nombre d'enfants de la femme (ID=1) entre " + 
            new SimpleDateFormat("dd/MM/yyyy").format(dateDebut) + " et " + 
            new SimpleDateFormat("dd/MM/yyyy").format(dateFin) + ": " + nombreEnfants);
    }
    
    private static void afficherFemmesMarieesDeuxFois() {
        List<Femme> femmes = femmeService.getFemmesMarieesAuMoinsDeuxFois();
        if (femmes != null && !femmes.isEmpty()) {
            for (Femme f : femmes) {
                System.out.println("ID: " + f.getId() + " - " + f.getNom() + " " + f.getPrenom() + 
                    " - Nombre de mariages: " + f.getMariages().size());
            }
        } else {
            System.out.println("Aucune femme mariée au moins deux fois trouvée.");
        }
    }
    
    private static void afficherHommesMariesQuatreFemmes() {
        Date dateDebut = createDate(1990, 1, 1);
        Date dateFin = createDate(2010, 12, 31);
        
        long nombre = femmeService.getNombreHommesMariesQuatreFemmesEntreDates(dateDebut, dateFin);
        System.out.println("Nombre d'hommes mariés à quatre femmes entre " + 
            new SimpleDateFormat("dd/MM/yyyy").format(dateDebut) + " et " + 
            new SimpleDateFormat("dd/MM/yyyy").format(dateFin) + ": " + nombre);
    }
    
    private static void afficherMariagesHommeAvecDetails() {
        hommeService.afficherMariagesAvecDetails(1);
    }
}

