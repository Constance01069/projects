package reseau;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;

public class Client {
    Socket socket;
    PrintWriter out;
    BufferedReader in;
    static String IP_SERV = "127.0.0.1"; // Mettre ici l'adresse du serveur

    public Client(String pseudo) {
        try {
            this.socket = new Socket(IP_SERV, 2021);
            this.out = new PrintWriter(this.socket.getOutputStream());
            this.in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        this.out.println(pseudo);
        this.out.flush();

        // Thread threadReceptionMessage = new Thread(new RecevoirMessageClient(this.in));
        // threadReceptionMessage.start();
    }

    public void envoyerMessage(String destinataire, String msg) {
        this.out.println(destinataire + ":" + msg);
        this.out.flush();
    }

    public String messageRecu() {
        String msg = "";

        try {
            msg = this.in.readLine();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return msg;
    }
}