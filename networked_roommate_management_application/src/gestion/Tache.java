package gestion;

import java.util.ArrayList;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import personnes.*;

public class Tache implements Serializable {
    private static final long serialVersionUID = 1L; 
    private String description;
    private boolean fait;
    private Colocataire responsable; 

    public Tache(String description, boolean fait, Colocataire responsable) {
        this.description = description;
        this.fait = fait;
        this.responsable = responsable;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isFait() {
        return fait;
    }

    public void setFait(boolean fait) {
        this.fait = fait;
    }

    public Colocataire getResponsable() {
        return responsable;
    }

    public void setResponsable(Colocataire responsable) {
        this.responsable = responsable;
    }

    public void ajouterTacheTous(Tache tache) {
        ArrayList<Tache> taches = new ArrayList<Tache>();
        taches.add(tache);
        //System.out.println("Tache ajoutée au résumé.");
    }

    public void supprimerTacheTous(Tache tache) {
        ArrayList<Tache> taches = new ArrayList<Tache>();
        taches.remove(tache);
        //System.out.println("Tache supprimée.");
    }
    
    public void ajouterTacheColoc(Tache tache, Colocataire colocataire) {
        ArrayList<Tache> taches = new ArrayList<Tache>();
        colocataire.toDoList.add(tache);
        //System.out.println("Tache ajoutée à la to-do list du coloc.");
    }

    public void supprimerTacheColoc(Tache tache, Colocataire colocataire) {
        ArrayList<Tache> taches = new ArrayList<Tache>();
        colocataire.toDoList.remove(tache);
        //System.out.println("Tache supprimée de la to-do list du coloc.");
    }

    //sauvegarde des taches dans un fichier à part
    public static void sauvegarderTaches(ArrayList<Tache> taches, String nomFichier) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(nomFichier))) {
            oos.writeObject(taches);
        } catch (IOException e) {
            e.printStackTrace();
        }
    } 


    //charger la liste de tâches mais non adaptée à l'IHM
    @SuppressWarnings("unchecked") //si je fais pas ça y a un warning
    public static ArrayList<Tache> chargerTaches(String nomFichier) {
        ArrayList<Tache> taches = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(nomFichier))) {
            Object obj = ois.readObject();
            if (obj instanceof ArrayList) {
                taches = (ArrayList<Tache>) obj;
            } else {
                System.err.println("Le fichier ne contient pas de liste de tâches.");
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return taches;
    }
    
    
    public static void main(String[] args) {
        ArrayList<Tache> taches = new ArrayList<>();
        taches.add(new Tache("Faire la vaisselle", false, null));
        sauvegarderTaches(taches, "taches.dat");
        ArrayList<Tache> tachesChargees=chargerTaches("taches.dat");
        for (Tache tache:tachesChargees) {
            System.out.println(tache.getDescription());
        }
    }


    

}
