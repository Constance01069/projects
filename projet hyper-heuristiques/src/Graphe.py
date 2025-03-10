from Noeuds import Noeud
import os
import sys
from pathlib import Path
import networkx as nx
import matplotlib.pyplot as plt
import numpy as np
class Graphe :

    def __init__(self):

        self.noeuds=[]
        self.couleursUtilisees=[]
        self.nom="inconnu"
        
    def ajouterNoeuds(self,Noeuds):
        self.noeuds.append(Noeuds);

    def obtenirNbNoeuds(self):
         return len(self.noeuds);

    def obtenirNbCouleurs(self):

        
            
    
        return len(self.couleursUtilisees);

    @staticmethod
    def trouver_fichier(nom_fichier):
        

        chemin_fichier = Path(__file__).resolve().parent / "data" / nom_fichier
        if chemin_fichier.exists():
            return chemin_fichier
        else:
            raise FileNotFoundError(f"Le fichier {nom_fichier} est introuvable dans {chemin_fichier.parent}.")

    def charger_graphe(self, fichier):
        

        
        fichier = Path(fichier)  
        noeuds_dict = {}

        with fichier.open("r") as f:
            for ligne in f:
                ligne = ligne.strip()

                
                if ligne.startswith("c") or not ligne:
                    continue

                
                if ligne.startswith("p"):
                    _, _, nb_noeuds, nb_aretes = ligne.split()
                    #print(f"Chargement d'un graphe avec {nb_noeuds} nœuds et {nb_aretes} arêtes")
                    continue
                elif ligne.startswith("e"):
                    
                    _, source, cible = ligne.split()
                else:
                    
                    try:
                        source, cible = map(int, ligne.split())
                    except ValueError:
                        raise ValueError(f"Ligne mal formatée : {ligne}")

                source, cible = int(source), int(cible)

                # Ajouter le nœud source s'il n'existe pas
                if source not in noeuds_dict:
                    noeud_source = Noeud()
                    noeud_source.numero = source
                    noeuds_dict[source] = noeud_source
                    self.noeuds.append(noeud_source)

                # Ajouter le nœud cible s'il n'existe pas
                if cible not in noeuds_dict:
                    noeud_cible = Noeud()
                    noeud_cible.numero = cible
                    noeuds_dict[cible] = noeud_cible
                    self.noeuds.append(noeud_cible)

                # Ajouter la relation de voisinage
                noeuds_dict[source].voisins.append(noeuds_dict[cible])
                noeuds_dict[cible].voisins.append(noeuds_dict[source])
                


    def afficher_graphe(self):
        print("\n--- Graphe ---")
        print(f"Nombre de nœuds: {len(self.noeuds)}")
        for noeud in self.noeuds:
            voisins = [voisin.numero for voisin in noeud.voisins]
            print(f"Nœud {noeud.numero} - Couleur: {noeud.couleur}, Voisins: {voisins}")

    def calculAttributNoeuds(self):

        for noeud in self.noeuds:
            noeud.calculDesAttributsDeductibles()

    def effacerCouleurs(self):
        for noeud in self.noeuds:
            noeud.couleur=0
            self.couleursUtilisees=[]

    #obtenir la liste d'arrêtes du graphe, utile seulement pour le dessin graphique du graphe
    def obtenirArretes(self):
        arretes=[]
        for noeud in self.noeuds :
            for noeud2 in noeud.voisins :
                if (noeud,noeud2) not in arretes:
                    if (noeud2,noeud) not in arretes:
                        arretes.append((noeud,noeud2))
        return arretes

    

    def dessiner_graphe(self):
        
        G = nx.Graph()

    # Ajouter les sommets et les arêtes
        G.add_nodes_from(self.noeuds)
        G.add_edges_from(self.obtenirArretes())

    # Générer une palette de couleurs pour chaque valeur unique de noeud.couleur
        couleurs_uniques = list(set(noeud.couleur for noeud in self.noeuds))
        nombre_couleurs = len(couleurs_uniques)  # Nombre de couleurs distinctes
        hues = np.linspace(0, 1, nombre_couleurs)  # Génère des teintes de l'arc-en-ciel

    # Conversion des teintes en couleurs RGB
        couleurs = [plt.cm.hsv(hue) for hue in hues]

    # Créer un dictionnaire des couleurs associées aux noeuds
        color_dict = {couleur: couleurs[i] for i, couleur in enumerate(couleurs_uniques)}

    # Associer la couleur correcte à chaque nœud
        couleur_noeuds = [color_dict[noeud.couleur] for noeud in self.noeuds]

    # Dessiner le graphe avec NetworkX et Matplotlib
        plt.figure(figsize=(8, 6))
        nx.draw(
            G,
            with_labels=False,  # Ne pas afficher les labels des nœuds
            node_color=couleur_noeuds,
            edge_color="black",  # Couleur des arêtes
            node_size=500,       # Taille des nœuds
        )
        plt.title(f"Graphe : {self.nom}")
        plt.show()


