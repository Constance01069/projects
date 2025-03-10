from Methode import Methode
import random
import os;
from FirstFit import FirstFit
from Graphe import Graphe
from sdo import sdo
from Dsatur import Dsatur
import copy
from WelshPowell import WelshPowell
from collections import Counter



class hyperHeuristique(Methode):

    def __init__(self):
        super().__init__()

        
        self.heuristiquesDisponibles = []
        self.meilleureCombinaison = []
        self.historiqueTabou = set()
        self.coupuresTabou = 100
        self.listeGraphesEntrainement = []
        self.meilleurScore = float('inf')
        self.nom="Hyper-heuristique"

        


    def rechercherSol(self,listeGraphe):
        #initialisation
        heuristiquesDisponibles = [sdo(),FirstFit(),Dsatur(),WelshPowell()]
        meilleursSolutionsdeChaqueGraphe=[]
        meileureSol=[]
        nbIter=2000
        heuristiquesTirees = random.choices(heuristiquesDisponibles, k=4)
        tailleTabou = 100 

        #on parcourt les graphes de l'ensemble d'entraînement
        for graphe in listeGraphe:

            
            

            #initialisation pour chaque graphe
            solutionTestee = random.choices(heuristiquesDisponibles, k=8)  
            meilleureSolution = copy.deepcopy(solutionTestee)  
            meilleurScore = 300  
            listeTaboue = []  

            #on itère pour rechercher la meilleure séquence
            for iteration in range(nbIter):
    
                nouvelleSolution = copy.deepcopy(solutionTestee)
                indices = random.sample(range(8), 2)  
                nouvelleSolution[indices[0]], nouvelleSolution[indices[1]] = (
                    random.choice(heuristiquesDisponibles),
                    random.choice(heuristiquesDisponibles),
                )

                #on n'évalue pas la quelité de la nouvelle séquence si elle est déja présente dans la liste Tabu
                if nouvelleSolution in listeTaboue:
                    continue  

                #on colorie le graphe avec la nouvelle séquence d'heuristiques pour évaluer sa qualité
                tailleDecoupe = graphe.obtenirNbNoeuds() // 8
                reste = graphe.obtenirNbNoeuds() % 8
                for i in range(8):
                    if i == 7:  
                        nouvelleSolution[i].colorier(graphe, tailleDecoupe + reste)
                    else:
                        nouvelleSolution[i].colorier(graphe, tailleDecoupe)

    
                nouveauScore = graphe.obtenirNbCouleurs()
                
                graphe.effacerCouleurs()
                #mise à jour si meilleure
                if nouveauScore < meilleurScore:  
                    meilleureSolution = copy.deepcopy(nouvelleSolution)
                    meilleurScore = nouveauScore
                    #print("nombre de couleurs inférieur trouvé : ",nouveauScore,"donc mise à jour de la meilleure combinaison avec :" )
                    #print(f"Meilleure solution : {[type(heuristique).__name__ for heuristique in meilleureSolution]}")
                    
                    
                #ajout à la liste Tabou
                listeTaboue.append(nouvelleSolution)
                if len(listeTaboue) > tailleTabou:  
                    listeTaboue.pop(0)

    
                solutionTestee = copy.deepcopy(meilleureSolution)

                #graphe.afficher_graphe
                #print(graphe.obtenirNbCouleurs())
                #graphe.effacerCouleurs()
                #print(f"Iteration {iteration + 1}/{nbIter}")
                #print(f"Solution testée : {[type(heuristique).__name__ for heuristique in solutionTestee]}")
                #print(f"Score de la solution testée : {nouveauScore}")
                #print(f"Meilleure solution : {[type(heuristique).__name__ for heuristique in meilleureSolution]}")
                #print(f"Score de la meilleure solution : {meilleurScore}")
                #graphe.afficher_graphe
                #print(graphe.obtenirNbCouleurs())
            #print(f"Meilleure solution par graphe : {[type(heuristique).__name__ for heuristique in meilleureSolution]}")
            meilleursSolutionsdeChaqueGraphe.append(meilleureSolution)
            #print(f"Nombre de solutions stockées : {len(meilleursSolutionsdeChaqueGraphe)}")
            #for i, sol in enumerate(meilleursSolutionsdeChaqueGraphe):
                #print(f"Solution {i} : {[type(h).__name__ for h in sol]}")
        


        
        #recherche de l'heuristique la plus fréquente à chaque position dans l'ensemble de séquences trouvées
        print(f"Nombre de solutions stockées : {len(meilleursSolutionsdeChaqueGraphe)}")
        for i, sol in enumerate(meilleursSolutionsdeChaqueGraphe):
            print(f"Solution {i} : {[type(h).__name__ for h in sol]}")
        for i in range(8):  
            try:
                heuristiques = [solution[i] for solution in meilleursSolutionsdeChaqueGraphe]
                print(f"Heuristiques en position {i+1} : {[type(h).__name__ for h in heuristiques]}")
            except IndexError:
                print(f"⚠ ERREUR : Certaines solutions n'ont pas assez d'heuristiques pour l'index {i}")
        


        
        #création d'un dictionnaire avec correspondance nom heuristique et heuristique
        heuristique_dict = {
            "sdo": sdo(),
            "FirstFit": FirstFit(),
            "Dsatur": Dsatur(),
            "WelshPowell": WelshPowell()
        }

        
        meilleure_combinaison = []

        #pour chaque position on trouve l'heuristique la plus fréquente grâce au counter puis on trouve son type grâce au dictionnaire et au l'ajoute à la meilleure
        #séquence finale
        for i in range(8):  
            heuristiques = [type(solution[i]).__name__ for solution in meilleursSolutionsdeChaqueGraphe]
    
        
            heuristique_plus_frequente = Counter(heuristiques).most_common(1)[0][0]
    
    
            self.meilleureCombinaison.append(heuristique_dict[heuristique_plus_frequente])


        print(f"Meilleure séquence finale : {[type(h).__name__ for h in self.meilleureCombinaison]}")

        
        
            
    def colorier(self,graphe, nbNoeuds):
        #on divise les noeuds du graphe de manière à colorier selon la séquence d'heuristiques trouvée
        tailleDecoupe = graphe.obtenirNbNoeuds() // 8
        reste = graphe.obtenirNbNoeuds() % 8
        for i in range(8):
            if i == 7:  
                self.meilleureCombinaison[i].colorier(graphe, tailleDecoupe + reste)
            else:
                self.meilleureCombinaison[i].colorier(graphe, tailleDecoupe)







          
