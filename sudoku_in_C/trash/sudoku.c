#include <stdio.h>
//#include <math.h>
#include <string.h>
//#define COLONNES 9
//#define LIGNES 9
#define TAILLE_SUDOK 9
#define TAILLE 500
#define TAILLE_CHIFFRE 2
#define TAILLE_COORD 2 // 0: ligne, 1: colonne

/*int coordonnee(colonne, ligne) {
    int coordonnee[] = {colonne,ligne};
    return coordonnee;
}

int obtenir_colonne(coordonnee) {
    int colonne = coordonnee[0];
    return colonne;
}

int obtenir_ligne(coordonnee) {
    int ligne = coordonnee[1];
    return ligne;
}*/

int obtenir_carre(int ligne, int colonne) {
    int carre = 3 * (ligne / 3) + (colonne / 3);
    return carre;
}

int case_vide(int grille[TAILLE_SUDOK][TAILLE_SUDOK], int ligne, int colonne) {
    int bool = 0;
    if (grille[ligne][colonne] == 0) bool = 1;
    return bool;
}

int grille_remplie(int grille[TAILLE_SUDOK][TAILLE_SUDOK]) {
    int bool = 1, lignes, colonnes;
    for (lignes = 0; lignes < TAILLE_SUDOK; lignes++) {
        for (colonnes = 0; colonnes < TAILLE_SUDOK; colonnes++) {
            if (case_vide(grille, lignes, colonnes)) bool = 0;
        }
    }
    return bool;
}

void obtenir_chiffres_d_une_ligne(int chiffres_ligne[TAILLE_SUDOK], int grille[TAILLE_SUDOK][TAILLE_SUDOK], int ligne) {
    int colonnes;
    for (colonnes = 0; colonnes < TAILLE_SUDOK; colonnes++) {
        chiffres_ligne[colonnes] = grille[ligne][colonnes];
    }
}

void obtenir_chiffres_d_une_colonne(int chiffres_colonne[TAILLE_SUDOK], int grille[TAILLE_SUDOK][TAILLE_SUDOK], int colonne) {
    int lignes;
    for (lignes = 0; lignes < TAILLE_SUDOK; lignes++) {
        chiffres_colonne[lignes] = grille[lignes][colonne];
    }
}

void obtenir_chiffres_d_un_carre(int chiffres_carre[TAILLE_SUDOK], int grille[TAILLE_SUDOK][TAILLE_SUDOK], int carre) {
    int lignes, colonnes, carres, borne_col, borne_lig;
    borne_lig = ((carre) * 3) % 3;
    borne_col = ((carre) % 3) * 3;
    carres = 0;
    for (lignes = borne_lig; lignes < borne_lig + 3; lignes++) {
        for (colonnes = borne_col; colonnes < borne_col + 3; colonnes++) {
            chiffres_carre[carres] = grille[lignes][colonnes];
            carres++;
        }
    }
}

int est_chiffre_valable(int grille[TAILLE_SUDOK][TAILLE_SUDOK], int chiffres_ligne[TAILLE_SUDOK], int chiffres_colonne[TAILLE_SUDOK], int chiffres_carre[TAILLE_SUDOK], int coord[TAILLE_COORD], int chiffre) {
    int i;
    int bool = 1;
    obtenir_chiffres_d_une_ligne(chiffres_ligne, grille, coord[0]);
    obtenir_chiffres_d_une_colonne(chiffres_colonne, grille, coord[1]);
    obtenir_chiffres_d_un_carre(chiffres_carre, grille, obtenir_carre(coord[0],coord[1]));
    i = 0;
    while ((bool != 0) && (i < TAILLE_SUDOK)) {
        if ((chiffre == chiffres_ligne[i]) || (chiffre == chiffres_colonne[i]) || (chiffre == chiffres_carre[i])) {
            bool = 0;
        }
        i++;
    }
    return bool;
}

int obtenir_solutions_possibles(int grille[TAILLE_SUDOK][TAILLE_SUDOK], int chiffres_ligne[TAILLE_SUDOK], int chiffres_colonne[TAILLE_SUDOK], int chiffres_carre[TAILLE_SUDOK], int coord[TAILLE_COORD], int solutions_possibles[TAILLE_SUDOK], int i) { //il n'y a que au max 9 solutions possibles
    int chiffre;
    i = 0;
    for (chiffre = 1; chiffre <= 9; chiffre++) {
        if (est_chiffre_valable(grille, chiffres_ligne, chiffres_colonne, chiffres_carre, coord, chiffre)) {
            solutions_possibles[i] = chiffre;
            i++;
        }
    }
    return i;
}

void coordonnee_premiere_case_vide(int grille[TAILLE_SUDOK][TAILLE_SUDOK], int coord[TAILLE_COORD]) {
    int lignes, colonnes;
    /*for (lignes = 0; lignes < TAILLE_SUDOK; lignes++) {
        for (colonnes = 0; colonnes < TAILLE_SUDOK; colonnes++) {
            if (case_vide(grille, lignes, colonnes)) {
                ligne = lignes;
                colonne = colonnes;
                coord[0] = lignes;
                coord[1] = colonnes;
            }
        }
    }*/
    lignes = 0;
    colonnes = 0;
    while (!case_vide(grille, lignes, colonnes)) {
        lignes++;
        while (!case_vide(grille, lignes, colonnes)) {
            colonnes++;
        }
    }
    coord[0] = lignes;
    coord[1] = colonnes;
}

int est_dedans(int solutions_possibles[TAILLE_SUDOK], int element, int nbElements) {
    int bool = 0;
    int i = 0;
    while ((i < nbElements) && (bool != 1)) {
        //printf(" i%d",i);
        //printf(" e%d",element);
        if (element == solutions_possibles[i]) {
            bool = 1;
            //printf("vrai");
        }
        i++;
    }
    return bool;
}

int resoudre_sudoku(int grille[TAILLE_SUDOK][TAILLE_SUDOK], int chiffres_ligne[TAILLE_SUDOK], int chiffres_colonne[TAILLE_SUDOK], int chiffres_carre[TAILLE_SUDOK]) {
    int bool = 0, ligne = 0, colonne = 0, est_une_solution, chiffre, max = 0, i;
    int solutions_possibles[TAILLE_SUDOK], coord[TAILLE_COORD];

    if (grille_remplie(grille)) {
        bool = 1;
    }
    else {
        coordonnee_premiere_case_vide(grille, coord);
        max = obtenir_solutions_possibles(grille, chiffres_ligne, chiffres_colonne, chiffres_carre, coord, solutions_possibles, max);
        //printf("%d\n",max);
        chiffre = 1;
        while ((chiffre <= TAILLE_SUDOK)) {
            if (est_dedans(solutions_possibles, chiffre, max)) {
                //printf(" c%d \n", chiffre);
                grille[coord[0]][coord[1]] = chiffre;
                est_une_solution = resoudre_sudoku(grille, chiffres_ligne, chiffres_colonne, chiffres_carre);
                printf("%d",est_une_solution);
                if (est_une_solution) {
                    bool = est_une_solution;
                }
                else {
                    grille[coord[0]][coord[1]] = 0;
                }
                bool = 0;   
            }
            chiffre++;
        }
    }
    return bool;
}

/*def resoudre_sudoku(grille: "GrilleSudoku") -> bool:
    def coordonnee_premiere_case_vide(grille) -> "Coordonnee":
        for ligne in range(1,10):
            for colonne in range(1,10):
                c = coordonnee(colonne, ligne)
                if case_vide(grille, c):
                    return c

    if grille_remplie(grille):
        return True
    else:
        coordonnee_case_vide = coordonnee_premiere_case_vide(grille)
        for chiffre in obtenir_solutions_possibles(grille, coordonnee_case_vide):
            fixer_chiffre(grille, coordonnee_case_vide, chiffre)
            est_une_solution = resoudre_sudoku(grille)
            if est_une_solution:
                return est_une_solution
            else:
                vider_case(grille, coordonnee_case_vide)
        return False*/

int main () {
    int lignes, colonnes, carres, bool;
    //int colonne = 1, ligne = 2, coordonnee[] = {ligne,colonne};
    int grille[TAILLE_SUDOK][TAILLE_SUDOK];
    int coord[TAILLE_COORD], chiffres_ligne[TAILLE_SUDOK], chiffres_colonne[TAILLE_SUDOK], chiffres_carre[TAILLE_SUDOK], solutions_possibles[TAILLE_SUDOK];
    //char grille_chaine[TAILLE];
    //char chiffre[TAILLE_CHIFFRE];

    /*remplit la grille de vide*/
    for (lignes = 0; lignes < TAILLE_SUDOK; lignes++) {
        for (colonnes = 0; colonnes < TAILLE_SUDOK; colonnes++) {
            scanf("%d", &grille[lignes][colonnes]); //0 représentera le vide
        }
    }

    //coordonnee[0] = 8;
    //coordonnee[1] = 8;

    //grille[5][8] = 0;

    //bool = case_vide(grille, coordonnee);
    //printf("%d\n", bool);

    /*bool = grille_remplie(grille);
    printf("%2d\n", bool);

    obtenir_chiffres_d_une_ligne(chiffres_ligne, grille, 8);
    for (colonnes = 0; colonnes < TAILLE_SUDOK; colonnes++) printf("%2d ", chiffres_ligne[colonnes]);
    printf("\n");

    obtenir_chiffres_d_une_colonne(chiffres_colonne, grille, 1);
    for (lignes = 0; lignes < TAILLE_SUDOK; lignes++) printf("%2d ", chiffres_colonne[lignes]);
    printf("\n");

    obtenir_chiffres_d_un_carre(chiffres_carre, grille, 1);
    for (carres = 0; carres < TAILLE_SUDOK; carres++) printf("%2d ", chiffres_carre[carres]);
    printf("\n");

    bool = obtenir_carre(0,6);
    printf("%2d\n", bool);

    bool = est_chiffre_valable(grille, chiffres_ligne, chiffres_colonne, chiffres_carre, 0, 1, 1);
    printf("%2d\n", bool);*/

    printf("\n");
    for (lignes = 0; lignes < TAILLE_SUDOK; lignes++) {
        for (colonnes = 0; colonnes < TAILLE_SUDOK; colonnes++) {
            printf("%2d ", grille[lignes][colonnes]);
        }
        printf("\n");
    }

    if (resoudre_sudoku(grille, chiffres_ligne, chiffres_colonne, chiffres_carre)) {
        printf("Grille résolue");
        printf("\n");
        for (lignes = 0; lignes < TAILLE_SUDOK; lignes++) {
            for (colonnes = 0; colonnes < TAILLE_SUDOK; colonnes++) {
                printf("%2d ", grille[lignes][colonnes]);
            }
            printf("\n");
        }
    }
}

/*grille_chaine[0] = ' ';

    for (lignes = 0; lignes < 13; lignes++) {
        if (lignes % 4 == 0) {
            strcat(grille_chaine, "+-------+-------+-------+\n");
        }
        else {
            for (colonnes = 0; colonnes < 13; colonnes++) {
                if (colonnes % 4 == 0) {
                    strcat(grille_chaine, "| ");
                }
                else {
                    if (grille[lignes % 9][colonnes % 9] == 0) strcat(grille_chaine, "  ");
                    else {
                        sprintf(chiffre, "%d", grille[lignes % 9][colonnes % 9]);
                        strcat(grille_chaine, chiffre);
                        strcat(grille_chaine, " ");
                    }
                }
            }
            strcat(grille_chaine, "\n");
        }
    }

    printf("%s\n", grille_chaine);*/