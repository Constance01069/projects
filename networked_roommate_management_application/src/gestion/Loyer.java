package gestion;

import java.io.Serializable;

public class Loyer implements Serializable {
    private static final long serialVersionUID = 1L;
    private String mois;
    private double montant;
    private boolean estPaye;

    public Loyer(String mois, double montant, boolean estPaye) {
        this.mois = mois;
        this.montant = montant;
        this.estPaye = estPaye;
    }

    public String getMois() {
        return mois;
    }

    public void setMois(String mois) {
        this.mois = mois;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public boolean estPaye() {
        return estPaye;
    }

    public void setEstPaye(boolean estPaye) {
        this.estPaye = estPaye;
    }
}