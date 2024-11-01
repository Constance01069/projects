library(tidyverse)
library(ranger)
library(sobolMDA)
library(sensitivity)

grapheExport_1param_1annee <- function(Sortie,Parametre, numAnnee, donnees){
  
      mat <- donnees %>% filter(year == numAnnee)
     
      ggplot(mat, aes(x={{Parametre}}, y={{Sortie}}))+geom_point()+ggtitle(paste("annee",numAnnee))
      

}

filtrerAnnee <- function(donnees,annee){
  
  mat <- donnees %>% filter(year == annee)
  return(mat)
}

grapheExport_1param_4annee <- function(Sortie,Parametre, donnees){
  
  
    
  
  
  grapheExport_1param_1annee({{Sortie}},{{Parametre}}, 1, donnees)
  
  grapheExport_1param_1annee({{Sortie}},{{Parametre}}, 2, donnees)
  
  #grapheExport_1param_1annee(Sortie,Parametre, 3, donnees)
  #grapheExport_1param_1annee(Sortie,Parametre, 4, donnees)
}

#ggplot(result_derive, aes(x=Derive, y=biomasse))+geom_point()+ggtitle(paste("annee"))+facet_wrap(~year)
