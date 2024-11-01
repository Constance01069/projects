package graphes;

import java.util.ArrayList;

public class Sortie {
	public static String PWD_FICHER_GRAPHE = "../src/graphes/doc.txt";

	public static ArrayList<String> barathonDepuisBar(String nomBar) {
		Graphe graphe = new Graphe();
		graphe.charger(PWD_FICHER_GRAPHE);

		ArrayList<String> meilleurChemin = noeud2Str(AlgorithmeFourmis.algorithmeFourmis(graphe, nomEnLettre(nomBar)));
		
		return meilleurChemin;
	}

	public static ArrayList<String> plusCourtCheminEntre2Bars(String bar1, String bar2) {
		Graphe graphe = new Graphe();
		graphe.charger(PWD_FICHER_GRAPHE);

		ArrayList<String> meilleurChemin = noeud2Str(Dijkstra.plusCourtChemin(graphe, nomEnLettre(bar1), nomEnLettre(bar2)));

		return meilleurChemin;
	}
	
	public static String[] listeBars() {
		String[] liste=new String[24];
		
		liste[0] = "L'Estaminet";
		liste[1] = "O Bon Son";
		liste[2] = "Ma-Kao Bar";
		liste[3] = "Le Shadow";
		liste[4] = "Le Socrate";
		liste[5] = "Le QG des Avenjoueurs";
		liste[6] = "O'Kallaghan";
		liste[7] = "Le 3 pieces";
		liste[8] = "La Porte Ouverte";
		liste[9] = "Le Vixen";
		liste[10] = "Le Broker";
		liste[11] = "Le Victor Hugo";
		liste[12] = "La Fée Torchette";
		liste[13] = "Le Petit Bar Cocktail";
		liste[14] = "Underdogs";
		liste[15] = "La Taverne de Thor";
		liste[16] = "Le Delirium";
		liste[17] = "Le Vicomté";
		liste[18] = "JM's Café";
		liste[19] = "Le Social Club";
		liste[20] = "La Boite à Bière";
		liste[21] = "Cocoon Bar";
		liste[22] = "Popcorn";
		liste[23] = "L'Antre du Malt";

		return liste;
	}

	private static String nomEnLettre(String nom) {
		String lettre = "";
		switch (nom) {
			case "L'Estaminet" :
				lettre = "A";
				break;
			case "O Bon Son" :
				lettre = "B";
				break;
			case "Ma-Kao Bar" :
				lettre = "C";
				break;
			case "Le Shadow" :
				lettre = "D";
				break;
			case "Le Socrate" :
				lettre = "E";
				break;
			case "Le QG des Avenjoueurs" :
				lettre = "F";
				break;
			case "O'Kallaghan" :
				lettre = "G";
				break;
			case "Le 3 pieces" :
				lettre = "H";
				break;
			case "La Porte Ouverte" :
				lettre = "I";
				break;
			case "Le Vixen" :
				lettre = "J";
				break;
			case "Le Broker" :
				lettre = "K";
				break;
			case "Le Victor Hugo" :
				lettre = "L";
				break;
			case "La Fée Torchette" :
				lettre = "M";
				break;
			case "Le Petit Bar Cocktail" :
				lettre = "N";
				break;
			case "Underdogs" :
				lettre = "O";
				break;
			case "La Taverne de Thor" :
				lettre = "P";
				break;
			case "Le Delirium" :
				lettre = "Q";
				break;
			case "Le Vicomté" :
				lettre = "R";
				break;
			case "JM's Café" :
				lettre = "S";
				break;
			case "Le Social Club" :
				lettre = "T";
				break;
			case "La Boite à Bière" :
				lettre = "U";
				break;
			case "Cocoon Bar" :
				lettre = "V";
				break;
			case "Popcorn" :
				lettre = "W";
				break;
			case "L'Antre du Malt" :
				lettre = "X";
				break;
		}

		return lettre;
	}
		
	public static String lettreEnNom(String lettre) {
		String nom = "";
		switch (lettre) {
			case "A" :
				nom = "L'Estaminet";
				break;
			case "B" :
				nom = "O Bon Son";
				break;
			case "C" :
				nom = "Ma-Kao Bar";
				break;
			case "D" :
				nom = "Le Shadow";
				break;
			case "E" :
				nom = "Le Socrate";
				break;
			case "F" :
				nom = "Le QG des Avenjoueurs";
				break;
			case "G" :
				nom = "O'Kallaghan";
				break;
			case "H" :
				nom = "Le 3 pieces";
				break;
			case "I" :
				nom = "La Porte Ouverte";
				break;
			case "J" :
				nom = "Le Vixen";
				break;
			case "K" :
				nom = "Le Broker";
				break;
			case "L" :
				nom = "Le Victor Hugo";
				break;
			case "M" :
				nom = "La Fée Torchette";
				break;
			case "N" :
				nom = "Le Petit Bar Cocktail";
				break;
			case "O" :
				nom = "Underdogs";
				break;
			case "P" :
				nom = "La Taverne de Thor";
				break;
			case "Q" :
				nom = "Le Delirium";
				break;
			case "R" :
				nom = "Le Vicomté";
				break;
			case "S" :
				nom = "JM's Café";
				break;
			case "T" :
				nom = "Le Social Club";
				break;
			case "U" :
				nom = "La Boite à Bière";
				break;
			case "V" :
				nom = "Cocoon Bar";
				break;
			case "W" :
				nom = "Popcorn";
				break;
			case "X" :
				nom = "L'Antre du Malt";
				break;
		}

		return nom;
	}

	private static ArrayList<String> noeud2Str(ArrayList<Noeud> listeNoeuds) {
		ArrayList<String> listeString = new ArrayList<>();

		for (Noeud noeud : listeNoeuds) {
			listeString.add(lettreEnNom(noeud.getBar()));
		}

		return listeString;
	}
	
}
