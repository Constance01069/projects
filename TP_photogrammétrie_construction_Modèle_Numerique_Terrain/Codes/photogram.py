#!/usr/bin/python3
#-*- coding: utf-8 -*-

import numpy as np
import matplotlib.image as mpimg
#from scipy import spatial
import math
import os

from mpl_toolkits.mplot3d import Axes3D 
import matplotlib.pyplot as plt




print("Debut programme")
os.chdir('/home/bauc/Vidéos/CodesTP1/Codes')
####################################################
## Chargement des donnees
lpts1 = np.loadtxt('PtImg1.data')
lpts2 = np.loadtxt('PtImg2.data')
####################################################


####################################################
## Caracteristiques prises de vues
nPts = len(lpts1)

F = np.array([0.0,0.0,-0.08])
U = np.array([1.0,0.0,0.0])
V = np.array([0.0,1.0,0.0])

## Prise de vue 1 :
# Completer ICI :
thetaY1 = -np.pi/20
rotY1 = np.array([
    [np.cos(thetaY1), 0, np.sin(thetaY1)],
    [0, 1, 0],
    [-np.sin(thetaY1), 0, np.cos(thetaY1)]
])
C1 = np.array([5, 10, 40])
F1 = np.dot(rotY1, F)  


U1 = np.array([])
V1 = np.array([])

## Prise de vue 2 :
# Completer ICI :
thetaY2 = 0
rotY2 = np.array([
    [np.cos(thetaY2), 0, np.sin(thetaY2)],
    [0, 1, 0],
    [-np.sin(thetaY2), 0, np.cos(thetaY2)]
])
C2 = np.array([10, 10, 40])
F2 = np.dot(rotY2, F)

#U2 = ...
#V2 = ...
####################################################


####################################################
## Photogrammetrie

listPts3D = []

# Completer ICI (Second membre q. 2.b) )
scmb = np.hstack((C1, C2)) 





maxx = 0.0
for i in range(nPts):
    print(i," / ",nPts)
    ## Coordonnees locales point vers coordonnees globales 
    uI1 = lpts1[i][0]
    vI1 = lpts1[i][1]
    # Completer ICI (q. 2.a) ) :
    pI1 = C1+F1+np.dot(rotY1,np.array([uI1, vI1, 0]))
    
    uI2 = lpts2[i][0]
    vI2 = lpts2[i][1]
    # Completer ICI (q. 2.a) ) :
    pI2 = C2+F2+np.dot(rotY2,np.array([uI2, vI2,0]))
    
    v1 = (C1-pI1)
    v2 = (C2-pI2)
    
    ## Matrice du systeme lineaire a resoudre
    # Completer ICI (q. 2.b) ) :
    mat = np.zeros((6,5))
    mat[0,:] = [1,0,0,v1[0],0]
    mat[1,:] = [0,1,0,v1[1],0]
    mat[2,:] = [0,0,1,v1[2],0]
    mat[3,:] = [1,0,0,0,v2[0]]
    mat[4,:] = [0,1,0,0,v2[1]]
    mat[5,:] = [0,0,1,0,v2[2]]
    print("dim de mat:", mat.shape)
    print("dim de scmb:", scmb.shape)
    print(repr(scmb))
    
    ## Resolution effective du systeme lineaire
    # Completer ICI (q. 2.c) ) :
     
    sol, resids, rank, s = np.linalg.lstsq(mat, scmb, rcond=None)
    
    # Calcul erreur :
    vv = np.dot(mat,sol)-scmb
    err = np.sqrt(np.dot(vv.transpose(),vv))
    #print(err)
    if (err >= maxx):
        maxx = err
    print(repr(sol))
    
    listPts3D.append(np.array([sol[0],sol[1],sol[2]]))
    
print("Erreur max",maxx)


llPts3D = np.array(listPts3D)
np.savetxt('Pt3D.xyz',llPts3D)
#print(llPts3D)
fig = plt.figure()
ax = fig.add_subplot(111, projection='3d')

ax.scatter(llPts3D[:,0], llPts3D[:,1], llPts3D[:,2],marker='.',linewidths=0.2)

plt.show()

print("Fin programme")
    
    