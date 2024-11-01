package reseau;

import java.io.BufferedReader;
import java.io.IOException;

public class TransmettreMessageServeur implements Runnable {
    public BufferedReader in;
    public Serveur serveur;
    public String pseudo;

    public TransmettreMessageServeur(BufferedReader in, Serveur serveur, String pseudo) {
        this.in = in;
        this.serveur = serveur;
        this.pseudo = pseudo;
    }

    public BufferedReader getIn() {
        return in;
    }

    public void setIn(BufferedReader in) {
        this.in = in;
    }

    public Serveur getServeur() {
        return serveur;
    }

    public void setServeur(Serveur serveur) {
        this.serveur = serveur;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }



 
@Override
    public void run() {
        try {
            String message;
            while ((message = in.readLine()) != null) {
                // Vérifier si le message contient un destinataire spécifié
                int separatorIndex = message.indexOf(":");
                if (separatorIndex != -1) {
                    String destinataire = message.substring(0, separatorIndex);
                    String contenuMessage = message.substring(separatorIndex + 1);

                    // Si le destinataire est "tous", envoyer à tout le monde
                    if (destinataire.equalsIgnoreCase("tous")) {
                        this.getServeur().envoyerMessage(contenuMessage, pseudo);
                    } else {
                        this.getServeur().envoyerMessagePrive(contenuMessage, destinataire, pseudo);
                    }
                } 
            }
            this.serveur.closeSocket(pseudo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
