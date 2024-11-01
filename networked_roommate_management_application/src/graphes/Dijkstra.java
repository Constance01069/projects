package graphes;

import java.util.ArrayList;
import java.util.*;

public class Dijkstra {

    public static ArrayList<Noeud> plusCourtChemin(Graphe graphe, String depart, String arrivee) {
        
        HashMap<String, Double> distance = new HashMap<>();
        HashMap<String, Noeud> predecesseur = new HashMap<>();
        PriorityQueue<NoeudAvecDistance> queue = new PriorityQueue<>();

        
        for (Noeud n : graphe.noeuds) {
            distance.put(n.bar, Double.POSITIVE_INFINITY);
            predecesseur.put(n.bar, null);
        }
        distance.put(depart, 0.0);

       
        queue.add(new NoeudAvecDistance(graphe.obtenirNoeudBar(depart, graphe), 0.0));

       
        while (!queue.isEmpty()) {
            NoeudAvecDistance noeudActuel = queue.poll();
            Noeud n = noeudActuel.noeud;

            for (Noeud voisin : graphe.obtenirVoisins(n, graphe)) {
                double nouveauCout = distance.get(n.bar) + graphe.obtenirCoutEntreNoeuds(n, voisin);

                if (nouveauCout < distance.get(voisin.bar)) {
                    distance.put(voisin.bar, nouveauCout);
                    predecesseur.put(voisin.bar, n);
                    queue.add(new NoeudAvecDistance(voisin, nouveauCout));
                }
            }
        }

       
        ArrayList<Noeud> chemin = new ArrayList<>();
        Noeud noeudArrivee = graphe.obtenirNoeudBar(arrivee, graphe);

        while (noeudArrivee != null) {
            chemin.add(0, noeudArrivee);
            noeudArrivee = predecesseur.get(noeudArrivee.bar);
        }

        return chemin;
    }

    
}
