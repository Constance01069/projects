from Methode import Methode
import random
class FirstFit(Methode):

    def __init__(self):
        super().__init__()
        self.nom="firstFit"
    
    
    
    def colorier(self,graphe,nbNoeuds):
        
        
        

        #on récupère les noeuds non coloriés du graphe
        noeudsNonColories = [noeud for noeud in graphe.noeuds if noeud.couleur == 0]

        #on n'en garde que le nombre indiqué en paramètres
        noeudsAcolorier = noeudsNonColories[:nbNoeuds]
        #coloriage des noeuds un à un 
        for noeud in noeudsAcolorier:

            

            
            couleurs_voisins = {voisin.couleur for voisin in noeud.voisins}

            
            couleur_a_utiliser = 1
            while couleur_a_utiliser in couleurs_voisins:
                couleur_a_utiliser += 1

            
            noeud.couleur = couleur_a_utiliser

            
            if couleur_a_utiliser not in graphe.couleursUtilisees:
                graphe.couleursUtilisees.append(couleur_a_utiliser)

            