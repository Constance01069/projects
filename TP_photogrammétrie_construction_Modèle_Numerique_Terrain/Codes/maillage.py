#!/usr/bin/python3.4
# -*-coding:Utf-8 -*

# by A. Tonnoir

import numpy as np
import copy

import matplotlib.pyplot as plt


## Ecrire assemblage P1
## Inclure element infinis

#########################################################
## Lecture du fichier maillage msh (version 4.0) :
def lectureMsh(nomFichier):
    fid = open(nomFichier,"r");

    contenu = fid.read();
    contenu = contenu.split("\n");

    points = []
    meshes3D = []
    meshes2D = []
    meshes1D = []
    meshes0D = []
    meshesTag3D = []
    meshesTag2D = []
    meshesTag1D = []
    meshesTag0D = []
    
    it = 0
    ele = contenu[it]
    while (ele != "$Nodes"):
        it = it + 1
        ele = contenu[it]
    
    it = it + 1
    ele = contenu[it]
    res = ele.split(" ")
    nBlock = int(res[0])
    nNodes = int(res[1])
    minTag = int(res[2])
    maxTag = int(res[3])
    
    print("Nb nodes : ",nNodes)
    
    cpt = 0
    for b in range(nBlock):
        it = it + 1
        ele = contenu[it]
        res = ele.split(" ")
        [entDim,tagEnt,param,nNodeB] = [int(res[0]),int(res[1]),int(res[2]),int(res[3])]
        
        for i in range(nNodeB):
            it = it + 1
            ele = contenu[it]
        
        for i in range(nNodeB):
            it = it + 1
            ele = contenu[it]
            res = ele.split(" ")
            points.append([float(res[0]), float(res[1]), float(res[2])])

    points = np.array(points)
    print(len(points))

    while (ele != "$Elements"):
        it = it + 1
        ele = contenu[it]
    
    it = it + 1
    ele = contenu[it]
    res = ele.split(" ")
    
    [nElemB,nElems,minElemTag,maxElemTag] = [int(res[0]),int(res[1]),int(res[2]),int(res[3])]
    
    print("Number of Elements (Tot): ",nElems)
    
    for b in range(nElemB): 
        it = it + 1
        ele = contenu[it]
        res = ele.split(" ")
        [entDim,entTag,entTyp,nbEleEnt] = [int(res[0]),int(res[1]),int(res[2]),int(res[3])]
        typeEle = int(entTyp)
        tag = int(entTag)
        for i in range(nbEleEnt):
            it = it + 1
            ele = contenu[it]
            res = ele.split(" ")
            if (typeEle == 4): # Tetrahedron
                meshes3D.append([int(res[1])-1, int(res[2])-1, int(res[3])-1, int(res[4])-1])    
                meshesTag3D.append(tag)
            if (typeEle == 2): # Triangle
                meshes2D.append([int(res[1])-1, int(res[2])-1, int(res[3])-1])    
                meshesTag2D.append(tag)  
            if (typeEle == 9): # Triangle P2
                meshes2D.append([int(res[1])-1, int(res[2])-1, int(res[3])-1, int(res[4])-1, int(res[5])-1, int(res[6])-1])    
                meshesTag2D.append(tag)    
            if (typeEle == 1): # Line
                meshes1D.append([int(res[1])-1, int(res[2])-1])    
                meshesTag1D.append(tag)      
            if (typeEle == 15): # Point
                meshes0D.append([int(res[1])-1])    
                meshesTag0D.append(tag)      

        
    meshes3D = np.array(meshes3D)
    meshes2D = np.array(meshes2D)
    meshes1D = np.array(meshes1D)
    meshes0D = np.array(meshes0D)
    
    fid.close()
    return [points,meshes3D,meshes2D,meshes1D,meshes0D,meshesTag3D,meshesTag2D,meshesTag1D,meshesTag0D]
#########################################################



#########################################################
## Lecture du fichier maillage msh (version 3.0) :
def lectureMsh3(nomFichier):
    fid = open(nomFichier,"r");

    contenu = fid.read();
    contenu = contenu.split("\n");

    points = []
    meshes3D = []
    meshes2D = []
    meshes1D = []
    meshes0D = []
    meshesTag3D = []
    meshesTag2D = []
    meshesTag1D = []
    meshesTag0D = []
    
    it = 0
    ele = contenu[it]
    while (ele != "$Nodes"):
        it = it + 1
        ele = contenu[it]

    it = it + 1    
    nbNodes = int(contenu[it])
    print("Nb nodes : ",nbNodes)
    for i in range(nbNodes):
        it = it + 1
        ele = contenu[it]
        res = ele.split(" ")
        points.append([float(res[1]), float(res[2]), float(res[3])])

    points = np.array(points)
    print(len(points))

    while (ele != "$Elements"):
        it = it + 1
        ele = contenu[it]
    
    it = it + 1    
    nbElements = int(contenu[it])
    print("Nb elements : ",nbElements)
    for i in range(nbElements):
        it = it + 1
        ele = contenu[it]
        res = ele.split(" ")
        if (float(res[1]) == 4): # Tetrahedron
            meshes3D.append([int(res[5])-1, int(res[6])-1, int(res[7])-1, int(res[8])-1])    
            meshesTag3D.append(res[3])
        if (float(res[1]) == 2): # Triangle
            meshes2D.append([int(res[5])-1, int(res[6])-1, int(res[7])-1])    
            meshesTag2D.append(res[3])
        if (float(res[1]) == 1): # Line
            meshes1D.append([int(res[5])-1, int(res[6])-1])    
            meshesTag1D.append(res[3])
        if (float(res[1]) == 15): # Point
            meshes0D.append(int(res[5])-1)    
            meshesTag0D.append(res[3])    
            
    meshes3D = np.array(meshes3D)
    meshes2D = np.array(meshes2D)
    meshes1D = np.array(meshes1D)
    meshes0D = np.array(meshes0D)
    
    fid.close()
    return [points,meshes3D,meshes2D,meshes1D,meshes0D,meshesTag3D,meshesTag2D,meshesTag1D,meshesTag0D]
#########################################################


#########################################################
## Ecriture solution fichier vtk (version 3.0) :
def ecritureSol(nomFichier,Sol,Solex,err,pts,m3D):
    fid = open(nomFichier,"w")

    # Ecriture de la solution :
    fid.write("# vtk DataFile Version 3.6\n")
    fid.write("PolyDATA\n")
    fid.write("ASCII\n")
    fid.write("DATASET UNSTRUCTURED_GRID\n")

    # Ecriture points du maillage :
    fid.write(str("POINTS   "+str(len(pts))+"    float\n"))

    for p in pts:
        fid.write(str(str(p[0])+" "+str(p[1])+"    "+str(p[2])+"\n"))

    # Ecriture mailles :
    fid.write(str("CELLS    "+str(len(m3D))+"    "+str(len(m3D)*5)+"\n"))

    for m in m3D:
        fid.write("4    "+str(m[0])+"    "+str(m[1])+"    "+str(m[2])+"    "+str(m[3])+"\n")

    fid.write("CELL_TYPES    "+str(len(m3D))+"\n")
    for m in m3D:
        fid.write("10\n")

    # Ecriture valeur solution aux noeuds du maillage :
    fid.write("POINT_DATA   "+str(len(pts))+"\n")
    fid.write("SCALARS scalars float    3\n");
    fid.write("LOOKUP_TABLE default\n");

    for ite,e in enumerate(Sol):
        fid.write(str(e)+"  "+str(Solex[ite])+" "+str(err[ite])+"\n")
#########################################################


#########################################################
## Ecriture solution fichier vtk (version 3.0) :
def ecritureMaillage(nomFichier,ptsM,m3D,m2D,m1D,m0D,mt3D,mt2D,mt1D,mt0D):
    fid = open(nomFichier,"w")

    # Ecriture du maillage :
    fid.write("$MeshFormat\n")
    fid.write("2.2 0 8\n")
    fid.write("$EndMeshFormat\n")
    
    # Ecriture points
    fid.write("$Nodes\n")
    fid.write(str(len(ptsM))+"\n")

    for i,p in enumerate(ptsM):
        fid.write(str(str(i+1)+" "+str(p[0])+" "+str(p[1])+" "+str(p[2])+"\n"))

    fid.write("$EndNodes\n")
    
        
    # Ecriture mailles :
    fid.write("$Elements\n")
    fid.write(str(len(m3D))+"\n") # +len(m2D)+len(m1D)+len(m0D)
    cpt = 1
    """
    for i,m in enumerate(m0D):
        fid.write(str(cpt)+" 15 2 "+str(mt0D[i])+" "+str(mt0D[i])+" "+str(m+1)+"\n")
        cpt = cpt+1
        
    for i,m in enumerate(m1D):
        fid.write(str(cpt)+" 1 2 "+str(mt1D[i])+" "+str(mt1D[i])+" "+str(m[0]+1)+" "+str(m[1]+1)+"\n")
        cpt = cpt+1
        
    for i,m in enumerate(m2D):
        fid.write(str(cpt)+" 2 2 "+str(mt2D[i])+" "+str(mt2D[i])+" "+str(m[0]+1)+" "+str(m[1]+1)+" "+str(m[2]+1)+"\n")
        cpt = cpt+1
    """    
    for i,m in enumerate(m3D):
        fid.write(str(cpt)+" 4 2 "+str(mt3D[i])+" "+str(mt3D[i])+" "+str(m[0]+1)+" "+str(m[1]+1)+" "+str(m[2]+1)+" "+str(m[3]+1)+"\n")
        cpt = cpt+1
   
    fid.write("$EndElements\n")
#########################################################