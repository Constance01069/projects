package personnes;

import java.util.List;

import annonces.Annonce;
import gestion.TableauPaiements;
import gestion.Tache;
import sorties.*;

import java.util.ArrayList;


public class Colocataire extends Personne {
    public ArrayList<Tache> toDoList;
    private ArrayList<Annonce> annonces;
    
    

    public Colocataire(String nom, String prenom) {
        super(nom, prenom);
        this.toDoList = new ArrayList<>();
        this.annonces = new ArrayList<>();
        
    }

    public List<Tache> getToDList() {
        return this.toDoList;
        
    }
     public void setTaches(ArrayList<Tache> taches) {
        this.toDoList = taches;
    }



    public List<Annonce> getAnnonces() {
        return annonces;
    }

    public void setAnnonces(ArrayList<Annonce> annonces) {
        this.annonces = annonces;
    }


    public TableauPaiements getTableauPaiements() {
        return null;
    }

    public int size() {
        return 0;
    }

    public Colocataire get(int i) {
        return null;
    }

    //quand un coloc a fini une tache, il la supprime de sa to-do list
    public void tacheFinie(Tache tache) {
        this.toDoList.remove(tache);
    }

    //ajouter une annonce dans la liste des annonces d'un coloc et dans la liste des annonces de tous les colocs
    public void ajouterAnnonce(Annonce annonce) {
        this.annonces.add(annonce);
    }
}