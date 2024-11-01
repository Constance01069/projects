package reseau;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

class Accepter_clients implements Runnable {
		private Serveur serveur;
	    private Socket socket;
	    private int nbClients = 0;

		public Accepter_clients(Serveur serveur) {
			this.serveur = serveur;
		}

			
		public void run() {
			BufferedReader in;
			String pseudo;
			try {
				while(true) {
					socket = serveur.getSocketserver().accept(); // Un client se connecte on l'accepte
					nbClients++;
					System.out.println("Le client numéro " + nbClients + " est connecté !");
					in = new BufferedReader (new InputStreamReader (socket.getInputStream()));
					pseudo = in.readLine();
					this.serveur.getSockets().put(pseudo, socket);
					new Thread(new TransmettreMessageServeur(in, serveur, pseudo)).start();
				}           
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}
	}
