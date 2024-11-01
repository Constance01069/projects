package graphes;

import java.util.*;

public class Test {
    public static void main(String[] args) {
    	HashMap<String,Integer> suiv;
    	HashMap<String,Integer> suiv1;
    	ArrayList<Noeud> ln;
    	Noeud n1,n2;
    	suiv=new HashMap<String,Integer>();
    	suiv1=new HashMap<String,Integer>();
    	ArrayList<Noeud> chemin;
    	
    	
    	suiv.put("B",1);
    	suiv.put("V",3);
    	suiv.put("U",2);
    	suiv1.put("B",2);
    	suiv1.put("V",2);
    	
    	
    	
    	ln=new ArrayList<Noeud>();
    	
    	Graphe g1=new Graphe();
    	Graphe g2= new Graphe(ln,2);
    	
    	
    	g1.charger("graphes/doc.txt");
    	for (Noeud n : g1.noeuds){
    		g1.afficherVD(n);
    	}
    	
    	
	// chemin = Sortie.barathonDepuisBar("Le Vixen");
	// for(Noeud b : chemin){
    //     	System.out.println(Sortie.lettreEnNom(b.getBar()));
    //     }
        
	
	ArrayList<String> chemin2= Sortie.plusCourtCheminEntre2Bars("Le Vixen","Le Shadow");
	for(String a : chemin2) {
		System.out.println(a);
	}
    }
}
