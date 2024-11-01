package graphes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;


class Graphe implements Serializable {
    public ArrayList<Noeud> noeuds;
    public Integer nbNoeuds;

    public Graphe() {
        this.noeuds = new ArrayList<Noeud>();
    }

    public Graphe(ArrayList<Noeud> noeuds, Integer n) {
        this.noeuds = noeuds;
        this.nbNoeuds = n;
    }

    public void ajouterNoeud(Noeud n) {
        noeuds.add(n);
    }

    public ArrayList<String> barDuGraphe(ArrayList<Noeud> noeuds) {
        ArrayList<String> l;
        l = new ArrayList<String>();
        for (Noeud n : noeuds) {
            l.add(n.bar);
        }
        return l;
    }

    public void afficherVD(Noeud n) {
        System.out.println("le bar " + n.bar + " a pour voisins: \n");

        for (Map.Entry mp : n.suiv.entrySet()) {
            System.out.println(mp.getKey() + " situe a " + mp.getValue());
        }
    }

    public void charger(String file) {
        String line;
        int nbComptesCourant;
        int nbComptesEpargne;
        String[] s;
        HashMap<String, Integer> suiv;
        Noeud n;

        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

          
            line = bufferedReader.readLine();
            nbNoeuds = Integer.parseInt(line);
            
            for (int i = 0; i < nbNoeuds; i++) {
                line = bufferedReader.readLine();
                s = line.split("\t");
                suiv = new HashMap<String, Integer>();
                n = new Noeud(Integer.parseInt(s[0]), s[1], suiv);

                for (int j = 2; j < s.length; j = j + 2) {
                    suiv.put(s[j], Integer.parseInt(s[j + 1]));
                }
                ajouterNoeud(n);
            }
        } catch (Exception e) {
            System.out.println("Erreur de chargement : " + e);
        }
    }

    public double obtenirCoutEntreNoeuds(Noeud noeudCourant, Noeud voisin) {
    Integer coutInteger = noeudCourant.suiv.get(voisin.bar);
    
    // on vérifie si les noeuds sont voisins
    if (coutInteger != null) {
        // Convertir l'Integer en double avant de le retourner
        return coutInteger.doubleValue();
    } else {
       
        return 190000; // si les neouds ne sont pas voisins on retourne une très grande valeur, ceci nous est utile pour l'algorithme des fourmis
    }
}

    public Noeud obtenirNoeudBar(String bar, Graphe graphe) {
        Noeud res;
        res = new Noeud();
        for (Noeud n : graphe.noeuds) {
            if (n.bar.equals(bar)) {
                res = n;
            }
        }
        return res;
    }

    public ArrayList<Noeud> obtenirVoisins(Noeud noeudCourant, Graphe graphe) {
        ArrayList<Noeud> voisins;
        voisins = new ArrayList<Noeud>();
        for (String s : noeudCourant.suiv.keySet()) {
            voisins.add(obtenirNoeudBar(s, graphe));
        }
        return voisins;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
    

