from Solution import Solution
import random

class VoyageurSolution(Solution):

    def __init__(self, donnee,items,poids_max):  
        super().__init__(donnee)
        self.items = items # inutile mais on en a besoin dans le problème du sac à dos dans sa fonction evaluate, sûrement pas la meilleure façon d'implémenter 
        self.poids_max = poids_max # inutile mais on en a besoin dans le problème du sac à dos dans sa fonction est réalisable , sûrement pas la meilleure façon d'implémenter
        self.valeur = 0  
        
    #evaluer le coût de la solution et met à jour l'attribut valeur de la solution
    #def evaluate(self):
        #def distance(ville1, ville2):
            
            #return ((ville1[0] - ville2[0]) ** 2 + (ville1[1] - ville2[1]) ** 2) ** 0.5

        #total_distance = 0

        #for i in range(len(self.donnee) - 1):
            #total_distance += distance(self.donnee[i], self.donnee[i + 1])


        #total_distance += distance(self.donnee[-1], self.donnee[0])

        #self.valeur = total_distance
        #return self.valeur
        

    #fonction utilisée dans le solveur escalade et recuit simulé pour trouver une solution voisine
    def muter(self):
        
       
        
        if len(self.donnee) >= 2:
            i, j = random.sample(range(len(self.donnee)), 2)
            self.donnee[i], self.donnee[j] = self.donnee[j], self.donnee[i]

    

    
    def __len__(self):
        
        return len(self.donnee)
    
 