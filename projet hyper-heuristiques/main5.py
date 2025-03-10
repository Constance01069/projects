import os;
import sys
from pathlib import Path

BASE_DIR = Path(__file__).resolve().parent
SRC_DIR = BASE_DIR / "src"
sys.path.append(str(SRC_DIR))
from src.FirstFit import FirstFit
from src.Graphe import Graphe
from src.sdo import sdo
from src.Dsatur import Dsatur
import copy
from src.WelshPowell import WelshPowell
from src.hyperHeuristique import hyperHeuristique
if __name__ == "__main__":

    
    BASE_DIR = Path(__file__).resolve().parent  
    DATA_DIR = BASE_DIR / "data"  

    #création de l'ensemble d'entraînement
    fichiers_graphes = [
        "1-FullIns_3.col",
        "games120.col",
        "le450_25a.col",
        "miles250.col",
        "miles1000.col",
        "mulsol.i.5.col",
        "queen9_9.col",
        "zeroin.i.2.col",
    ]

    
    graphes = [Graphe() for _ in fichiers_graphes]

    #chargement des noeuds de l'ensemble d'entraînement
    try:
        for i, nom_fichier in enumerate(fichiers_graphes):
            graphes[i].nom=nom_fichier
            chemin_fichier = DATA_DIR / nom_fichier  
            if not chemin_fichier.exists():
                raise FileNotFoundError(f"Le fichier {chemin_fichier} est introuvable !")
            
            #print(f"\nChargement depuis le fichier {nom_fichier}...")
            graphes[i].charger_graphe(chemin_fichier)  
            
    except FileNotFoundError as e:
        print(e)
    

    hhe=hyperHeuristique()

    #création de l'hyperheuristique
    hhe.rechercherSol(graphes)
    #print(hhe)

    methodes=[hhe,sdo(),FirstFit(),Dsatur(),WelshPowell()]

    #affichage du coloriage des graphes de l'ensemble d'entraînement à décommenter si souhaité
    """
    for i in range(len(graphes)):
        for methode in methodes :
            graphes[i].effacerCouleurs()
            print("coloriage du graphe",graphes[i].nom,"avec la methode", methode.nom)
            methode.colorier(graphes[i],graphes[i].obtenirNbNoeuds())
            print("nbNoeuds: ", graphes[i].obtenirNbNoeuds() )
            print(f"Liste des couleurs utilisées : {graphes[i].couleursUtilisees}")
            print(f"Nombre de couleurs utilisées pour le coloriage: {graphes[i].obtenirNbCouleurs()}")
            print()
    """ 

    #création de l'ensemble de graphes tests       
    fichiers_graphes_tests = [
        "r125.1.col",
        "queen11_11.col",
        "mulsol.i.2.col",
        "zeroin.i.3.col",
        "1-FullIns_5.col",
        "jean.col",
    ]

    graphes_tests = [Graphe() for _ in fichiers_graphes_tests]
    #chargement des graphes tests
    try:
        for i, nom_fichier in enumerate(fichiers_graphes_tests):
            graphes_tests[i].nom=nom_fichier
            chemin_fichier = DATA_DIR / nom_fichier  
            if not chemin_fichier.exists():
                raise FileNotFoundError(f"Le fichier {chemin_fichier} est introuvable !")
            
            #print(f"\nChargement depuis le fichier {nom_fichier}...")
            graphes_tests[i].charger_graphe(chemin_fichier)  
            
    except FileNotFoundError as e:
        print(e)

    #affichage du coloriage des graphes tests par chacunes des 5 méthodes implémentées
    for i in range(len(graphes_tests)):
        for methode in methodes :
            graphes_tests[i].effacerCouleurs()
            print("coloriage du graphe",graphes_tests[i].nom,"avec la methode", methode.nom)
            methode.colorier(graphes_tests[i],graphes_tests[i].obtenirNbNoeuds())
            print("nbNoeuds: ", graphes_tests[i].obtenirNbNoeuds() )
            print(f"Liste des couleurs utilisées : {graphes_tests[i].couleursUtilisees}")
            print(f"Nombre de couleurs utilisées pour le coloriage: {graphes_tests[i].obtenirNbCouleurs()}")

    #affichage graphique des graphes si souhaité :
            
    graphes_tests[5].effacerCouleurs()
    hhe.colorier(graphes_tests[5],graphes_tests[5].obtenirNbNoeuds())
    #graphes_tests[5].dessiner_graphe()

    graphes_tests[4].effacerCouleurs()
    hhe.colorier(graphes_tests[4],graphes_tests[4].obtenirNbNoeuds())
    graphes_tests[4].dessiner_graphe()

    #graphes_tests[3].effacerCouleurs()
    #hhe.colorier(graphes_tests[3],graphes_tests[3].obtenirNbNoeuds())
    #graphes_tests[3].dessiner_graphe()

    #graphes_tests[2].effacerCouleurs()
    #hhe.colorier(graphes_tests[2],graphes_tests[2].obtenirNbNoeuds())
    #graphes_tests[2].dessiner_graphe()

    graphes_tests[1].effacerCouleurs()
    hhe.colorier(graphes_tests[1],graphes_tests[1].obtenirNbNoeuds())
    graphes_tests[1].dessiner_graphe()

    #graphes_tests[0].effacerCouleurs()
    #hhe.colorier(graphes_tests[0],graphes_tests[0].obtenirNbNoeuds())
    #graphes_tests[0].dessiner_graphe()

    #print(graphes_tests[5].nom)
    #print(graphes_tests[4].nom)
    #print(graphes_tests[1].nom)
