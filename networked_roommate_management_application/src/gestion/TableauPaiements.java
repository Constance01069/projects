package gestion;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TableauPaiements implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<Loyer> loyers;

    public TableauPaiements() {
        this.loyers = new ArrayList<>();
    }

    public List<Loyer> getLoyers() {
        return loyers;
    }

    public void setLoyers(List<Loyer> loyers) {
        this.loyers = loyers;
    }

    public void ajouterLoyer(Loyer loyer) {
        loyers.add(loyer);
    }


public List<String> obtenirResumeDerniersLoyers() {
    List<String> resumeLoyers = new ArrayList<>();
    LocalDate currentDate = LocalDate.now();

    for (int i = 0; i < 6; i++) {
        LocalDate moisAct = currentDate.minusMonths(i);
        String moisAnnee = moisAct.format(DateTimeFormatter.ofPattern("MMMM yyyy"));
        boolean loyerTrouve = false;

        for (Loyer loyer : loyers) {
            if (moisAnnee.equals(loyer.getMois())) {
                resumeLoyers.add(loyer.getMois() + "\t\t" + loyer.getMontant() + "\t" + (loyer.estPaye() ? "Payé" : "Non Payé"));
                loyerTrouve = true;
                break;
            }
        }

        if (!loyerTrouve) {
            resumeLoyers.add(moisAnnee + "\t\t0.00\tNon Payé");
        }
    }

    return resumeLoyers;
}
 
    //sauvegarde sans IHM donc affichage par println
    public void sauvegarderTableau(String nomFichier) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(nomFichier))) {
            oos.writeObject(this);
            System.out.println("Tableau de paiements sauvegardé avec succès.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static TableauPaiements chargerTableau(String nomFichier) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(nomFichier))) {
            return (TableauPaiements) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new TableauPaiements(); // Retourne un nouvel objet en cas d'échec de chargement
        }
    }

   
    

    public static void main(String[] args) {
        // création + exemples
        TableauPaiements tableau = new TableauPaiements();
        tableau.ajouterLoyer(new Loyer("décembre 2023", 500.00, false));
        tableau.ajouterLoyer(new Loyer("janvier 2024", 500.00, false));

        // sauvegarde
        tableau.sauvegarderTableau("tableau_paiements.ser");

        // chargement
        TableauPaiements tableauCharge = TableauPaiements.chargerTableau("tableau_paiements.ser");

        // Affichage des paiements pour les 6 derniers mois
        List<String> resumeLoyers = tableauCharge.obtenirResumeDerniersLoyers();
        System.out.println("Tableau des paiements :");
        for (String ligne : resumeLoyers) {
            System.out.println(ligne);
        }
    }

}