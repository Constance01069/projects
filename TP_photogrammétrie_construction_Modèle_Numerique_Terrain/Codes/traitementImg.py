#!/usr/bin/python3
#-*- coding: utf-8 -*-

import numpy as np
import matplotlib.image as mpimg
from scipy import spatial
from operator import itemgetter
import os
import image as imag
import time


os.chdir('/home/bauc/Vidéos/CodesTP1/Codes')   
def addToList(maList,maListN,ele):
    tol = 10 # 10 pixel
    
    if (len(maList) > 0):
        tree = spatial.KDTree(maList)
        [dist,ind] = tree.query(ele)
        
        if dist < tol:
            Nn = maListN[ind]
            res = (maList[ind]*(Nn)*1.0 + ele) / (Nn+1)
            maList[ind] = res
            maListN[ind] = Nn+1
        else:
            maList.append(ele)
            maListN.append(1.0) 
    else:
        maList.append(ele)
        maListN.append(1.0) 
        
start = time.time()
print("Debut programme")        
####################################################
## Lecture du fichier image
img = mpimg.imread("img2.png")
img = img*255
img = img.astype(float)
img = img[:,:,0:3]

imgB = img[:,:,:]*1.0
####################################################

####################################################
## Traitement image (detecteur de Harris)
imgBdxL = imag.moyY(imag.derivX(imgB))
Ix2 = imgBdxL * imgBdxL

imgBdyL = imag.moyX(imag.derivY(imgB))
Iy2 = imgBdyL * imgBdyL

Ixy = imgBdxL * imgBdyL


Ix2BL = imag.blur(Ix2)
Iy2BL = imag.blur(Iy2)
IxyBL = imag.blur(Ixy)


imgHH = Ix2BL*Iy2BL - IxyBL*IxyBL - 0.0*(Ix2BL+Iy2BL)*(Ix2BL+Iy2BL)

maxHH = np.max(imgHH[:,:,0])
minHH = np.min(imgHH[:,:,0])
imgHH = ((imgHH+minHH)/(maxHH-minHH))*255 

listCorner = []

listPtsPixBl = []
imgHHnew = imgHH*0.0
for i,ll in enumerate(imgHH):
    for j,cc in enumerate(ll):
        valll = 120
        if ( (cc[0] < valll) or (imgHH[i-1,j,0] < valll) or (imgHH[i+1,j,0] < valll) or (imgHH[i,j-1,0] < valll) or (imgHH[i,j+1,0] < valll) or (imgHH[i-1,j-1,0] < valll) or (imgHH[i+1,j-1,0] < valll) or (imgHH[i-1,j+1,0] < valll) or (imgHH[i+1,j+1,0] < valll) ):
            imgHHnew[i,j,:] = [0,0,0]
        else:
            listPtsPixBl.append(np.array([i*1.0,j*1.0]))
            imgHHnew[i,j,:] = [255,255,255]

imgHH = imgHHnew*1.0

## Ecriture image obtenu avec detecteur de Harris :
mpimg.imsave("img2Corner.png", imgHH.astype(np.uint8))
print("Fin detecteur Harris")
####################################################


####################################################
## Conversion points en coordonnees
listPtsPix = []
listPtsPixN = []
print("Groupe Pix Bl debut",len(listPtsPixBl))    
    
si = len(listPtsPixBl)
for j,e in enumerate(listPtsPixBl):
    if (j%100 == 0):
        print(j," / ",si)
        #print len(listPtsPix)
    addToList(listPtsPix,listPtsPixN,e)
print("Groupe Pix Bl fin")  
             
print("Len listPtsPix",len(listPtsPix))


# Conversion des pixel en coordonnees :
NrI = len(imgHH[:,0,0])
listPts = []
scale = 20.0
for e in listPtsPix:
    px = e
    x = px[1]/(NrI*scale) - 0.5 / (scale)
    y = px[0]/(NrI*scale) - 0.5 / (scale)
    
    listPts.append(np.array([x,y]))


print(len(listPts))
nnPts = int(np.sqrt(len(listPts)))

FinalList = []
sortedList = sorted(listPts, key=itemgetter(0))
for i in range(nnPts):
    sortedListTmp = sortedList[i*nnPts:(i+1)*nnPts]
    sortedListTmp = sorted(sortedListTmp, key=itemgetter(1))
    
    for e in sortedListTmp:
        FinalList.append(e)

print(len(FinalList)) 
np.savetxt('PtImg2.data',FinalList)
####################################################

print("Fin programme")
end = time.time()
print(f"Temps d'exécution : {end - start:.6f} secondes")
