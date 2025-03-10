from Methode import Methode
import random
class Dsatur(Methode):

    def __init__(self):
        super().__init__()
        self.nom="dsatur"

    def colorier(self, graphe,nbNoeuds):
    
        
        #obtention des noeuds non coloriés du graphe
        noeudsNonColories = [noeud for noeud in graphe.noeuds if noeud.couleur == 0]

        
        noeudsAcolorier = noeudsNonColories[:nbNoeuds] 
        #triage selon le nombre de couleurs voisines différentes
        noeudsTries = sorted(noeudsAcolorier, key=lambda x: x.nbCouleursVoisines)

        #coloriage selon cet ordre et mise à jour de ce classement après chaque coloriage
        while noeudsTries:

            
        
            noeud = noeudsTries.pop(0)

        
            couleurs_voisins = {voisin.couleur for voisin in noeud.voisins}

        
            couleur_a_utiliser = 1
            while couleur_a_utiliser in couleurs_voisins:
                couleur_a_utiliser += 1

            noeud.couleur = couleur_a_utiliser

        
            if couleur_a_utiliser not in graphe.couleursUtilisees:
                graphe.couleursUtilisees.append(couleur_a_utiliser)

            for nouedC in noeud.voisins:
                nouedC.calculDesAttributsDeductibles

            #on doit retrier les noeuds à chaque itération car le fait de colorier un noeud peut modifier le degré de saturation de ces voisins
            noeudsTries = sorted(noeudsTries, key=lambda x: x.nbCouleursVoisines)

            