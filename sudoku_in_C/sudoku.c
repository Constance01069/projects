#include <stdio.h>
#include <sys/time.h>
#define TAILLE_SUDOK 9
#define MAX_LIG_COL 8 //=TAILLE_SUDOK - 1, car les lignes et colonnes sont numerotees de 0 a 8

int grille[TAILLE_SUDOK][TAILLE_SUDOK];
int resoudreSudoku(int,int);
void afficheSudoku(void);
int dansLaLigne(int,int);
int dansLaColonne(int,int);
int dansLeCarre(int,int,int);
int quelleBorne(int);
void remplissageGrille();
void resoudreUnePossibilite();

void remplissageGrille() { //remplit la grille. Lors de l'execution, on donne en entree une grille a resoudre
	int lignes, colonnes;
	for (lignes = 0; lignes < TAILLE_SUDOK; lignes++) {
		for (colonnes = 0; colonnes < TAILLE_SUDOK; colonnes++) {
			scanf("%d", &grille[lignes][colonnes]); //0 representera le vide
		}
	}
}

int dansLaLigne(int ligne, int chiffre) { //verifie si un chiffre est deja present dans la ligne i
	int colonnes;

	for (colonnes = 0; colonnes < TAILLE_SUDOK; colonnes++) {
		if (grille[ligne][colonnes] == chiffre) {
			return 1;
		}
	}
	return 0;
}

int dansLaColonne(int colonne, int chiffre){ //verifie si un chiffre est deja present dans la colonne j
	int lignes;
	
	for (lignes = 0; lignes < TAILLE_SUDOK; lignes++) {
		if (grille[lignes][colonne] == chiffre) {
			return 1;
		}
	}
	return 0;
}

int quelleBorne(int indice) { //donne la borne inferieure pour une recherche dans un carre
    if (indice < 3) {
		indice = 0;
	}
	else if (indice < 6) {
		indice = 3;
	}
	else {
		indice = 6;
	}

    return indice;
}

int dansLeCarre(int i, int j, int chiffre) { //verifie si un chiffre est deja present dans le carre associe a la ligne i et la colonne j
	int lignes, colonnes, borne_ligne, borne_colonne;

	borne_ligne = quelleBorne(i);
    borne_colonne = quelleBorne(j);

	for (lignes = borne_ligne; lignes < borne_ligne + 3; lignes++) {
		for (colonnes = borne_colonne; colonnes < borne_colonne + 3; colonnes++) {
			if (grille[lignes][colonnes] == chiffre) {
				return 1;
			}
		}
	}

	return 0;
}

void afficheSudoku() { //affiche le sudoku elegamment
	int lignes, colonnes;

	for (lignes = 0; lignes < TAILLE_SUDOK; lignes++) {
		if (lignes % 3 == 0) {
            printf("+-------+-------+-------+\n");
        }
        for (colonnes = 0; colonnes < TAILLE_SUDOK; colonnes++) {
            if (colonnes % 3 == 0) {
                printf("| ");
            }
            if (grille[lignes][colonnes] == 0) {
                printf("  ");
            }
            else {
                printf("%d ", grille[lignes][colonnes]);
            }
        }
        printf("|\n");
    }
    printf("+-------+-------+-------+\n\n");
}


void resoudreUnePossibilite() {
int i, j, compteur1, compteur2, chiffreTest, tmp;

    compteur2 = 0;
	for (i = 0; i < TAILLE_SUDOK; i++) {
		for (j = 0; j < TAILLE_SUDOK; j++) {
			if (grille[i][j] == 0) {
				compteur1 = 0;
				for (chiffreTest = 1; chiffreTest <= TAILLE_SUDOK; chiffreTest++) {
					if (!dansLeCarre(i,j,chiffreTest) && !dansLaLigne(i,chiffreTest) && !dansLaColonne(j,chiffreTest)) {
						tmp = chiffreTest;
						compteur1++;
					}
				}
				if (compteur1 == 1) {
					grille[i][j] = tmp;
					compteur2 = 1;
				}
			}
		}
	}
	if (compteur2 == 1) {
		resoudreUnePossibilite();
	}
}
			

int resoudreSudoku(int i, int j) {
	int chiffre = 1, i_apres = 0, j_apres = 0;

	if (grille[i][j] != 0) {
		if (i == MAX_LIG_COL && j == MAX_LIG_COL) { //verifie si on est positionne dans la derniere case de la grille
			return 1;
		}
		if (i < MAX_LIG_COL) {
			i++;
		}
		else {	
			if (j < MAX_LIG_COL) {
				i = 0;
				j++;
			}
		}
		if (resoudreSudoku(i,j)) {
			return 1;
		}
		else {
			return 0;
		}
	}		

	if (grille[i][j] == 0) { //ici on va fixer un chiffre et continuer l'exploration, si jamais il n'est pas bon on effectue un backtrack
		while (chiffre <= 9) { //les chiffres dans un sudoku sont compris entre 1 et 9
			if (!dansLeCarre(i,j,chiffre) && !dansLaLigne(i,chiffre) && !dansLaColonne(j,chiffre)) {
				grille[i][j] = chiffre;
				if (i == MAX_LIG_COL && j == MAX_LIG_COL) {
					return 1;
				}
				if (i < MAX_LIG_COL) {
					i_apres = i + 1;
				}
				else {
					i_apres = 0;
					j_apres = j + 1;
				}
				if (resoudreSudoku(i_apres,j_apres)) {
					return 1;
				}
			}
			chiffre++;
		}
		grille[i][j] = 0;
		return 0;
	}
}



int main() {
	int i = 0, j = 0;
	
	/*initialisation du timer*/
	struct timeval begin, end;
	gettimeofday(&begin, 0);

	remplissageGrille();

    printf("Grille à résoudre :\n");
	afficheSudoku();
	resoudreUnePossibilite();
	//afficheSudoku();
	if (resoudreSudoku(i,j)) {
        printf("Grille résolue :\n");
	    afficheSudoku();
    }

	/*calcul du temps d'execution*/
	gettimeofday(&end, 0);
	long seconds = end.tv_sec - begin.tv_sec;
    long microseconds = end.tv_usec - begin.tv_usec;
    double elapsed = seconds + microseconds*1e-6;
	printf("Temps d'exécution : %.5f secondes\n", elapsed);
    
    return 0;
}   
