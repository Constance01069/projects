package graphes;

class NoeudAvecDistance implements Comparable<NoeudAvecDistance> {
    Noeud noeud;
    double distance;

    public NoeudAvecDistance(Noeud noeud, double distance) {
        this.noeud = noeud;
        this.distance = distance;
    }

    @Override
    public int compareTo(NoeudAvecDistance autre) {
        return Double.compare(this.distance, autre.distance);
    }
}

