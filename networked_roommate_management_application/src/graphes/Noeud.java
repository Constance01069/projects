package graphes;

import java.io.Serializable;
import java.util.*;


class Noeud implements Serializable {
    public int id;
    public String bar;
    public HashMap<String, Integer> suiv;

    public Noeud() {
        this.bar = " ";
        this.suiv = new HashMap<>();
    }

    public Noeud(int id, String bar, HashMap<String, Integer> suiv) {
        this.id = id;
        this.bar = bar;
        this.suiv = suiv;
    }

    public String getBar() {
        return this.bar;
    }
}
