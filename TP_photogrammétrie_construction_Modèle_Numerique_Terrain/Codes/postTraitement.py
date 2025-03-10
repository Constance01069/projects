#!/usr/bin/python3.4
# -*-coding:Utf-8 -*

# by A. Tonnoir

import numpy as np
import copy

from scipy import spatial
import matplotlib.pyplot as plt
import maillage as mesh
import os
    
os.chdir('/home/bauc/Vidéos/CodesTP1/Codes')     
def topoMNT(x,y,listCoordXYZ,tree): 
    # Completer ICI (q. 2.a) ) :
    dist, ind = tree.query([x, y])
    return listCoordXYZ[ind,2]

def topoMNTP1(x, y, listCoordXYZ, tree):
    

    dist, ind = tree.query([x, y], k=3)

    x1, y1, z1 = listCoordXYZ[ind[0]]
    x2, y2, z2 = listCoordXYZ[ind[1]]
    x3, y3, z3 = listCoordXYZ[ind[2]]

    #if dist[0] == 0:
        #return z1

    w1 = 1 / (dist[0] + 1e-6) #+1e-6 pour éviter une division par 0
    w2 = 1 / (dist[1] + 1e-6)
    w3 = 1 / (dist[2] + 1e-6)

    w_tot = w1 + w2 + w3
    w1 /= w_tot
    w2 /= w_tot
    w3 /= w_tot

    
    z = w1 * z1 + w2 * z2 + w3 * z3
    return z




print("Debut programme")
#########################################################
## Noms fichier lecture / ecriture :
maillageCubeReg = "Maillages/myCube.msh";
maillageCubeDef = "Maillages/cubeModifIL.msh";
nomFichierMNT = "Pt3D.xyz";
#########################################################


#########################################################
## Lecture et traitement du fichier MNT :
## Lecture :
fid = open(nomFichierMNT,"r");

contenu = fid.read();
contenu = contenu.split("\n");

listCoordIni = []

for e in contenu:
    res = e.split(" ")
    if (len(res) > 1):
        res = [float(res[0]), float(res[1]), float(res[2])]
        listCoordIni.append([res[0],res[1],res[2]]);
        

listCoordIni = np.array(listCoordIni)

## Traitement des donnees :
## Reduction au carre [0,1]^2
xmin = min(listCoordIni[:,0])
xmax = max(listCoordIni[:,0])
ymin = min(listCoordIni[:,1])
ymax = max(listCoordIni[:,1])
zmin = min(listCoordIni[:,2])
zmax = max(listCoordIni[:,2])

print(xmin, xmax)
print(ymin, ymax)
print(zmin, zmax)

## On ramene les coordonnees X,Y sur le carree (0,1)^2
listCoordIni[:,0] = (listCoordIni[:,0] - xmin) / (xmax - xmin) 
listCoordIni[:,1] = (listCoordIni[:,1] - ymin) / (ymax - ymin) 

listCoordXY = []
for e in listCoordIni:
    listCoordXY.append([e[0],e[1]]) ## Coordonnees X,Y
#########################################################


#########################################################
## Lecture du fichier maillage regulier :
[ptsM,m3D,m2D,m1D,m0D,mt3D,mt2D,mt1D,mt0D] = mesh.lectureMsh(maillageCubeReg)
#########################################################


#########################################################
## Deformation du maillage regulier :

maxZ = zmax
## Choix profondeur maillage :
minZ = -20;
print(maxZ, minZ)

newpoints = []
## Creation du 2D tree:
tree = spatial.KDTree(listCoordXY)

cpt = 0
nbPts = len(ptsM)
for pp in ptsM:
    cpt = cpt + 1
    if (cpt % 10 == 0):
        print(str(cpt)+" / "+str(nbPts))
    x = pp[0]
    y = pp[1]
    z = pp[2]
    
    xnew = x*(xmax-xmin)+xmin # On revient aux coordonnees de depart
    ynew = y*(ymax-ymin)+ymin # On revient aux coordonnees de depart
    
    # Completer ICI (q. 2.a) ) :
    f_xy = topoMNTP1(x, y, listCoordIni, tree)
    
    znew = minZ * (1 - z) + z * f_xy
    newpoints.append([xnew,ynew,znew])
#########################################################




#########################################################
## Ecriture nouveau maillage :
mesh.ecritureMaillage(maillageCubeDef,newpoints,m3D,m2D,m1D,m0D,mt3D,mt2D,mt1D,mt0D) 
#########################################################
