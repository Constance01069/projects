from Probleme import Probleme
from Solution import Solution
import random
from Solveur import Solveur
from VoyageurSolution import VoyageurSolution

class Voyageur(Probleme):

    def __init__(self):
        self.villes = []
        self.max=0
        self.items=[]

    #fonction pour charger une instance du problème du voyageur depuis un fichier .tsp
    def load_from_file(self, filename):
        
        with open(filename, 'r') as file:
            start_reading = False
            for line in file:
                if line.strip() == "NODE_COORD_SECTION":
                    start_reading = True
                    continue
                if line.strip() == "EOF":
                    break
                if start_reading:
                    parts = line.strip().split()
                    if len(parts) >= 3:
                        x, y = float(parts[1]), float(parts[2])
                        self.villes.append((x, y))
    
    #fonction pour créer une solution du problème du voyageur dont les villes dans l'ordre de visite sont contenues dans la liste de villes lis
    #lis est la forme [1,7,5,3,4,2,0,6] qui représente les villes dans leur ordre de visite
    def creerSol(self,lis):
        return VoyageurSolution(lis,[],0)

    #creer une solution initiale aléatoire, c'est à dire un ordre de villes aléatoire
    def get_initial_solution(self):
        import random
        villes_Melangees = self.villes[:]  
        random.shuffle(villes_Melangees)
        return self.creerSol(villes_Melangees)
    
    #calculer la distance parcourue en visitant les villes dans l'ordre contenu dans solution
    def evaluer_solution(self, solution):
        total_distance = 0

        for i in range(len(solution.donnee) - 1):
            ville1 = solution.donnee[i]
            ville2 = solution.donnee[i + 1]
            total_distance += self.distance(ville1, ville2)


        # ne pas oublier le retour à la première ville
        solution.valeur = total_distance+self.distance(solution.donnee[len(solution.donnee)-1], solution.donnee[0]) 
        
        return solution.valeur
    
    #cette fonction, bien qu'inutile ici, est nécessaire dans le cadre de la programmation générique car elle est utile pour le problème du sac à dos où certaines solutions peuvent ne pas être réalisables
    def est_sol_realisable(self, solution):
        
        return True



    #calcul de la distance euclidienne entre 2 villes
    def distance(self, ville1, ville2):
        
        return ((ville1[0] - ville2[0]) ** 2 + (ville1[1] - ville2[1]) ** 2) ** 0.5
