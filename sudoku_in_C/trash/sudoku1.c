#include <stdio.h>
#include <string.h>
#include <sys/time.h>
//#define COLONNES 9
//#define LIGNES 9
#define TAILLE_SUDOK 9
#define TAILLE 500
#define TAILLE_CHIFFRE 2

int grille[TAILLE_SUDOK][TAILLE_SUDOK];
int resoudreSudoku(int,int);
void afficheSudoku(void);
int dansLaLigne(int,int,int);
int memeCarre(int,int,int);
int memeLigne(int,int,int);
void remplissageGrille();

void remplissageGrille() { //remplit la grille. Lors de l'execution, on donne en entree une grille a resoudre
	int lignes, colonnes;
	for (lignes = 0; lignes < TAILLE_SUDOK; lignes++) {
		for (colonnes = 0; colonnes < TAILLE_SUDOK; colonnes++) {
			scanf("%d", &grille[lignes][colonnes]); //0 representera le vide
		}
	}
}

int dansLaLigne(int i, int j, int chiffre) { //verifie si un chiffre est deja present dans la ligne i /!\ voir avec Constance
	int colonnes, test = 0;

	for (colonnes = 0; colonnes < TAILLE_SUDOK; colonnes++) {
		if (grille[i][colonnes] == chiffre) {
			return 1;
		}
	}
	return 0;
}

int memeLigne(int i, int j, int chiffre){ //verifie si un chiffre est deja present dans la colonne j /!\ voir avec Constance
	int lignes, test;
	
	for (lignes = 0; lignes < TAILLE_SUDOK; lignes++) {
		if (grille[lignes][j] == chiffre) {
			return 1;
		}
	}
	return 0;
}

int memeCarre(int i, int j, int chiffre){
	int lignes, colonnes;

	if (i < 3) {
		i = 0;
	}
	else if (i < 6) {
		i = 3;
	}
	else {
		i = 6;
	}
	
	if (j < 3) {
		j = 0;
	}
	else if (j < 6) {
		j = 3;
	}
	else {
		j = 6;
	}

	for (lignes = i; lignes < i + 3; lignes++) {
		for (colonnes = j; colonnes < j + 3; colonnes++) {
			if (grille[lignes][colonnes] == chiffre) {
				return 1;
			}
		}
	}

	return 0;
}

void afficheSudoku() {
	int lignes, colonnes;

	for (lignes = 0; lignes < TAILLE_SUDOK; lignes++) {
		for (colonnes = 0; colonnes < TAILLE_SUDOK; colonnes++) {
			printf("%2d ", grille[lignes][colonnes]);
		}
		printf("\n");
	}
 	printf("\n");
}

int resoudreSudoku(int i, int j) {
	int chiffre = 1, tx = 0, ty = 0, test;

	if (grille[i][j] != 0) {
		if (i == 8 && j == 8) {
			return 1;
		}
		if (i < 8) {
			i++;
		}
		else {	
			if (j < 8) {
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

	if (grille[i][j] == 0) {
		while (chiffre <= TAILLE_SUDOK) {
			if (!memeCarre(i,j,chiffre) && !memeLigne(i,j,chiffre) && !dansLaLigne(i,j,chiffre)) {
				grille[i][j] = chiffre;
				if (i == 8 && j == 8) {
					return 1;
				}
				if (i < 8) {
					tx = i +   1;
				}
				else {
					tx = 0;
					ty = j + 1;
				}
				if (resoudreSudoku(tx,ty)) {
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

	afficheSudoku();
	resoudreSudoku(i,j);
	afficheSudoku();

	/*calcul du temps d'execution*/
	gettimeofday(&end, 0);
	long seconds = end.tv_sec - begin.tv_sec;
    long microseconds = end.tv_usec - begin.tv_usec;
    double elapsed = seconds + microseconds*1e-6;
	printf("Time measured: %.5f seconds.\n", elapsed);
	
	return 0;
}   