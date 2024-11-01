library(psych)

source("./R_utiles/fct_csvEntreesISIS.R")

set.seed(100)
# choix du chemin où ranger le csv créé

chemin <- './Rdata/'

# choix du nom du csv

#nomCsv <- "paramIndep"
#nomCsv <- "paramMortalite"
#nomCsv <- "paramDep0406"
#nomCsv <- "paramFecondite"



# choix de la taille de l'échantillon de données

nbLignes <- 50


# choisir 1 si l'on souhaite faire varier un seul paramètre et 5 si l'on souhaite tous les faire varier

nbParam <- 1

# si nbParm=1 alors rentrer le nom du parametre qui varie et l'intervalle sur lequel il varie (rappelons que tous les parametres suivent une loi uniforme)

nomParam <- "Fecondite"
bornInf <- 0.8
bornSup <- 1.2

# si nbParm=5 écrire si oui on non on met une corrélation entre les paramètres (0 pour non et 1 pour oui) et vérifier

dep <- "non"

# si l'on souhaite mettre une dépendance rentrer les coeff de corrélation entre chaque paramètre (coeff compris entre -1 et 1), si l'on ne souhaite pas en mettre verifier que tous les coefficients sont à 0

depSurvieDerive <- -0.6
depSurvieStandardisation <- 0
depSurvieMortalite <- -0.8
depSurvieFecondite <- 0
depDeriveStandardisation <- 0
depDeriveMortalite <- 0
depDeriveFecondite <- 0
depStandardisationMortalite <- 0
depStandardisationFecondite <- 0
depMortaliteFecondite <- 0

#choix affichage verification (oui ou non)

verification <- "oui"

# Debut

if (nbParam == 1) {
  mat <- creerMatrice1param(nomParam,bornInf,bornSup,nbLignes)
} else if (dep == "non"){
  mat <- creerMatriceDep(nbLignes,0,0,0,0,0,0,0,0,0,0)
} else {
  mat <- creerMatriceDep(nbLignes,depSurvieDerive,depSurvieStandardisation,depSurvieMortalite,depSurvieFecondite,depDeriveStandardisation,depDeriveMortalite,depDeriveFecondite,depStandardisationMortalite,depStandardisationFecondite,depMortaliteFecondite)
}

if (verification == "oui") {
  #mat <- data.frame(mat)
  visualisationDonnees(mat)
}

creerCsv(mat,nomCsv,chemin)

cor(mat)




  