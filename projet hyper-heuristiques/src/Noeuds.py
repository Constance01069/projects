
class Noeud :

    def __init__(self):

        self.numero=0;
        self.couleur=0;
        self.voisins=[];
        self.degre=0;
        self.couleurVoisines=[];
        self.nbCouleursVoisines=0;

    def colorier(self,couleur):
        self.couleur=couleur;

    def obtenirVoisins(self):
        return self.voisins;

    def obtenirNbVoisins(self):
        return len(self.voisins);

    def obtenirCouleur(self):
        return self.couleur;

    def calculDesAttributsDeductibles(self):
        
        self.degre = len(self.voisins)
        self.couleursVoisines = {voisin.couleur for voisin in self.voisins}
        self.nbCouleursVoisines = len(self.couleursVoisines)





