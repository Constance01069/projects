

library(dplyr)
library(ggplot2)
library(gtools)
library(data.table)
library(ggpubr)
library(tidyr)
library(knitr)
library(cowplot)


source("./R_utiles/fct_lectureExportsISIS.R")

# Chemins

# ***A REMPLIR***  Chemin où enregistrer les sorties Rds

cheminRData<- './Rdata/'

# ***A REMPLIR*** Chemin où trouver les valeurs d'entrée

cheminCombinaisonSimulation <-'./Rdata/'

# ***A REMPLIR*** Chemin pour accéder aux dossiers des simulations

dossierSim<- 'C:/Users/cbau/isis-fish-4/isis-database/simulations/'



# ***A REMPLIR*** noms du csv avec les combianaisons des simulations

#nomCsvSimu <- "paramIndep"
#nomCsvSimu <- "testRef"
#nomCsvSimu <- "paramMortalite"
#nomCsvSimu <- "paramDep0507"
nomCsvSimu <- "paramFecondite"


# ***A REMPLIR*** nom de la simulation


pattern <- "sim_paramFecondite_2024-03-22-08-37"

# ***A REMPLIR*** titre rds de sortie


titreRds <- "paramFecondite"




matSimu <- creerMatCombiSimu(nomCsvSimu,cheminCombinaisonSimulation)

matResult <- creerMatResult(dossierSim,pattern)

matComplete <- assemblerSimuSorties(matSimu,matResult)

creerFichRds(matComplete,cheminRData,titreRds)
