import random
import math
import time
from Solveur import Solveur

class Escalade(Solveur):

    def __init__(self):

        super().__init__()
        

    def resoudre(self, problem, time_limit):
        
        start_time = time.time()

        
        current_solution = problem.get_initial_solution()
        
        current_distance = problem.evaluer_solution(current_solution)

        best_solution = problem.creerSol(current_solution.donnee)
        best_distance = current_distance

        

        while time.time() - start_time < time_limit:
            
            new_solution = problem.creerSol(current_solution.donnee)
            new_solution.muter()

            
            new_distance = problem.evaluer_solution(new_solution)

            
            if new_distance < current_distance:
                current_solution = new_solution
                current_distance = new_distance

                
                if current_distance < best_distance:
                    best_solution = problem.creerSol(current_solution.donnee)
                    best_distance = current_distance

            
           
            #if best_distance==new_distance:
                #print(best_distance)

        return best_solution, best_distance

