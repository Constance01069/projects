package personnes;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Personne implements Serializable {
    private static final long serialVersionUID = 1L; 
    private String nom;
    private String prenom;
    private String identifiant;
    private String motDePasse;

    public Personne(String prenom, String nom) {
        this.nom = nom;
        this.prenom = prenom;
        this.creerIdMdp();
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nouveauNom) {
        nom = nouveauNom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String nouveauPrenom) {
        prenom = nouveauPrenom;
    }

    public String getIdentifiant() {
        return identifiant;
    }

    public void setIdentifiant(String nouveauIdentifiant) {
        identifiant = nouveauIdentifiant;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String nouveauMotDePasse) {
        motDePasse = nouveauMotDePasse;
    }

    //cree un identifiant et un mdp pour le client a partir de son nom et prenom
    public void creerIdMdp() {
        String identifiant = this.getPrenom() + this.getNom();
        String mdp = this.getPrenom() + this.getNom() + "123";
        this.setIdentifiant(identifiant);
        this.setMotDePasse(mdp);
    }

    public void sauvegarderPersonne(String nomFichier) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(nomFichier))) {
            oos.writeObject(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    //verifier id et mdp
    public boolean verifierConnexion(String identifiant, String motDePasse) {
        return this.getIdentifiant().equals(identifiant) && this.getMotDePasse().equals(motDePasse);
    }


    // chargement
    public static Personne chargerPersonne(String nomFichier) {
        Personne personne = null;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(nomFichier))) {
            Object obj = ois.readObject();
            if (obj instanceof Personne) {
                personne = (Personne) obj;
            } else {
                System.err.println("Le fichier ne contient pas de personne.");
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return personne;
    }

    public static void main(String[] args) {
        Personne personne = new Personne("Silhadi", "Ines");
        personne.creerIdMdp();
        personne.sauvegarderPersonne("personne.dat");
        Personne personneChargee = Personne.chargerPersonne("inessilhadi.dat");
        System.out.println(personneChargee.getIdentifiant());
        System.out.println(personneChargee.getMotDePasse());
    }

    
}





    



