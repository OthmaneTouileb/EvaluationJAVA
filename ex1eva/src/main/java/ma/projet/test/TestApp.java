package ma.projet.test;

import ma.projet.classes.*;
import ma.projet.service.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TestApp {
    
    private static CategorieService categorieService = new CategorieService();
    private static ProduitService produitService = new ProduitService();
    private static CommandeService commandeService = new CommandeService();
    private static LigneCommandeService ligneCommandeService = new LigneCommandeService();
    
    public static void main(String[] args) {
        System.out.println("=== Test Application de Gestion de Stock ===\n");
        
        // Test 1: Création et gestion des catégories
        System.out.println("1. Test des catégories:");
        testCategories();
        
        // Test 2: Création et gestion des produits
        System.out.println("\n2. Test des produits:");
        testProduits();
        
        // Test 3: Création et gestion des commandes
        System.out.println("\n3. Test des commandes:");
        testCommandes();
        
        // Test 4: Création et gestion des lignes de commande
        System.out.println("\n4. Test des lignes de commande:");
        testLignesCommande();
        
        // Test 5: Produits par catégorie
        System.out.println("\n5. Test: Produits par catégorie:");
        testProduitsParCategorie();
        
        // Test 6: Produits commandés entre deux dates
        System.out.println("\n6. Test: Produits commandés entre deux dates:");
        testProduitsEntreDates();
        
        // Test 7: Produits d'une commande donnée
        System.out.println("\n7. Test: Produits d'une commande donnée:");
        testProduitsParCommande();
        
        // Test 8: Produits avec prix > 100 DH
        System.out.println("\n8. Test: Produits avec prix > 100 DH:");
        testProduitsPrixSuperieur();
        
        System.out.println("\n=== Fin des tests ===");
    }
    
    private static void testCategories() {
        // Création de catégories
        Categorie cat1 = new Categorie("CAT001", "Ordinateurs");
        Categorie cat2 = new Categorie("CAT002", "Périphériques");
        Categorie cat3 = new Categorie("CAT003", "Accessoires");
        
        categorieService.create(cat1);
        categorieService.create(cat2);
        categorieService.create(cat3);
        
        System.out.println("Catégories créées avec succès");
        
        // Affichage de toutes les catégories
        List<Categorie> categories = categorieService.findAll();
        System.out.println("Liste des catégories:");
        for (Categorie c : categories) {
            System.out.println("  - " + c);
        }
    }
    
    private static void testProduits() {
        // Récupération des catégories
        List<Categorie> categories = categorieService.findAll();
        if (categories.isEmpty()) {
            System.out.println("Aucune catégorie trouvée");
            return;
        }
        
        Categorie cat1 = categories.get(0);
        Categorie cat2 = categories.size() > 1 ? categories.get(1) : cat1;
        
        // Création de produits
        Produit p1 = new Produit("ES12", 120.0f, cat1);
        Produit p2 = new Produit("ZR85", 100.0f, cat1);
        Produit p3 = new Produit("EE85", 200.0f, cat2);
        Produit p4 = new Produit("AB99", 80.0f, cat2);
        Produit p5 = new Produit("CD77", 150.0f, cat1);
        
        produitService.create(p1);
        produitService.create(p2);
        produitService.create(p3);
        produitService.create(p4);
        produitService.create(p5);
        
        System.out.println("Produits créés avec succès");
        
        // Affichage de tous les produits
        List<Produit> produits = produitService.findAll();
        System.out.println("Liste des produits:");
        for (Produit p : produits) {
            System.out.println("  - " + p.getReference() + " - " + p.getPrix() + " DH - Catégorie: " + p.getCategorie().getLibelle());
        }
    }
    
    private static void testCommandes() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        
        try {
            // Création de commandes
            Date date1 = sdf.parse("2013-03-14");
            Date date2 = sdf.parse("2013-03-15");
            Date date3 = sdf.parse("2013-03-20");
            
            Commande cmd1 = new Commande(date1);
            Commande cmd2 = new Commande(date2);
            Commande cmd3 = new Commande(date3);
            
            commandeService.create(cmd1);
            commandeService.create(cmd2);
            commandeService.create(cmd3);
            
            System.out.println("Commandes créées avec succès");
            
            // Affichage de toutes les commandes
            List<Commande> commandes = commandeService.findAll();
            System.out.println("Liste des commandes:");
            for (Commande c : commandes) {
                System.out.println("  - Commande ID: " + c.getId() + " - Date: " + sdf.format(c.getDate()));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    
    private static void testLignesCommande() {
        // Récupération des produits et commandes
        List<Produit> produits = produitService.findAll();
        List<Commande> commandes = commandeService.findAll();
        
        if (produits.isEmpty() || commandes.isEmpty()) {
            System.out.println("Produits ou commandes introuvables");
            return;
        }
        
        // Création de lignes de commande pour la première commande
        Commande cmd1 = commandes.get(0);
        if (produits.size() >= 3) {
            LigneCommandeProduit lcp1 = new LigneCommandeProduit(7, produits.get(0), cmd1);
            LigneCommandeProduit lcp2 = new LigneCommandeProduit(14, produits.get(1), cmd1);
            LigneCommandeProduit lcp3 = new LigneCommandeProduit(5, produits.get(2), cmd1);
            
            ligneCommandeService.create(lcp1);
            ligneCommandeService.create(lcp2);
            ligneCommandeService.create(lcp3);
        }
        
        // Création de lignes pour la deuxième commande
        if (commandes.size() > 1 && produits.size() >= 2) {
            Commande cmd2 = commandes.get(1);
            LigneCommandeProduit lcp4 = new LigneCommandeProduit(3, produits.get(0), cmd2);
            LigneCommandeProduit lcp5 = new LigneCommandeProduit(10, produits.get(3), cmd2);
            
            ligneCommandeService.create(lcp4);
            ligneCommandeService.create(lcp5);
        }
        
        System.out.println("Lignes de commande créées avec succès");
        
        // Affichage de toutes les lignes
        List<LigneCommandeProduit> lignes = ligneCommandeService.findAll();
        System.out.println("Liste des lignes de commande:");
        for (LigneCommandeProduit l : lignes) {
            System.out.println("  - Produit: " + l.getProduit().getReference() + 
                             " - Quantité: " + l.getQuantite() + 
                             " - Commande: " + l.getCommande().getId());
        }
    }
    
    private static void testProduitsParCategorie() {
        List<Categorie> categories = categorieService.findAll();
        if (categories.isEmpty()) {
            System.out.println("Aucune catégorie trouvée");
            return;
        }
        
        Categorie cat = categories.get(0);
        System.out.println("Produits de la catégorie: " + cat.getLibelle());
        
        List<Produit> produits = produitService.getProduitsByCategorie(cat.getId());
        if (produits != null && !produits.isEmpty()) {
            for (Produit p : produits) {
                System.out.println("  - " + p.getReference() + " - " + p.getPrix() + " DH");
            }
        } else {
            System.out.println("  Aucun produit trouvé pour cette catégorie");
        }
    }
    
    private static void testProduitsEntreDates() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        
        try {
            Date dateDebut = sdf.parse("2013-03-14");
            Date dateFin = sdf.parse("2013-03-16");
            
            System.out.println("Produits commandés entre " + sdf.format(dateDebut) + " et " + sdf.format(dateFin) + ":");
            
            List<Produit> produits = produitService.getProduitsCommandesEntreDates(dateDebut, dateFin);
            if (produits != null && !produits.isEmpty()) {
                for (Produit p : produits) {
                    System.out.println("  - " + p.getReference() + " - " + p.getPrix() + " DH");
                }
            } else {
                System.out.println("  Aucun produit trouvé pour cette période");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    
    private static void testProduitsParCommande() {
        List<Commande> commandes = commandeService.findAll();
        if (!commandes.isEmpty()) {
            int commandeId = commandes.get(0).getId();
            produitService.afficherProduitsParCommande(commandeId);
        } else {
            System.out.println("Aucune commande trouvée");
        }
    }
    
    private static void testProduitsPrixSuperieur() {
        System.out.println("Produits avec prix > 100 DH:");
        List<Produit> produits = produitService.getProduitsPrixSuperieur(100);
        if (produits != null && !produits.isEmpty()) {
            for (Produit p : produits) {
                System.out.println("  - " + p.getReference() + " - " + p.getPrix() + " DH - Catégorie: " + p.getCategorie().getLibelle());
            }
        } else {
            System.out.println("  Aucun produit avec prix > 100 DH");
        }
    }
}


