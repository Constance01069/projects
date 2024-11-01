library(knitr)
library(magrittr)
library(dplyr)
library(ggplot2)
library(psych)
library(copula)
library(tidyverse)
library(ranger)
library(sobolMDA)


grapheOOBpredictionError <- function (model,export,donnees){
  
  
  moyenneExports <- donnees %>% summarise(mean = mean({{export}}))
  erreur <- model_biomasse$prediction.error
  pourcentage <- (erreur/moyenneExports)*100
  result <- c(moyenneExports, erreur, pourcentage)
  result <- setNames(result, c("Moyenne_exports", "Erreur", "Pourcentage"))
  ggplot(result)
  return(result)
  
}

calculIndiceSobolTotaux <- function(donnees,export){
  
  X <- donnees %>% select(Survie, Derive, Standardisation, Mortalite, Fecondite)
  X1 <- as.matrix(X)
  y <- donnees %>% select({{export}})
  y1 <- as.matrix(y)
  
  
  x<-shapleysobol_knn(model=NULL, X=X1,
                      
                      n.knn=3,
                      U=0,
                      noise=TRUE,
                      boot.level=0.7, 
                      conf=0.95)
  tell(x,y1)
  return(x)
  
}

creerTableauIndiceSTIndep <- function (donnees,export,annee){
  
  x1 <- calculIndiceSobolTotaux(donnees,{{export}})
  
  
  indice1 <- x1$Sobol
  
  temp <-  indice1 %>%as.vector() %>% bind_cols(names(indice1))
  indIndepBio <-  temp %>%mutate(Parametre=c("Survie","Derive","Standardisation","Mortalite","Fecondite")) %>% rename(Indice = 1)
  indIndepBio <- indIndepBio %>% mutate(estimationIndice = " ST indep")%>% mutate(annee =annee)
  return(indIndepBio)
  
}

creerTableauIndiceSTDep <- function (donnees,export,annee){
  
  x1 <- calculIndiceSobolTotaux(donnees,{{export}})
  
  
  indice1 <- x1$Sobol
  
  temp <-  indice1 %>%as.vector() %>% bind_cols(names(indice1))
  indDepBio <-  temp %>%mutate(Parametre=c("Survie","Derive","Standardisation","Mortalite","Fecondite")) %>% rename(Indice = 1)
  indDepBio <- indDepBio %>% mutate(estimationIndice = " ST dep")%>% mutate(annee =annee)
  return(indDepBio)
  
}



creerTableauComparatif <- function(nomRDS,annee,export){
  
  
}

obtenirTableauIndicesBio <- function(cheminRData, nomRDS){
  
  fresult_paramIndep <- readRDS(file=paste0(cheminRData, nomRDS))
  
  
  
  fdonneesIndepRD_annee1 <- fresult_paramIndep %>% filter(year==1)
  fdonneesIndepRD_annee2 <- fresult_paramIndep %>% filter(year==2)
  fdonneesIndepRD_annee3 <- fresult_paramIndep %>% filter(year==3)
  fdonneesIndepRD_annee4 <- fresult_paramIndep %>% filter(year==4)
  
  fmodel_ParamIndep_biomasse_annee1 <- sobolMDA::ranger(formula=biomasse ~ Survie + Derive + Standardisation + Mortalite + Fecondite , data
                                                       =fdonneesIndepRD_annee1,importance = "sobolMDA")
  fmodel_ParamIndep_biomasse_annee2 <- sobolMDA::ranger(formula=biomasse ~ Survie + Derive + Standardisation + Mortalite + Fecondite , data
                                                       =fdonneesIndepRD_annee2,importance = "sobolMDA")
  fmodel_ParamIndep_biomasse_annee3 <- sobolMDA::ranger(formula=biomasse ~ Survie + Derive + Standardisation + Mortalite + Fecondite , data
                                                       =fdonneesIndepRD_annee3,importance = "sobolMDA")
  fmodel_paramIndep_biomasse_annee4 <- sobolMDA::ranger(formula=biomasse ~ Survie + Derive + Standardisation + Mortalite + Fecondite , data
                                                       =fdonneesIndepRD_annee4,importance = "sobolMDA")
  
  fimportancesIndepBio_anne1 <- sobolMDA:: importance(fmodel_ParamIndep_biomasse_annee1)
  fimportancesIndepBio_anne2 <- sobolMDA:: importance(fmodel_ParamIndep_biomasse_annee2)
  fimportancesIndepBio_anne3 <- sobolMDA:: importance(fmodel_ParamIndep_biomasse_annee3)
  fimportancesIndepBio_anne4 <- sobolMDA:: importance(fmodel_paramIndep_biomasse_annee4)
  
  
  
  fannee1 <- fimportancesIndepBio_anne1 %>%
    tibble::enframe() %>%
    mutate(Parametre = name) %>%
    select(-name) %>%
    rename(Indice = value) %>%
    mutate(annee = 1)
  
  fannee2 <- fimportancesIndepBio_anne2 %>%
    tibble::enframe() %>%
    mutate(Parametre = name) %>%
    select(-name) %>%
    rename(Indice = value) %>%
    mutate(annee = 2)
  
  fannee3 <- fimportancesIndepBio_anne3 %>%
    tibble::enframe() %>%
    mutate(Parametre = name) %>%
    select(-name) %>%
    rename(Indice = value) %>%
    mutate(annee = 3)
  
  fannee4 <- fimportancesIndepBio_anne4 %>%
    tibble::enframe() %>%
    mutate(Parametre = name) %>%
    select(-name) %>%
    rename(Indice = value) %>%
    mutate(annee = 4)
  
  fisa_bio_dep <- bind_rows(fannee1, fannee2) %>% bind_rows(fannee3) %>% bind_rows(fannee4)
  return(fisa_bio_dep)
  
}

# calculIndicesAvecIntervalle <- function(cheminRData,nomRds,annee){
#   
#   fresult_paramIndep <- readRDS(file=paste0(cheminRData, nomRDS))
#   
#   fdonneesRD_annee <- fresult_paramIndep %>% filter(year==annee)
#   fmodel_ParamIndep_biomasse_annee1 <- sobolMDA::ranger(formula=biomasse ~ Survie + Derive + Standardisation + Mortalite + Fecondite , data
#                                                         =fdonneesIndepRD_annee1,importance = "sobolMDA")
#   
#   tab <- importancesIndepBio_anne4 %>%
#     tibble::enframe() %>% spread(key = name,value = value)
#     
#   for (i in 1:9) {
#     
#     fmodel_ParamIndep_biomasse_annee1 <- sobolMDA::ranger(formula=biomasse ~ Survie + Derive + Standardisation + Mortalite + Fecondite , data
#                                                           =fdonneesIndepRD_annee1,importance = "sobolMDA")
#     
#     temp <- importancesIndepBio_anne4 %>%
#       tibble::enframe() %>% spread(key = name,value = value)
#     tab <- tab %>% 
#     
#   }
#   return(tab)
#   
#   
#   
#   
#   
#   
#   
# }

obtenirTableauIndicesCap <- function(cheminRData, nomRDS){
  
  fresult_paramIndep <- readRDS(file=paste0(cheminRData, nomRDS))
  
  
  
  fdonneesIndepRD_annee1 <- fresult_paramIndep %>% filter(year==1)
  fdonneesIndepRD_annee2 <- fresult_paramIndep %>% filter(year==2)
  fdonneesIndepRD_annee3 <- fresult_paramIndep %>% filter(year==3)
  fdonneesIndepRD_annee4 <- fresult_paramIndep %>% filter(year==4)
  
  fmodel_ParamIndep_biomasse_annee1 <- sobolMDA::ranger(formula=captures ~ Survie + Derive + Standardisation + Mortalite + Fecondite , data
                                                        =fdonneesIndepRD_annee1,importance = "sobolMDA")
  fmodel_ParamIndep_biomasse_annee2 <- sobolMDA::ranger(formula=captures ~ Survie + Derive + Standardisation + Mortalite + Fecondite , data
                                                        =fdonneesIndepRD_annee2,importance = "sobolMDA")
  fmodel_ParamIndep_biomasse_annee3 <- sobolMDA::ranger(formula=captures ~ Survie + Derive + Standardisation + Mortalite + Fecondite , data
                                                        =fdonneesIndepRD_annee3,importance = "sobolMDA")
  fmodel_paramIndep_biomasse_annee4 <- sobolMDA::ranger(formula=captures ~ Survie + Derive + Standardisation + Mortalite + Fecondite , data
                                                        =fdonneesIndepRD_annee4,importance = "sobolMDA")
  
  fimportancesIndepBio_anne1 <- sobolMDA:: importance(fmodel_ParamIndep_biomasse_annee1)
  fimportancesIndepBio_anne2 <- sobolMDA:: importance(fmodel_ParamIndep_biomasse_annee2)
  fimportancesIndepBio_anne3 <- sobolMDA:: importance(fmodel_ParamIndep_biomasse_annee3)
  fimportancesIndepBio_anne4 <- sobolMDA:: importance(fmodel_paramIndep_biomasse_annee4)
  
  
  
  fannee1 <- fimportancesIndepBio_anne1 %>%
    tibble::enframe() %>%
    mutate(Parametre = name) %>%
    select(-name) %>%
    rename(Indice = value) %>%
    mutate(annee = 1)
  
  fannee2 <- fimportancesIndepBio_anne2 %>%
    tibble::enframe() %>%
    mutate(Parametre = name) %>%
    select(-name) %>%
    rename(Indice = value) %>%
    mutate(annee = 2)
  
  fannee3 <- fimportancesIndepBio_anne3 %>%
    tibble::enframe() %>%
    mutate(Parametre = name) %>%
    select(-name) %>%
    rename(Indice = value) %>%
    mutate(annee = 3)
  
  fannee4 <- fimportancesIndepBio_anne4 %>%
    tibble::enframe() %>%
    mutate(Parametre = name) %>%
    select(-name) %>%
    rename(Indice = value) %>%
    mutate(annee = 4)
  
  fisa_bio_dep <- bind_rows(fannee1, fannee2) %>% bind_rows(fannee3) %>% bind_rows(fannee4)
  return(fisa_bio_dep)
  
}


obtenirTableauIndicesBioFe <- function(cheminRData, nomRDS){
  
  fresult_paramIndep <- readRDS(file=paste0(cheminRData, nomRDS))
  
  
  
  fdonneesIndepRD_annee1 <- fresult_paramIndep %>% filter(year==1)
  fdonneesIndepRD_annee2 <- fresult_paramIndep %>% filter(year==2)
  fdonneesIndepRD_annee3 <- fresult_paramIndep %>% filter(year==3)
  fdonneesIndepRD_annee4 <- fresult_paramIndep %>% filter(year==4)
  
  fmodel_ParamIndep_biomasse_annee1 <- sobolMDA::ranger(formula=biomasseFeconde ~ Survie + Derive + Standardisation + Mortalite + Fecondite , data
                                                        =fdonneesIndepRD_annee1,importance = "sobolMDA")
  fmodel_ParamIndep_biomasse_annee2 <- sobolMDA::ranger(formula=biomasseFeconde ~ Survie + Derive + Standardisation + Mortalite + Fecondite , data
                                                        =fdonneesIndepRD_annee2,importance = "sobolMDA")
  fmodel_ParamIndep_biomasse_annee3 <- sobolMDA::ranger(formula=biomasseFeconde ~ Survie + Derive + Standardisation + Mortalite + Fecondite , data
                                                        =fdonneesIndepRD_annee3,importance = "sobolMDA")
  fmodel_paramIndep_biomasse_annee4 <- sobolMDA::ranger(formula=biomasseFeconde ~ Survie + Derive + Standardisation + Mortalite + Fecondite , data
                                                        =fdonneesIndepRD_annee4,importance = "sobolMDA")
  
  fimportancesIndepBio_anne1 <- sobolMDA:: importance(fmodel_ParamIndep_biomasse_annee1)
  fimportancesIndepBio_anne2 <- sobolMDA:: importance(fmodel_ParamIndep_biomasse_annee2)
  fimportancesIndepBio_anne3 <- sobolMDA:: importance(fmodel_ParamIndep_biomasse_annee3)
  fimportancesIndepBio_anne4 <- sobolMDA:: importance(fmodel_paramIndep_biomasse_annee4)
  
  
  
  fannee1 <- fimportancesIndepBio_anne1 %>%
    tibble::enframe() %>%
    mutate(Parametre = name) %>%
    select(-name) %>%
    rename(Indice = value) %>%
    mutate(annee = 1)
  
  fannee2 <- fimportancesIndepBio_anne2 %>%
    tibble::enframe() %>%
    mutate(Parametre = name) %>%
    select(-name) %>%
    rename(Indice = value) %>%
    mutate(annee = 2)
  
  fannee3 <- fimportancesIndepBio_anne3 %>%
    tibble::enframe() %>%
    mutate(Parametre = name) %>%
    select(-name) %>%
    rename(Indice = value) %>%
    mutate(annee = 3)
  
  fannee4 <- fimportancesIndepBio_anne4 %>%
    tibble::enframe() %>%
    mutate(Parametre = name) %>%
    select(-name) %>%
    rename(Indice = value) %>%
    mutate(annee = 4)
  
  fisa_bio_dep <- bind_rows(fannee1, fannee2) %>% bind_rows(fannee3) %>% bind_rows(fannee4)
  return(fisa_bio_dep)
  
}
