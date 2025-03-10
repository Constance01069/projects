import random
import math
import time
from Solveur import Solveur

import random
import math

class AlgoFourmis(Solveur):

    def __init__(self, num_fourmis, alpha, beta, taux_evaporation, intensification):
        
        
        super().__init__()
        self.num_fourmis = num_fourmis
        self.alpha = alpha
        self.beta = beta
        self.taux_evaporation = taux_evaporation
        self.intensification = intensification
        self.pheromones = None



    def resoudre(self, problem, time_limit):
        
        
        start_time = time.time()
        
        num_villes = len(problem.villes)

        self.pheromones = [[1.0 for _ in range(num_villes)] for _ in range(num_villes)]  
        
        meilleureSol = None

        meilleureDistance = float('inf')
        
        while time.time() - start_time < time_limit:

            all_solutions = []
            
            
            for _ in range(self.num_fourmis):
                solution = self.construct_solution(problem)
                distance = problem.evaluer_solution(solution)
                all_solutions.append((solution, distance))
                
                if distance < meilleureDistance:
                    meilleureSol = solution
                    meilleureDistance = distance
            
            
            self.update_pheromones(all_solutions, meilleureDistance, problem)
        
        return meilleureSol, meilleureDistance

    def construct_solution(self, problem):
        
        num_villes = len(problem.villes)

        villes_nonVisitees = list(range(num_villes))

        path = []
        
        villeCourante = random.choice(villes_nonVisitees)

        path.append(villeCourante)

        villes_nonVisitees.remove(villeCourante)
        

        while villes_nonVisitees:
            probabilites = self.calculerProba(villeCourante, villes_nonVisitees, problem)
            villeSuivante = random.choices(villes_nonVisitees, weights=probabilites)[0]
            path.append(villeSuivante)
            villes_nonVisitees.remove(villeSuivante)
            villeCourante = villeSuivante
        
        
        solution_data = [problem.villes[i] for i in path]

        return problem.creerSol(solution_data)

    def calculerProba(self, villeCourante, villes_nonVisitees, problem):
        
        probabilites = []

        for villeSuivante in villes_nonVisitees:
            pheromone = self.pheromones[villeCourante][villeSuivante] ** self.alpha
            heuristic = (1.0 / problem.distance(problem.villes[villeCourante], problem.villes[villeSuivante])) ** self.beta
            probabilites.append(pheromone * heuristic)
        
        total = sum(probabilites)

        return [p / total for p in probabilites] if total > 0 else [1 / len(villes_nonVisitees)] * len(villes_nonVisitees)

    
    def update_pheromones(self, all_solutions, meilleureDistance, problem):
    
    
        for i in range(len(self.pheromones)):

            for j in range(len(self.pheromones[i])):

                self.pheromones[i][j] *= (1 - self.taux_evaporation)

    
        for solution, distance in all_solutions:

            if distance <= meilleureDistance:
            
                indices = [problem.villes.index(city) for city in solution.donnee]

                for i in range(len(indices) - 1):
                    
                    ville1, ville2 = indices[i], indices[i + 1]
                    deposition = self.intensification / distance
                    self.pheromones[ville1][ville2] += deposition
                    self.pheromones[ville2][ville1] += deposition
