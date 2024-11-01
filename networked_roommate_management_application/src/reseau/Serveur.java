package reseau;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class Serveur {
    private HashMap<String,Socket> sockets;
    private ServerSocket socketserver;

    
    public Serveur(HashMap<String,Socket> sockets, ServerSocket socketserver) {
        this.sockets = sockets;
        this.socketserver = socketserver;
    }

    public HashMap<String,Socket> getSockets() {
        return sockets;
    }


    public void setSockets(HashMap<String,Socket> sockets) {
        this.sockets = sockets;
    }


    public ServerSocket getSocketserver() {
        return socketserver;
    }


    public void setSocketserver(ServerSocket socketserver) {
        this.socketserver = socketserver;
    }

    public void closeSocket(String pseudo){
        try {
            Socket socket = this.getSockets().get(pseudo);
            socket.close();
            this.getSockets().remove(pseudo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void envoyerMessage(String s, String pseudo) {
        PrintWriter out;
        HashMap<String,Socket> socketsClone = (HashMap<String,Socket>) this.getSockets().clone(); 
        socketsClone.remove(pseudo);
        s = "A tous de " + pseudo + " :\n" + s;
        for (Socket socket : socketsClone.values()) {
            try {
                out = new PrintWriter(socket.getOutputStream());
                out.println(s);
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            
        }
    }
    
    // Envoyer un message privé à un destinataire spécifique
    public void envoyerMessagePrive(String s, String pseudoDestinataire, String pseudoExpediteur) {
        PrintWriter out;
        Socket destinataire = this.getSockets().get(pseudoDestinataire);
        if (destinataire != null) {
            s = "Message de " + pseudoExpediteur + " :\n" + s;
            try {
                out = new PrintWriter(destinataire.getOutputStream());
                out.println(s);
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    // Nouvelle méthode pour envoyer une annonce depuis l'IHM
    public void envoyerAnnonce(String annonce, String destinataire) {
        // Ton code actuel pour envoyer un message privé
        envoyerMessagePrive(annonce, destinataire, "Serveur");
    }



    public static void main(String[] args) {
    ServerSocket socket;
        try {
            socket = new ServerSocket(2021);
            Serveur serveur = new Serveur(new HashMap<String,Socket>(),socket);

            Thread t = new Thread(new Accepter_clients(serveur));

            t.start();
            System.out.println("Serveur pret pour servir plusieurs clients !");    
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

        
}