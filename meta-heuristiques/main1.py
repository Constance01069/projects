import time
from pathlib import Path
import sys

sys.dont_write_bytecode = True  # Désactive complètement l'utilisation du cache

# Définition des chemins
BASE_DIR = Path(__file__).resolve().parent
SRC_DIR = BASE_DIR / "src"

# Ajout du dossier src/ au path pour que Python trouve les modules
sys.path.append(str(SRC_DIR))
from src.VoyageurSolution import VoyageurSolution
from src.recuitSimule import RecuitSimule
from src.Solution import Solution
from src.Solveur import Solveur
from src.voyageur import Voyageur
from src.algoFourmis import AlgoFourmis
from src.algoGenet import AlgoGenet
from src.sacADos import SacADos
from src.escalade import Escalade
import os


def main():

    # Définir le répertoire de base du projet
    base_dir = Path(__file__).parent.resolve()  # 📌 Chemin du dossier du script

    # Définir les chemins des sous-dossiers
    data_dir = base_dir / "data"       # 📁 Contient les fichiers d'instance
    results_dir = base_dir / "results"  # 📁 Contient les fichiers de résultats
    src_dir = base_dir / "src"         # 📁 Contient les classes

    # Assurer que le dossier de résultats existe
    results_dir.mkdir(exist_ok=True)
    #placement dans le bon dossier
    #dossier = "/home/bauc/Documents/projet_MH/"

    #CHOIX DES INSTANCES DES PROBLEMES
    ##CHOIX FICHIER TSP VOYAGEUR
    nomfich = "wi29.tsp"
    #nom_fich_complet = os.path.join(dossier, nomfich)
    nom_fich_complet = data_dir / nomfich

    ##CHOIX NB OBJETS ET POIDS MAX SAC A DOS
    nb_objets = 500  
    poids_max = 1200  
    
    #CHOIX DU TEMPS
    temps_Voyageur = 250
    temps_SacAdos=100

   
    #CHOIX DES PARAMETRES DES SOLVEURS
    ##RECUIT SIMULE
    temperature_Initiale = 10000 
    taux_refroidissement = 0.95  
    ##ALGO FOURMIS
    num_fourmis=200
    alpha=1
    beta= 2
    taux_evaporation=0.85
    intensification=10

    #Chargement des problèmes
    problemeVoyageur = Voyageur()
    problemeVoyageur.load_from_file(nom_fich_complet)

    

    
    problemeSacAdos = SacADos()
    problemeSacAdos.creer_instance_aleatoire(nb_objets, poids_max)

    
    
    
    
    #Chargement des solveurs
    solveurRecuitSimule = RecuitSimule(temperature_Initiale, taux_refroidissement)
    solveurAlgoFourmis=AlgoFourmis(num_fourmis,alpha,beta,taux_evaporation,intensification)
    solveurAlgoGenet=AlgoGenet()
    solveurEscalade=Escalade()

   
    #Résolution
    
    solution_Escalade_Voyageur,distance_Escalade_Voyageur=solveurEscalade.resoudre(problemeVoyageur,temps_Voyageur)
    solution_RecuitSimule_Voyageur,distance_RecuitSimule_Voyageur, =solveurRecuitSimule.resoudre(problemeVoyageur,temps_Voyageur)
    solution_AlgoGenetique_Voyageur,distance_AlgoGenetique_Voyageur, =solveurAlgoGenet.resoudre(problemeVoyageur,temps_Voyageur)
    solution_AlgoFourmis_Voyageur,distance_AlgoFourmis_Voyageur,=solveurAlgoFourmis.resoudre(problemeVoyageur,temps_Voyageur)
    solution_Escalade_SacAdos,distance_Escalade_SacAdos,=solveurEscalade.resoudre(problemeSacAdos,temps_SacAdos)
    solution_RecuitSimule_SacAdos,distance_RecuitSimule_SacAdos,=solveurRecuitSimule.resoudre(problemeSacAdos,temps_SacAdos)

    #on aura besoin d'afficher -(-valeur) du sac à dos car on a minimiser -valeur du sac à dos d'pù
    valeur_Escalade_SacAdos=(-1)*distance_Escalade_SacAdos
    valeur_recuitSimule_SacAdos=(-1)*distance_RecuitSimule_SacAdos

    #output_file = os.path.join(dossier, "testVoyageur.txt")
    with open(results_dir / "testVoyageur.txt", "w") as f:
        f.write(f"Résultats de l'instance du problème du voyageur ... avec un temps de {temps_Voyageur} secondes \n")
        
        f.write(f"Recuit Simulé, meilleure distance trouvée = {distance_RecuitSimule_Voyageur} km \n")
        f.write(f"Algorithme des Fourmis, meilleure distance trouvée = {distance_AlgoFourmis_Voyageur} km \n")
        f.write(f"Algorithme Génétique, meilleure distance trouvée = {distance_AlgoGenetique_Voyageur} km \n")
        f.write(f"Escalade, meilleure valeur trouvée = {distance_Escalade_Voyageur} km \n")

    #output_file = os.path.join(dossier, "testSacAdos.txt")
    with open(results_dir / "testSacAdos.txt", "w") as f:
        f.write(f"Résultats de l'instance du problème du sac à dos avec {nb_objets} objets possibles et un poids max de {poids_max} kg avec un temps de {temps_SacAdos} secondes \n")
        f.write(f"solution avec algorihtme glouton = {problemeSacAdos.obtenir_Sol_Glouton().valeur}  \n")
        f.write(f"Recuit Simulé, meilleure valeur trouvée = {valeur_recuitSimule_SacAdos}  \n")
        f.write(f"Escalade, meilleure valeur trouvée = {valeur_Escalade_SacAdos} \n")
    
   
    

    print("résolution instance",nomfich,"du problème du voyageur avec un temps de ",temps_Voyageur,"secondes :")
    print("Escalade, distance trouvée ;", distance_Escalade_Voyageur)
    print("Recuit simulé, distance trouvée ;", distance_RecuitSimule_Voyageur)
    print("Algorithme génétique, distance trouvée ;", distance_AlgoGenetique_Voyageur)
    print("Algorithme des fourmis, distance trouvée ;", distance_AlgoFourmis_Voyageur,"\n")

    print("résolution instance du problème du sac à dos avec",nb_objets," objets et un poids max de",poids_max," avec un temps de ",temps_SacAdos,"secondes :")
    print("solution avec algorihtme glouton = ",problemeSacAdos.obtenir_Sol_Glouton().valeur)
    print("Escalade, valeur trouvée ;", valeur_Escalade_SacAdos)
    print("Recuit simulé, valeur trouvée ;", valeur_recuitSimule_SacAdos)
    
    
    


if __name__ == "__main__":
    main()
