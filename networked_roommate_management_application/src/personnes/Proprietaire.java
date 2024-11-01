package personnes;

import java.util.ArrayList;
import java.util.Scanner;

import annonces.Annonce;
import gestion.Loyer;
import gestion.TableauPaiements;


public class Proprietaire extends Personne {
    private ArrayList<Colocataire> listeLocataires;
    private TableauPaiements tableauPaiements;
    private ArrayList<Annonce> annonces;


    
    public ArrayList<Colocataire> getListeLocataires() {
        return listeLocataires;
    }

    public TableauPaiements getTableauPaiements() {
        return tableauPaiements;
    }

    
    public ArrayList<Annonce> getAnnonces() {
        return annonces;
    }

    public Proprietaire(String nom, String prenom, ArrayList<Colocataire> listeLocataires, ArrayList<TableauPaiements> tableauPaiements, ArrayList<Annonce> annonces) {
        super(nom, prenom);
        this.listeLocataires = listeLocataires;
        this.annonces = annonces;
        this.tableauPaiements = new TableauPaiements();
    }

    public Proprietaire(String nom, String prenom) {
        super(nom, prenom);
        listeLocataires = new ArrayList<Colocataire>();
    }


    public void setListeLocataires(ArrayList<Colocataire> listeLocataires) {
        this.listeLocataires = listeLocataires;
    }


    public void setAnnonces(ArrayList<Annonce> annonces) {
        this.annonces = annonces;
    }

    public void afficherProfil() {
        System.out.println("Nom: " + getNom());
        System.out.println("Prénom: " + getPrenom());
    }

    public void ajouterColocataire(Colocataire colocataire) {
        listeLocataires.add(colocataire);
    }

    public void supprimerColocataire(Colocataire colocataire) {
        listeLocataires.remove(colocataire);
    }

    //version non IHM
    public void enregistrerLoyer(String mois, double montant) {
        // Scanner scanner = new Scanner(System.in);

        System.out.print("Mois du loyer : ");
        // String mois = scanner.next();

        System.out.print("Montant du loyer : ");
        // double montant = scanner.nextDouble();

        boolean estPaye = false;

        Loyer loyer = new Loyer(mois, montant, estPaye);
        tableauPaiements.ajouterLoyer(loyer);

        System.out.println("Loyer ajouté avec succès pour le mois de " + mois + ".");
    }

    public Colocataire instanceColoc(String[] nomPrenom) {
        ArrayList<Colocataire> listeColoc = getListeLocataires();
        Colocataire coloc = listeColoc.get(0);
        Boolean trouve = false;
        int i = 0;
        while (!trouve) {
            if ((listeColoc.get(i).getNom() == nomPrenom[0]) && (listeColoc.get(i).getPrenom() == nomPrenom[1])) {
                trouve = true;
            }
            i++;
        }
        
        return coloc;
    }


public static void main(String[] args) {
    ArrayList<Colocataire> listeColocataires = new ArrayList<>();
    ArrayList<Annonce> listeAnnonces = new ArrayList<>();
    Proprietaire proprietaire = new Proprietaire("kenza", "ben", listeColocataires, null, listeAnnonces);

    Colocataire colocataire1 = new Colocataire("constance", "beau");
    Colocataire colocataire2 = new Colocataire("ines", "silhadi");

    proprietaire.ajouterColocataire(colocataire1);
    proprietaire.ajouterColocataire(colocataire2);
/* 
    ArrayList<Loyer> loyersColocataire1 = new ArrayList<>();
        ArrayList<Loyer> loyersColocataire2 = new ArrayList<>();

        loyersColocataire1.add(new Loyer("Janvier", 500, true));
        loyersColocataire1.add(new Loyer("Février", 500, true));

        loyersColocataire2.add(new Loyer("Janvier", 600, true));
        loyersColocataire2.add(new Loyer("Février", 600, false));

        colocataire1.getTableauPaiements().setLoyers(loyersColocataire1);
        colocataire2.getTableauPaiements().setLoyers(loyersColocataire2);
*/
    Scanner scanner = new Scanner(System.in);

    int choix;
    do {
        System.out.println("\nMenu pour Proprietaire:");
        System.out.println("1. Afficher profil");
        System.out.println("2. Suivre paiements des colocataires");
        System.out.println("3. Ajouter colocataire");
        System.out.println("4. Supprimer colocataire");
        System.out.println("5. Enregistrer loyer");
        System.out.println("0. Quitter");

        System.out.print("Choisissez une action (0-5) : ");
        choix = scanner.nextInt();

        switch (choix) {
            case 1:
                proprietaire.afficherProfil();
                break;
            case 2:
                System.out.print("Nom du colocataire : ");
                String nomColoc = scanner.next();
                System.out.print("Prénom du colocataire : ");
                String prenomColoc = scanner.next();
                Colocataire nouveauColocataire = new Colocataire(nomColoc, prenomColoc);
                proprietaire.ajouterColocataire(nouveauColocataire);
                break;
            case 3:
                System.out.print("Nom du colocataire à supprimer : ");
                String nomSupprimer = scanner.next();
                System.out.print("Prénom du colocataire à supprimer : ");
                String prenomSupprimer = scanner.next();
                Colocataire colocataireASupprimer = new Colocataire(nomSupprimer, prenomSupprimer);
                proprietaire.supprimerColocataire(colocataireASupprimer);
                break;
            case 4:
                // proprietaire.enregistrerLoyer();
                break;
            case 0:
                System.out.println("Au revoir !");
                break;
            default:
                System.out.println("Choix invalide. Veuillez réessayer.");
        }
    } while (choix != 0);

    scanner.close();
}

/* 
    // Méthode pour choisir les destinataires parmi la liste des colocataires non fonctionnelle 
    private ArrayList<Colocataire> choisirDestinataires() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Voici la liste des colocataires:");

        for (int i =0; i<listeLocataires.size(); i++) {
            System.out.println((i+1) + ". " + listeLocataires.get(i).getNom() + " " + listeLocataires.get(i).getPrenom());
        }

        // Demander à l'utilisateur de choisir les destinataires
        System.out.println("Choisissez les destinataires en en selectionnant parmi les choix ");
        String choixUtilisateur = scanner.nextLine();

    }
 */



}
