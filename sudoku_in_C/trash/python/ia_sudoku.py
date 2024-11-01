#!/usr/bin/env python3

from grille_sudoku import coordonnee, obtenir_colonne, obtenir_ligne, obtenir_carre, \
    grille_vide, listes_chiffres_en_lignes_en_grille, \
    case_vide, obtenir_chiffre, fixer_chiffre, vider_case, grille_en_chaine

def grille_remplie(grille: "GrilleSudoku") -> bool:
    for colonne in range(1, 10):
        for ligne in range(1, 10):
            if case_vide(grille, coordonnee(colonne, ligne)):
                return False
    return True

def obtenir_chiffres_d_une_ligne(grille: "GrilleSudoku", ligne: int) -> set:
    return {obtenir_chiffre(grille, coordonnee(colonne, ligne)) for colonne in range(1, 10)}

def obtenir_chiffres_d_une_colonne(grille: "GrilleSudoku", colonne: int) -> set:
    return {obtenir_chiffre(grille, coordonnee(colonne, ligne)) for ligne in range(1, 10)}

def pasouf(carre) -> int:
    if carre in (1,2,3):
        return 1
    if carre in (4,5,6):
        return 4
    if carre in (7,8,9):
        return 7

def obtenir_chiffres_d_un_carre(grille: "GrilleSudoku", carre: int) -> set:
    return {obtenir_chiffre(grille, coordonnee(colonne, ligne)) for ligne in range(pasouf(carre), pasouf(carre) + 3) for colonne in range(((carre - 1) % 3) * 3 + 1, ((carre - 1) % 3) * 3 + 4)}

def est_chiffre_valable(grille: "GrilleSudoku",\
                        coordonnee : "Coordonnee",\
                        chiffre: int) -> bool:
    if not(chiffre in obtenir_chiffres_d_une_colonne(grille, obtenir_colonne(coordonnee))) and \
    not(chiffre in obtenir_chiffres_d_une_ligne(grille, obtenir_ligne(coordonnee))) and \
    not(chiffre in obtenir_chiffres_d_un_carre(grille, obtenir_carre(coordonnee))):
        return True
    return False

def obtenir_solutions_possibles(grille: "GrilleSudoku",
                                coordonnee : "Coordonnee") -> set:
    res = set()
    for chiffre in range(1,10):
        if est_chiffre_valable(grille, coordonnee, chiffre):
            res.add(chiffre)
    return res

def resoudre_sudoku(grille: "GrilleSudoku") -> bool:
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
        return False

if __name__ == "__main__":
    print("Tests unitaires :")
    g = grille_vide()
    fixer_chiffre(g, coordonnee(5, 1), 3)
    fixer_chiffre(g, coordonnee(6, 1), 5)
    fixer_chiffre(g, coordonnee(2, 2), 2)
    fixer_chiffre(g, coordonnee(3, 2), 3)
    fixer_chiffre(g, coordonnee(3, 3), 9)
    fixer_chiffre(g, coordonnee(8, 3), 6)
    fixer_chiffre(g, coordonnee(9, 3), 7)
    fixer_chiffre(g, coordonnee(2, 5), 9)
    fixer_chiffre(g, coordonnee(4, 5), 7)
    fixer_chiffre(g, coordonnee(7, 5), 1)
    fixer_chiffre(g, coordonnee(1, 6), 6)
    fixer_chiffre(g, coordonnee(2, 6), 2)
    fixer_chiffre(g, coordonnee(6, 6), 3)
    fixer_chiffre(g, coordonnee(8, 6), 9)
    fixer_chiffre(g, coordonnee(2, 7), 3)
    fixer_chiffre(g, coordonnee(3, 7), 8)
    fixer_chiffre(g, coordonnee(4, 7), 9)
    fixer_chiffre(g, coordonnee(2, 8), 5)
    fixer_chiffre(g, coordonnee(5, 8), 4)
    fixer_chiffre(g, coordonnee(7, 8), 2)
    fixer_chiffre(g, coordonnee(9, 8), 8)
    fixer_chiffre(g, coordonnee(3, 9), 4)
    fixer_chiffre(g, coordonnee(5, 9), 2)
    fixer_chiffre(g, coordonnee(7, 9), 6)

    res = "OK" if not grille_remplie(g) else "KO"
    print(f"  grille_remplie {res}")
    chiffre = obtenir_chiffre(g,coordonnee(5,1))
    res = "OK" if chiffre == 3 else "KO"
    print(f"  obtenir_chiffre ligne 1 {res}")
    chiffre = obtenir_chiffre(g,coordonnee(7,9))
    res = "OK" if chiffre == 6 else "KO"
    print(f"  obtenir_chiffre ligne 9 {res}")
    chiffre = obtenir_chiffre(g,coordonnee(1,6))
    res = "OK" if chiffre == 6 else "KO"
    print(f"  obtenir_chiffre colonne 1 {res}")
    chiffre = obtenir_chiffre(g,coordonnee(3,9))
    res = "OK" if chiffre == 4 else "KO"
    print(f"  obtenir_chiffre colonne 9 {res}")
    chiffres = obtenir_chiffres_d_une_ligne(g,6)
    res = "OK" if chiffres == {6,2,3,9,None} else "KO"
    print(f"  obtenir_chiffres_d_une_ligne {res}")
    chiffres = obtenir_chiffres_d_une_colonne(g,3)
    res = "OK" if chiffres == {3,9,8,4,None} else "KO"
    print(f"  obtenir_chiffres_d_une_colonne {res}")
    chiffres = obtenir_chiffres_d_un_carre(g,4)
    res = "OK" if chiffres == {9,6,2,None} else "KO"
    print(f"  obtenir_chiffres_d_un_carre {res}")
    res = "OK" if est_chiffre_valable(g, coordonnee(4,1), 1) else "KO"
    print(f"  est_chiffre_valable valable {res}")
    res = "OK" if not est_chiffre_valable(g, coordonnee(4,1), 9) else "KO"
    print(f"  est_chiffre_valable non valable {res}")
    res = "OK" if obtenir_solutions_possibles(g, coordonnee(4,1)) == {1,2,4,6,8} else "KO"
    print(f"  obtenir_solutions_possibles {res}")

    print("Resolution")
    print(" Grille à résoudre")
    from exemples_grilles import SIMPLE
    g = listes_chiffres_en_lignes_en_grille(SIMPLE)
    print(grille_en_chaine(g))
    if resoudre_sudoku(g):
        print(" Grille résolue")
        print(grille_en_chaine(g))
