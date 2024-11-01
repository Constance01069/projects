package graphes;

import java.util.ArrayList;
import java.util.Random;

public class AlgorithmeFourmis {
	
    
    public static ArrayList<Noeud> algorithmeFourmis(Graphe graphe, String lettre) {
    	int NOMBRE_FOURMIS = 100;
    	int NOMBRE_ITERATIONS = 1000;
        ArrayList<Noeud> meilleurCheminGlobal = null;
        double meilleurCoutGlobal = Double.MAX_VALUE;

        
        double[][] pheromones = initialiserPheromones(graphe);

        
        for (int iteration = 0; iteration < NOMBRE_ITERATIONS; iteration++) {
            
            for (int i = 0; i < NOMBRE_FOURMIS; i++) {
                ArrayList<Noeud> cheminFourmi = deplacerFourmi(graphe, pheromones,lettre);
                double coutCheminFourmi = calculerCoutChemin(cheminFourmi, graphe);

                
                if (coutCheminFourmi < meilleurCoutGlobal) {
                    meilleurCoutGlobal = coutCheminFourmi;
                    meilleurCheminGlobal = new ArrayList<>(cheminFourmi);
                }
            }

            
            evaporationPheromones(pheromones);
            depotPheromones(pheromones, meilleurCheminGlobal, graphe);
        }

        return meilleurCheminGlobal;
    }

    private static double[][] initialiserPheromones(Graphe graphe) {
        int nombreNoeuds = graphe.noeuds.size();
        double[][] pheromones = new double[nombreNoeuds][nombreNoeuds];

        
        for (int i = 0; i < nombreNoeuds; i++) {
            for (int j = 0; j < nombreNoeuds; j++) {
                if (i != j) {
                    pheromones[i][j] = 1.0;
                }
            }
        }

        return pheromones;
    }

    private static ArrayList<Noeud> deplacerFourmi(Graphe graphe, double[][] pheromones, String lettre) {
        ArrayList<Noeud> cheminFourmi = new ArrayList<>();

        // on commence avec le noeud de départ qu'on a choisi
        Noeud noeudCourant = graphe.obtenirNoeudBar(lettre,graphe);
        cheminFourmi.add(noeudCourant);

        // la fourmi parcourt tout le graphe, on construit son chemin
        while (cheminFourmi.size() < graphe.noeuds.size()) {
            Noeud prochainNoeud = choisirProchainNoeud(graphe, noeudCourant, cheminFourmi, pheromones);
            cheminFourmi.add(prochainNoeud);
            noeudCourant = prochainNoeud;
        }

        return cheminFourmi;
    }

    private static Noeud choisirProchainNoeud(Graphe graphe, Noeud noeudCourant, ArrayList<Noeud> cheminFourmi, double[][] pheromones) {
        Random random = new Random();
        ArrayList<Noeud> voisinsNonVisites = new ArrayList<>();

    
        for (Noeud voisin : graphe.obtenirVoisins(noeudCourant, graphe)) {
            if (!cheminFourmi.contains(voisin)) {
                voisinsNonVisites.add(voisin);
            }
        }

    
        if (voisinsNonVisites.isEmpty()) {
            
            return cheminFourmi.get(0);
        }

        
        double[] probabilites = calculerProbabilites(graphe, noeudCourant, voisinsNonVisites, pheromones);
        double choix = random.nextDouble();
        double sommeProbabilites = 0.0;

        for (int i = 0; i < probabilites.length; i++) {
            sommeProbabilites += probabilites[i];
            if (choix <= sommeProbabilites) {
                return voisinsNonVisites.get(i);
            }
        }

        
        return voisinsNonVisites.get(voisinsNonVisites.size() - 1);
    }

    private static double[] calculerProbabilites(Graphe graphe, Noeud noeudCourant, ArrayList<Noeud> voisinsNonVisites, double[][] pheromones) {
        double[] probabilites = new double[voisinsNonVisites.size()];
        double somme = 0.0;
        double ALPHA = 1.0; // Influence de la phéromone
        double BETA = 3.0;  // Influence de la distance
        double RHO = 0.7;   // Taux d'évaporation de la phéromone

        for (int i = 0; i < voisinsNonVisites.size(); i++) {
            Noeud voisin = voisinsNonVisites.get(i);
            double pheromone = pheromones[noeudCourant.id-1][voisin.id-1];  //on utilise les id des noeud comme indice du tableau
            double distance = graphe.obtenirCoutEntreNoeuds(noeudCourant, voisin);

            // calcul de la proba de prendre chaque noeud
            probabilites[i] = Math.pow(pheromone, ALPHA) * Math.pow(1.0 / distance, BETA);
            somme += probabilites[i];
        }

        for (int i = 0; i < probabilites.length; i++) {
            probabilites[i] /= somme;
        }

        return probabilites;
    }

    private static void evaporationPheromones(double[][] pheromones) {
    	double RHO = 0.7;   // Taux d'évaporation de la phéromone

        for (int i = 0; i < pheromones.length; i++) {
            for (int j = 0; j < pheromones[i].length; j++) {
                pheromones[i][j] *= (1.0 - RHO);
            }
        }
    }

    private static void depotPheromones(double[][] pheromones, ArrayList<Noeud> chemin, Graphe graphe) {
        for (int i = 0; i < chemin.size() - 1 && i < pheromones.length; i++) {
            int indexNoeud1 = chemin.get(i).id - 1;
            int indexNoeud2 = chemin.get(i + 1).id - 1;

            pheromones[indexNoeud1][indexNoeud2] += (1.0 / calculerCoutChemin(chemin, graphe));
            pheromones[indexNoeud2][indexNoeud1] = pheromones[indexNoeud1][indexNoeud2];
        }
    }

    private static double calculerCoutChemin(ArrayList<Noeud> chemin, Graphe graphe) {
        double coutTotal = 0.0;

        for (int i = 0; i < chemin.size() - 1; i++) {
            Noeud noeudCourant = chemin.get(i);
            Noeud noeudSuivant = chemin.get(i + 1);

            coutTotal += graphe.obtenirCoutEntreNoeuds(noeudCourant, noeudSuivant);
        }

        return coutTotal;
    }
}

