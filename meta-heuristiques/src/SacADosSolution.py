
from Solution import Solution
import random

class SacADosSolution(Solution):


    def __init__(self, donnee, items, poids_max):  
       
        super().__init__(donnee)
        #on a besoin de la liste des items pour évaluer la solution
        self.items = items

        #on a beoin du poids max pour savoir si la sol est réalisable
        self.poids_max = poids_max

        self.valeur = 0  
        #self.evaluate()  




    def evaluate(self):
        
        poids_total = 0
        valeur_totale = 0

        for i in range(len(self.donnee)):
            if self.donnee[i] == 1:  
                poids_total += self.items[i][0] 
                valeur_totale += self.items[i][1]  

        
        if poids_total > self.poids_max:
            self.valeur = -1 
        else:
            self.valeur = valeur_totale  

        #on retourne - la valeur pour rester dans le cadre d'un problème de minimisation dans le cadre de notre programmation générique
        return -self.valeur

    def muter(self):
        

        indices = list(range(len(self.donnee)))
        random.shuffle(indices)  # Mélange les indices pour tester aléatoirement

        for idx in indices:
            self.donnee[idx] = 1 - self.donnee[idx]  # Inversion de l'état (0 → 1 ou 1 → 0)

            # Vérification de la validité après mutation
            self.evaluate()
            if self.valeur != -1:  # Si la mutation reste valide, on la garde
                return
            
            # Sinon, on annule la mutation
            self.donnee[idx] = 1 - self.donnee[idx]

   
    