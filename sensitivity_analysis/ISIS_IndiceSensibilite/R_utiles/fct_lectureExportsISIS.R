
library(dplyr)
library(ggplot2)
library(gtools)
library(data.table)
library(ggpubr)
library(tidyr)
library(knitr)
library(cowplot)

creerMatCombiSimu <- function(nomCsv,cheminCsv) {
  
  CombinaisonSimulation <- fread(paste0(cheminCsv,nomCsv,".csv"), sep=';', h=T)
  colnames(CombinaisonSimulation) <- c('Survie','Derive','Standardisation','Mortalite','Fecondite')
  CombinaisonSimulation <- CombinaisonSimulation %>% mutate(simname = row_number())
  return(CombinaisonSimulation)
  
  
}

# prendre le même nom que le fichier de l'export
creerMatResult <- function(DossierSimu,pattern){
  
  simPath1 = list.files(DossierSimu, pattern=pattern)
  simPath1 <- mixedsort(sort(simPath1)) # uniquement si le dossier du plan (dans isis-fish-4) n'a pas ?t? supprim?
  simPath1 <- simPath1[-1] # uniquement si le dossier du plan (dans isis-fish-4) n'a pas ?t? supprim?
  
  # nb simus
  nb_simus=length(simPath1)
  
  
  biosc <- data.frame()
  
  
  ssbiosc <- data.frame()
  
  
  catsc <- data.frame()
  
    
    for(s in (1:nb_simus)){
      
      
      biomass = read.csv(paste0(DossierSimu,simPath1[s],'/resultExports/BiomasseBeginMonth_Janvier.csv'),header=F,sep=";")
      colnames(biomass) = c("pop","step","value")
      bio <- biomass %>% 
        mutate(year = floor(step/12),
               simname=s) %>% 
        select(simname,year,value)
      
      biosc <- rbind(biosc,bio)
      
      ssbiomass = read.csv(paste0(DossierSimu,simPath1[s],'/resultExports/BiomasseBeginMonthFeconde_Janvier.csv'),header=F,sep=";")
      colnames(ssbiomass) = c("pop","step","value")
    
    
    
   
  
    
    
      
      
    
        
        ssbio <- ssbiomass %>%
          mutate(year=floor(step/12),
                 simname=s) %>%
          select(simname,year,value)
        
        ssbiosc <- rbind(ssbiosc,ssbio)
        
        
      
       
      
    
   
    
      
        
        catch = read.csv(paste0(DossierSimu,simPath1[s],'/resultExports/CatchWeightSp_Year.csv'),header=T,sep=";")
        colnames(catch) = c("pop","step","value")
        
        cat <- catch%>%
          mutate(simname=s,year=step)%>%
          select(simname,year,value)
        
        catsc <- rbind(catsc,cat)
        
    
    
    
    }
  colnames(biosc) <- c("simname","year","biomasse")
  colnames(ssbiosc) <- c("simname","year","biomasseFeconde")
  colnames(catsc) <- c("simname","year","captures")
  mat <- left_join(biosc, ssbiosc, by = c("simname","year"))
  mat <- left_join(mat, catsc, by = c("simname","year"))
  return(mat) 
}

# assembler la matrice des résultats et celle des valeurs des paramètres 
assemblerSimuSorties <- function(CombiSimu,matSorties){
  
  mat <- CombiSimu %>% left_join(matSorties, by = 'simname')
  return(mat)
  
}

# sauvegarder la matrice dans un fichier Rds
creerFichRds <- function(matComplete,dossier,nomRds) {
  chemin_complet <- file.path(dossier, nomRds)
  if (!file.exists(chemin_complet)){
    saveRDS(matComplete, file = chemin_complet)
    print("fichier rds crée")
  } else {
    print("fichier rds existe déja, effacez le avant si vous voulez le remplacer")
  }
  
}

