library(psych)
library(copula)





# creation d'une matrice avec les valeurs de référence pour chaque paramètres, choix du nb de lignes
creerMatriceRef <- function(nbLignes) {
  mat <- matrix(0,nrow=nbLignes,ncol=5) 
  colnames(mat) <- c("Survie", "Derive", "Standardisation", "Mortalite", "Fecondite")
  mat[,"Survie"] <- 0.5
  mat[,"Derive"] <- 0
  mat[,"Standardisation"] <- 1
  mat[,"Mortalite"] <- 1
  mat[,"Fecondite"] <- 1
  return(mat)
}

##test <- creerMatriceRef(5)


# Pour creer une matrice avec un parametre qui varie sur un intervalle dont on choisit les bornes 
# selon une loi uniforme, tous les autres sont mis à leur valeur de référence, on choisit aussi la taille de l'échantillon

creerMatrice1param <- function(nomParam,bornInf,bornSup,nbLignes) {
  mat <- creerMatriceRef(nbLignes) 
  mat[,nomParam] <- runif(nbLignes,min=bornInf,max=bornSup)
  return(mat)
}

##test1 <- creerMatrice1param("Survie",0.2,0.4,6)

# creer une matrice ou tous les paramètres varient sur un intervalle selon une loi uniforme, prend en paramètres les valeurs de la corrélation entre les paramètres

creerMatriceDep <- function(nbLignes,surDer,surStan,surMor,surFec,derStan,derMor,derFec,staMor,staFec,morFec) {
  myCop <- normalCopula(param=c(surDer,surStan,surMor,surFec,derStan,derMor,derFec,staMor,staFec,morFec), dim = 5, dispstr = "un")
  
  #possibilite de changer les bornes des intervalles
  myMvd <- mvdc(copula=myCop, margins=c("unif", "unif", "unif","unif","unif"),
                paramMargins=list(list(min=0.2, max=0.7),
                                  list(min=-0.1, max=0.2), 
                                  list(min=0.8,max=1.20),
                                  list(min=0.8,max=1.20),
                                  list(min=0.8,max=1.20)))
  mat <- rMvdc(nbLignes,myMvd)
  colnames(mat) <- c("Survie", "Derive", "Standardisation", "Mortalite", "Fecondite")
  return(mat)
}

##test2 <- creerMatriceDep(1000,0.2,0,0.3,0,0,0,0,0,0,0)
##visualisationDepDonnees(test2)

# fonction pour visualiser la répartition des données obtenues, cette fonction génère un ensemble de graphiques, chaque graphique représentant
# la relation entre deux variables, sur la diagonale principale elle affiche les histogrammes des variables individuelles et en dehors de la diagonale les nuages de points pour chaque paire de variables

visualisationDonnees <- function(matrice) {
  psych :: pairs.panels(matrice)
}

#export des donnees sous forme csv

creerCsv <- function(matrice,nomCsv,chemin) {
  write.table(matrice, file = paste0(chemin, nomCsv, ".csv"), sep = ";", row.names = FALSE)
}



##creerCsv("test2","C:/Users/cbau/Documents/tests/",test2)

