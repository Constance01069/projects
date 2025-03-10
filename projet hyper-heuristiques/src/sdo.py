from Methode import Methode
import random
class sdo(Methode):

    def __init__(self):
        super().__init__()
        self.nom="sdo"

    def colorier(self, graphe,nbNoeuds):
        
        #on calcule les degrès des noeuds
        for noeud in graphe.noeuds:
            noeud.degre = len(noeud.voisins)

        noeudsNonColories = [noeud for noeud in graphe.noeuds if noeud.couleur == 0]

        
        noeudsAcolorier = noeudsNonColories[:nbNoeuds]
    # seul changement par rapport à l'algo WelshPowell, les noeuds sont triés dans l'ordre décroissants
        noeuds_tries = sorted(noeudsAcolorier, key=lambda x: x.degre)

    
        

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

            #coloriage des noeuds non adjacents si possible
            for noeudnonAdj in nonAdjacents:
                couleurs_voisins_nonAdj = {voisin.couleur for voisin in noeudnonAdj.voisins}
                if couleur_a_utiliser not in couleurs_voisins_nonAdj:
                    noeudnonAdj.couleur = couleur_a_utiliser
                    noeuds_tries.remove(noeudnonAdj)  
