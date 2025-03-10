from Methode import Methode
import random
class WelshPowell(Methode):

    def __init__(self):
        super().__init__()
        self.nom="WelshPowell"

    def colorier(self, graphe,nbNoeuds):
    # On initialise les degrés des noeuds
        
        
        for noeud in graphe.noeuds:
            noeud.degre = len(noeud.voisins)

    # On trie les noeuds par ordre décroissant
        noeudsNonColories = [noeud for noeud in graphe.noeuds if noeud.couleur == 0]

        
        noeudsAcolorier = noeudsNonColories[:nbNoeuds]
        noeuds_tries = sorted(noeudsAcolorier, key=lambda x: x.degre, reverse=True)

    
        #coloriage des noeuds un par dans l'orodre dans lequel ils sont triés
        while noeuds_tries:
        
            noeud = noeuds_tries.pop(0)

        
            couleurs_voisins = {voisin.couleur for voisin in noeud.voisins}

        
            couleur_a_utiliser = 1
            while couleur_a_utiliser in couleurs_voisins:
                couleur_a_utiliser += 1

            noeud.couleur = couleur_a_utiliser

        
            if couleur_a_utiliser not in graphe.couleursUtilisees:
                graphe.couleursUtilisees.append(couleur_a_utiliser)

        
            nonAdjacents = [noeudt for noeudt in noeuds_tries if noeudt not in noeud.voisins]

            # On colorie les noeuds adjacents avec la même couleur si possible
            for noeudnonAdj in nonAdjacents:
                couleurs_voisins_nonAdj = {voisin.couleur for voisin in noeudnonAdj.voisins}
                if couleur_a_utiliser not in couleurs_voisins_nonAdj:
                    noeudnonAdj.couleur = couleur_a_utiliser
                    noeuds_tries.remove(noeudnonAdj)  # On supprime le nœud colorié de la liste
