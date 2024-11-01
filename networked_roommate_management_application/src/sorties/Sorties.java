package sorties;
import java.util.ArrayList;
import java.util.List;

import personnes.Colocataire;

public class Sorties {
    private List<Bars> barsDansLordre;
    private List<Colocataire> participants;
    private Bars[][] plan;
    private double tempsTrajets;

    public Sorties(List<Bars> barsDansLordre, List<Colocataire> participants, Bars[][] plan, double tempsTrajets) {
        this.barsDansLordre = barsDansLordre;
        this.participants = participants;
        this.plan = plan;
        this.tempsTrajets = tempsTrajets;
    }

    public List<Bars> getBarsDansLordre() {
        return barsDansLordre;
    }

    public List<Colocataire> getParticipants() {
        return participants;
    }

    public Bars[][] getPlan() {
        return plan;
    }

    public double getTempsTrajets() {
        return tempsTrajets;

    }

    public void organiserSortie(List<Bars> barsDisponibles) {
        // Sélectionner 5 bars aléatoirement parmi la liste des bars disponibles
        List<Bars> barsSelectionnes = new ArrayList<>();
        // Assurez-vous que la liste des bars disponibles n'est pas vide avant de la parcourir
        if (!barsDisponibles.isEmpty()) {
            for (int i = 0; i < 5; i++) {
                int indexAleatoire = (int) (Math.random() * barsDisponibles.size());
                barsSelectionnes.add(barsDisponibles.get(indexAleatoire));
            }
        }

        // Créer une nouvelle sortie avec les bars sélectionnés
    }

    public void afficherListeBars(List<Bars> bars) {
        System.out.println("Liste des bars disponibles:");
        for (int i = 0; i < bars.size(); i++) {
            System.out.println((i + 1) + ". " + bars.get(i).getNom());
        }
    }



}

