import random
import time
from Solveur import Solveur

class AlgoGenet(Solveur):

    def __init__(self):
        
        super().__init__()

    def resoudre(self, problem, time_limit):

        start_time = time.time()

        taille_population = 200

        taux_mutation = 0.1

        
        pop_initiale = [problem.get_initial_solution() for _ in range(taille_population)]

        sol_avec_couts = [(sol.donnee, problem.evaluer_solution(sol)) for sol in pop_initiale]

        
        best_solution = min(sol_avec_couts, key=lambda x: x[1])
        current_best = min(sol_avec_couts, key=lambda x: x[1])
        while time.time() - start_time < time_limit:

            nouvelle_pop = problem.creerSol([])

            
            for _ in range(taille_population // 4):

                tournoi = random.sample(sol_avec_couts, 50)
                parent1 = min(tournoi, key=lambda x: x[1])
                tournoi.remove(parent1)
                parent2 = min(tournoi, key=lambda x: x[1])

                enfant1, enfant2 = self.crossover(parent1[0], parent2[0])
                nouvelle_pop.donnee.append(parent1[0])
                nouvelle_pop.donnee.append(parent2[0])
                nouvelle_pop.donnee.append(enfant1)
                nouvelle_pop.donnee.append(enfant2)

            
            for i in range(taille_population):

                if random.random() < taux_mutation:
                    nouvelle_pop.donnee[i] = self.mutate(nouvelle_pop.donnee[i])

            
            sol_avec_couts = [(sol, problem.evaluer_solution(problem.creerSol(sol))) for sol in nouvelle_pop.donnee]

            sol_avec_couts.append(current_best)

            sol_avec_couts.sort(key=lambda x: x[1])

            sol_avec_couts = sol_avec_couts[:taille_population]

            
            current_best = min(sol_avec_couts, key=lambda x: x[1])

            if current_best[1] < best_solution[1]:
                best_solution = current_best

        
        return best_solution

    def crossover(self, parent1, parent2):

        n = len(parent1)
        k = random.randint(1, n // 2)
        indices = random.sample(range(n), k)

        enfant1 = [None] * n
        enfant2 = [None] * n

        for i in indices:

            enfant1[i] = parent1[i]
            enfant2[i] = parent2[i]

        remaining_parent2 = [gene for gene in parent2 if gene not in enfant1]

        remaining_parent1 = [gene for gene in parent1 if gene not in enfant2]

        for i in range(n):

            if enfant1[i] is None:
                enfant1[i] = remaining_parent2.pop(0)
            if enfant2[i] is None:
                enfant2[i] = remaining_parent1.pop(0)

        return enfant1, enfant2

    def mutate(self, solution):

        n = len(solution)

        i, j = random.sample(range(n), 2)

        solution[i], solution[j] = solution[j], solution[i]
        
        return solution