# code permettant de résoudre des instances du tsp ou du problème du sac à dos avec des méta-heuristiques

Le rapport et le main sont disponible dans le dossier principal. La programmation est une programmation orientée objet, les classes se trouvent dans le dossier \src. Des fichiers .tsp contenant des instances du problème TSP de la TSPLIB sont diponibles dans le dossier \data. Les fichiers de résultats sont enrégistrés dans le dossier \results.

## Problème du sac à dos
1. On peut choisir le nombre d'objets disponibles et le poids maximum du sac à dos et créer une instance aléatoire du problème du sac à dos. 
2. Ensuite on peut obtenir une solution approchée à ce problème grâce à un algorithme glouton ou grâce aux méta heuristiques RecuitSimulé ou Escalade.
3. exemple dans les fichiers du dossier \results

## Problème du voyageur de commerce (TSP)
 1. fichiers d'instances de tsp dans dossier \data, possibilité d'en mettre d'autres
 2. Possibilités de les charger et d'obtenir une solution approchée avec les méta heuristiques escalade, recuit simulé, algorithme génétique et algorithme des fourmis.
 3. résultats des instances de \data dans dossier \results

## choix du temps
1. étant donné qu'en fonction de l'instance d'un problème choisi il peut être difficile de se rapprocher de la solution optimale et que le temps que dure une itération varie lui aussi beaucoup en fonction du problème, le critère d'arrêt des algorithmes et le temps de recherche choisi par l'utilisateur. A adapter en fonction de la taille des instances des différents problèmes.
