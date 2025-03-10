import random
import math
import time
from Solveur import Solveur

class RecuitSimule(Solveur):

    def __init__(self, initial_temperature, cooling_rate):

        super().__init__()
        self.initial_temperature = initial_temperature
        self.cooling_rate = cooling_rate

    def resoudre(self, problem, time_limit):
        
        start_time = time.time()

        
        current_solution = problem.get_initial_solution()
        
        current_distance = problem.evaluer_solution(current_solution)

        best_solution = problem.creerSol(current_solution.donnee)
        best_distance = current_distance

        temperature = self.initial_temperature

        while time.time() - start_time < time_limit:
            
            new_solution = problem.creerSol(current_solution.donnee)

            new_solution.muter()

            
            new_distance = problem.evaluer_solution(new_solution)

            
            if new_distance < current_distance or \
                    random.random() < math.exp((current_distance - new_distance) / temperature):
                current_solution = new_solution

                current_distance = new_distance

                
                if current_distance < best_distance:
                    best_solution = problem.creerSol(current_solution.donnee)

                    best_distance = current_distance

            
            temperature *= self.cooling_rate
            #if best_distance==new_distance:
                #print(best_distance)

        return best_solution, best_distance

