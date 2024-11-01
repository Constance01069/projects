package annonces;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import reseau.Client;

public class Annonce {

    private List<Client> destinataires;
    private String contenu;
    private LocalDate datePublication;
    private LocalDate dateExpiration;

    public Annonce(String contenu, List<Client> destinataires, LocalDate datePublication) {
        this.destinataires = new ArrayList<>();
        this.contenu = contenu;
        this.datePublication = LocalDate.now();
        this.dateExpiration = this.datePublication.plus(1,ChronoUnit.WEEKS);
    }

    public void ajouterDestinataire(Client client) {
        destinataires.add(client);
        System.out.println("Destinataire ajouté à l'annonce.");
    }

    public List<Client> getDestinataires() {
        return destinataires;
    }

    public String getContenu() {
        return contenu;
    }

    public LocalDate getDatePublication() {
        return datePublication;
    }

    public void setDatePublication(LocalDate datePublication) {
        this.datePublication = datePublication;
    }

    public void setDestinataires(List<Client> destinataires) {
        this.destinataires = destinataires;
    }

    public void setContenu(String contenu) {
        this.contenu= contenu;
    }

    public void setDateExpiration(LocalDate dateExpiration) {
        this.dateExpiration = dateExpiration;
    }

    public LocalDate getDateExpiration() {
        return dateExpiration;
    }

    public void sauvegarderAnnonce(String nomFichier) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomFichier))) {
            writer.write(contenu);
            writer.newLine();
            writer.write(datePublication.toString());

            System.out.println("Annonce sauvegardée avec succès.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Annonce chargerAnnonce(String nomFichier) {
        try (BufferedReader reader = new BufferedReader(new FileReader(nomFichier))) {
            String contenu = reader.readLine();
            LocalDate datePublication = LocalDate.parse(reader.readLine());

            System.out.println("Annonce chargée avec succès.");
            return new Annonce(contenu, new ArrayList<>(), datePublication);
            } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void supprimerAnnoncesPerimees(List<Annonce> annonces) {
        Iterator<Annonce> iterator = annonces.iterator();
        LocalDate currentDate = LocalDate.now();

        while (iterator.hasNext()) {
            Annonce annonce = iterator.next();
            if (annonce.getDateExpiration().isBefore(currentDate)) {
                iterator.remove();
            }
        }
    }

    public static void main(String[] args) {
        String contenu = "blablabla";
        LocalDate datePublication = LocalDate.now();
        Annonce annonce = new Annonce(contenu, new ArrayList<>(), datePublication);

        System.out.println("Contenu de l'annonce: " + annonce.getContenu());
        System.out.println("Date de publication: " + annonce.getDatePublication());
        System.out.println("Date d'expiration: " + annonce.getDateExpiration());
    }
}



    /*
    public void publierAnnonce(String texte, List<Personne> destinataires, Date datePeremption, Personne auteur) {
        //on choisit le ou les destinaiare(s) dans l'IHM qui propose une liste 
        //on récupère le texte 

        //on crée une new annonce 
        Annonce nouvelleAnnonce = new Annonce(texte, destinataires, datePeremption, auteur);
        for (Personne dest : destinataires) {
            dest.add(nouvelleAnnonce);
        } 
    }
 */





