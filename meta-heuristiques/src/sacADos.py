from Probleme import Probleme
from Solution import Solution
import random
from Solveur import Solveur
from SacADosSolution import SacADosSolution


import random

class SacADos(Probleme):

    def __init__(self):


        # liste des objets sous forme de tuple (poids, valeur)
        self.items = []  
        # poids maximum du sac à dos
        self.max = 0  



    #lis est de la forme [0,0,1,0,1,1,1,0] où lis[i] vaut 1 si l'item i est mis dans le sac à dos et 0 sinon
    def creerSol(self, lis):
        
        return SacADosSolution(lis, self.items, self.max)


    def evaluer_solution(self, solution):
        
        valeur = 0

        for i, selectionne in enumerate(solution.donnee):  
            if selectionne == 1:  
                valeur += self.items[i][1]  

        solution.valeur = valeur

        return -solution.valeur




    def est_sol_realisable(self, solution):
        
        poids_total = sum(self.items[i][0] for i in range(len(solution.donnee)) if solution.donnee[i] == 1)

        return poids_total <= self.max


    #construit une solution aléatoire réalisable (c'est à dire de poids inférieur au poids max du sac à dos)
    def get_initial_solution(self):

        solution = [0] * len(self.items)  

        poids_total = 0

        indices = list(range(len(self.items)))

        random.shuffle(indices)  

        for i in indices:
            poids, _ = self.items[i]
            if poids_total + poids <= self.max:  
                solution[i] = 1  
                poids_total += poids

        return SacADosSolution(solution, self.items, self.max)



    
    
    #cette fonction crée aléatoirement une instance du problème du sac à dos en vérifiant quelques critères
    #comme par exemple que le poids total de tous les objets soit supérieur au poids max du sac à dos sinon le problème n'a pas d'intérêts
    def creer_instance_aleatoire(self, n, max_weight):
        
    
        self.max = max_weight
        self.items = []

        
        total_weight = 0
        poids_cible = random.uniform(3, 5) * max_weight  

        for _ in range(n):
            
            #pour éviter les objects trop lours :
            poids = random.randint(int(0.05 * max_weight), int(0.2 * max_weight))

            #pour que la valeur de l'objet ne soit pas complétement indépendante de son poids
            valeur = poids * random.uniform(2, 5)  

            self.items.append((poids, int(valeur)))
            total_weight += poids

        


    #algorithme glouton qui est très bon pour résoudre le problème du sac à dos, nous prendrons cette solution (quasiment optimale) pour évaluer les solutions obtenues avec les autres algorithmes
    def obtenir_Sol_Glouton(self):

        # on trie les objets par ratio valeur/poids
        self.items.sort(key=lambda item: item[1] / item[0], reverse=True)  

        solution_gloutonne = self.creerSol([0] * len(self.items))  
        poids_total = 0

        #on les ajoute dans cet ordre jusqu'à remplir le sac
        for i, (poids, valeur) in enumerate(self.items):
            if poids_total + poids <= self.max:
                solution_gloutonne.donnee[i] = 1  
                solution_gloutonne.valeur += valeur
                poids_total += poids

        return solution_gloutonne
