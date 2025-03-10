
def blur(img):
    imgBL = moyY(moyX(img))
    nbSmooth = 5
    for i in range(nbSmooth):
        imgBL = moyY(moyX(imgBL))
    
    return imgBL


def derivX(img):
    NrI = len(img[:,0,0])
    imgBdxL = img*0.0
    
    imgBdx = img[:,2:NrI,:] - img[:,0:(NrI-2),:]
    imgBdxL[:,1:(NrI-1),:] = imgBdx


    return imgBdxL
    
def derivY(img):
    NrI = len(img[:,0,0])
    imgBdyL = img*0.0
    
    imgBdy = img[2:NrI,:,:] - img[0:(NrI-2),:,:]
    imgBdyL[1:(NrI-1),:,:] = imgBdy

    return imgBdyL
    
def moyX(img):
    NrI = len(img[:,0,0])
    imgBmxL = img*0.0
    
    imgBmx = img[:,2:NrI,:] + img[:,1:(NrI-1),:]*2 + img[:,0:(NrI-2),:]
    imgBmxL[:,1:(NrI-1),:] = imgBmx
    
    return imgBmxL
    
def moyY(img):
    NrI = len(img[:,0,0])
    imgBmyL = img*0.0
    
    imgBmy = img[2:NrI,:,:] + img[1:(NrI-1),:,:]*2 + img[0:(NrI-2),:,:]
    imgBmyL[1:(NrI-1),:,:] = imgBmy


    return imgBmyL
        
        
def moyFX(img):
    NrI = len(img[:,0,0])
    imgBmxL = img*0.0
    
    imgBmx = img[:,2:NrI,:] + img[:,1:(NrI-1),:] + img[:,0:(NrI-2),:]
    imgBmxL[:,1:(NrI-1),:] = imgBmx

    return imgBmxL
    
def moyFY(img):
    NrI = len(img[:,0,0])
    imgBmyL = img*0.0
    
    imgBmy = img[2:NrI,:,:] + img[1:(NrI-1),:,:] + img[0:(NrI-2),:,:]
    imgBmyL[1:(NrI-1),:,:] = imgBmy


    return imgBmyL
    