package ihm;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import personnes.Colocataire;
import personnes.Personne;
import personnes.Proprietaire;
import reseau.Client;

public class Menus extends Methodes_menus {
    public static JPanel appli(JFrame frame, ArrayList<Personne> users) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
        UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");

        JPanel appli = creerLePanel();
            GridBagConstraints constraintsAppli = new GridBagConstraints();

            JLabel titre = creerLabel(NOMAPP, COULEUR_TITRE_RETOUR);

            JPanel panelBoutons = creerUnPanelInterieur();
                GridBagConstraints constraintsBoutons = new GridBagConstraints();

                JLabel label = creerLabel("S'identifier", COULEUR_TEXTE);

                String[] nomBoutons = {"Propriétaire", "Colocataire"};
                JButton[] listeBoutons = creerBoutons(nomBoutons);
                for (JButton bouton : listeBoutons) {
                    bouton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            if (bouton.getText() == "Propriétaire") {
                                try {
                                    changerPanel(frame, identification(frame, PROPRIETAIRE, users));
                                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                                        | UnsupportedLookAndFeelException e1) {
                                    // TODO Auto-generated catch block
                                    e1.printStackTrace();
                                }
                            }
                            if (bouton.getText() == "Colocataire") {
                                try {
                                    changerPanel(frame, identification(frame, COLOCATAIRE, users));
                                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                                        | UnsupportedLookAndFeelException e1) {
                                    // TODO Auto-generated catch block
                                    e1.printStackTrace();
                                }
                            }
                        }
                    });
                }

                constraintsBoutons.insets = new Insets(30, 0, 30, 0);
                setGridXGridY(constraintsBoutons, 0, GridBagConstraints.RELATIVE); //GridBagConstraints.RELATIVE : chaque ajout de bouton ajoute une ligne

                panelBoutons.add(label, constraintsBoutons);
                ajouterListeBoutons(panelBoutons, listeBoutons, constraintsBoutons);
            
            setGridXGridY(constraintsAppli, 0, GridBagConstraints.RELATIVE);

            setAnchorWeightY(constraintsAppli, GridBagConstraints.PAGE_END, 0.5);
            appli.add(titre, constraintsAppli);
            
            setAnchorWeightY(constraintsAppli, GridBagConstraints.CENTER, 0.5);
            appli.add(panelBoutons, constraintsAppli);

        resizeAppli(frame, appli, titre, label, listeBoutons);
    
        return appli;
    }

    protected static void resizeAppli(JFrame frame, JPanel panel, JLabel label1, JLabel label2, JButton[] boutons) {
        panel.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                label1.setFont(fontTitre(frame));

                label2.setFont(fontAutre(frame));

                for (JButton bouton : boutons) {
                    bouton.setPreferredSize(calculerDimBouton(frame));
                    bouton.setFont(fontAutre(frame));
                }
            }
        });
    }

    public static JPanel identification(JFrame frame, int PropOuLoc, ArrayList<Personne> users) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
        UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");

        JPanel identification = creerLePanel();
            GridBagConstraints constraintsIdentification = new GridBagConstraints();
            setGridXGridY(constraintsIdentification, 0, GridBagConstraints.RELATIVE);

            JLabel titre = creerLabel("S'identifier", COULEUR_TEXTE);

            JLabel personne = new JLabel();
            if (PropOuLoc == PROPRIETAIRE) {
                personne = creerLabel("Propriétaire", Color.BLACK);
            }
            if (PropOuLoc == COLOCATAIRE) {
                personne = creerLabel("Colocataire", Color.BLACK);
            }            

            JPanel panelIdMdp = creerUnPanelInterieur();
                GridBagConstraints constraintsIdMdp = new GridBagConstraints();
                constraintsIdMdp.insets = new Insets(30, 0, 30, 0);
                setGridXGridY(constraintsIdMdp, 0, GridBagConstraints.RELATIVE);

                JPanel id = creerUnPanelInterieur();
                    JLabel labelId = creerLabel("Identifiant :", Color.BLACK);
                    JTextField tfId = new JTextField();

                    id.add(labelId, constraintsIdMdp);
                    id.add(tfId, constraintsIdMdp);

                JPanel mdp = creerUnPanelInterieur();
                    JLabel labelMdp = creerLabel("Mot de passe :", Color.BLACK);
                    JTextField tfMdp = new JTextField();

                    mdp.add(labelMdp, constraintsIdMdp);
                    mdp.add(tfMdp, constraintsIdMdp);

                JButton connexion = creerBouton("Connexion");
                connexion.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try {
                        for (Personne user : users) {
                            if (user.verifierConnexion(tfId.getText(), tfMdp.getText())) {
                                if (PropOuLoc == PROPRIETAIRE && user == getProprietaire(users)) {
                                    changerPanel(frame, Menu_proprio.profil(frame, users));
                                    PSEUDO = user.getIdentifiant();
                                }
                                if (PropOuLoc == COLOCATAIRE && user != getProprietaire(users)) {
                                    changerPanel(frame, Menu_colocataire.profil(frame, users));
                                    PSEUDO = user.getIdentifiant();
                                }
                            }
                        }
                    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                            | UnsupportedLookAndFeelException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                }
            });
                
                panelIdMdp.add(id, constraintsIdMdp);
                panelIdMdp.add(mdp, constraintsIdMdp);
                panelIdMdp.add(connexion, constraintsIdMdp);

            JButton retour = creerBouton("Retour");
            retour.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try {
                        changerPanel(frame, appli(frame, users));
                    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                            | UnsupportedLookAndFeelException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                }
            });


        setAnchorWeightY(constraintsIdentification, GridBagConstraints.CENTER, 0.25);
        identification.add(titre, constraintsIdentification);

        setAnchorWeightY(constraintsIdentification, GridBagConstraints.CENTER, 0.25);
        identification.add(personne, constraintsIdentification);           
        
        setAnchorWeightY(constraintsIdentification, GridBagConstraints.CENTER, 0.5);
        identification.add(panelIdMdp, constraintsIdentification);

        ajouterRetour(identification, retour, constraintsIdentification);

        resizeIdentification(frame, identification, titre, personne, labelId, labelMdp, tfId, tfMdp, connexion, retour);
    
        return identification;
    }

    protected static void resizeIdentification(JFrame frame, JPanel panel, JLabel label1, JLabel label2, JLabel label3, JLabel label4, JTextField tf1, JTextField tf2, JButton btn1, JButton retour) {
        panel.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                label1.setFont(fontTitre(frame));
                label2.setFont(fontAutre(frame));
                label3.setFont(fontAutre(frame));
                label4.setFont(fontAutre(frame));

                tf1.setPreferredSize(calculerDimBouton(frame));
                tf2.setPreferredSize(calculerDimBouton(frame));

                btn1.setPreferredSize(calculerDimBouton(frame));
                btn1.setFont(fontAutre(frame));

                retour.setPreferredSize(calculerDimRetour(frame));
                retour.setFont(fontAutre(frame));
            }
        });
    }

    public static JPanel publierAnnonce(JFrame frame,  int PropOuLoc, String pseudoClient, ArrayList<Personne> users) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
        UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        final Client client = new Client(pseudoClient);

        JPanel publierAnnonce = creerLePanel();
            GridBagConstraints constraintsPublierAnnonce = new GridBagConstraints();
            setGridXGridY(constraintsPublierAnnonce, 0, GridBagConstraints.RELATIVE);

            JLabel titre = creerLabel("Publier une annonce", COULEUR_TEXTE);       

            JPanel panelChoixTxt = creerUnPanelInterieur();
                GridBagConstraints constraintsChoixTxt = new GridBagConstraints();
                setGridXGridY(constraintsChoixTxt, 0, GridBagConstraints.RELATIVE);

                JComboBox<String> choixDestinataire = new JComboBox<>();
                choixDestinataire.setBackground(COULEUR_FOND_BOUTONS);
                choixDestinataire.addItem("Choisir destinataire");

                for (Personne user : users) {
                    choixDestinataire.addItem(user.getIdentifiant());
                }
                choixDestinataire.addItem("tous");
                
                JTextField txtAnnonce = new JTextField("Entrez votre message ici");

                JButton publier = creerBouton("Envoyer message");
                publier.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        client.envoyerMessage((String) choixDestinataire.getSelectedItem(), txtAnnonce.getText());
                        txtAnnonce.setText("Entrez votre message ici");
                    }
                });

                panelChoixTxt.add(choixDestinataire, constraintsChoixTxt);
                panelChoixTxt.add(txtAnnonce, constraintsChoixTxt);
                panelChoixTxt.add(publier, constraintsChoixTxt);

            JPanel panelReception = creerUnPanelInterieur();
                GridBagConstraints constraintsReception = new GridBagConstraints();
                // constraintsReception.insets = new Insets(30, 0, 30, 0);
                setGridXGridY(constraintsReception, 0, GridBagConstraints.RELATIVE);
                JLabel message = creerLabel("Messages reçus :", COULEUR_TEXTE);

                JTextArea txtMessage = new JTextArea();
                Thread recevoirMsg = new Thread(() -> {
                    try {
                        while (true) {
                           txtMessage.append(client.messageRecu()); 
                           txtMessage.append("\n");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                recevoirMsg.start();

                panelReception.add(message, constraintsReception);
                panelReception.add(txtMessage, constraintsReception);

            JButton retour = creerBouton("Retour");
            retour.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try {
                        if (PropOuLoc == PROPRIETAIRE) {
                            changerPanel(frame, Menu_proprio.profil(frame, users));
                        }
                        if (PropOuLoc == COLOCATAIRE) {
                            changerPanel(frame, Menu_colocataire.profil(frame, users));
                        }
                    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                            | UnsupportedLookAndFeelException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                }
            });


        setAnchorWeightY(constraintsPublierAnnonce, GridBagConstraints.CENTER, 0.25);
        publierAnnonce.add(titre, constraintsPublierAnnonce);

        publierAnnonce.add(panelChoixTxt, constraintsPublierAnnonce);
        publierAnnonce.add(panelReception, constraintsPublierAnnonce);

        ajouterRetour(publierAnnonce, retour, constraintsPublierAnnonce);

        resizePublierAnnonce(frame, publierAnnonce, titre, message, txtAnnonce, txtMessage, choixDestinataire, publier, retour);
    
        return publierAnnonce;
    }

    protected static void resizePublierAnnonce(JFrame frame, JPanel panel, JLabel label1, JLabel label2, JTextField textField1, JTextArea textArea1, JComboBox<String> combo1, JButton bouton1, JButton retour) {
        panel.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                label1.setFont(fontTitre(frame));

                label2.setFont(fontAutre(frame));

                // textField1.setPreferredSize(calculerTextArea(frame));
                textField1.setFont(fontAutre(frame));

                textArea1.setPreferredSize(calculerTextArea(frame));
                textArea1.setFont(fontAutre(frame));

                combo1.setPreferredSize(calculerDimBouton(frame));
                combo1.setFont(fontAutre(frame));
                
                bouton1.setPreferredSize(calculerDimBouton(frame));
                bouton1.setFont(fontAutre(frame));
                
                retour.setPreferredSize(calculerDimRetour(frame));
                retour.setFont(fontAutre(frame));
            }
        });
    }

    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
        Scanner scanner = new Scanner(System.in);

        Proprietaire proprio = new Proprietaire("Jean", "Michel");

        ArrayList<Personne> users = new ArrayList<>();
        users.add(proprio);

        Colocataire coloc1 = new Colocataire("Paul", "Méhaud");
        getProprietaire(users).ajouterColocataire(coloc1);

        Colocataire coloc2 = new Colocataire("Kenza", "Bennani");
        getProprietaire(users).ajouterColocataire(coloc2);

        Colocataire coloc3 = new Colocataire("Constance", "Bau");
        getProprietaire(users).ajouterColocataire(coloc3);

        Colocataire coloc4 = new Colocataire("Ines", "Silhadi");
        getProprietaire(users).ajouterColocataire(coloc4);
        
        users.addAll(getProprietaire(users).getListeLocataires());
        
        JFrame fenetre = new JFrame(NOMAPP);
        fenetre.setSize(WIDTH, HEIGHT);
        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fenetre.add(appli(fenetre, users));        
        
        fenetre.setVisible(true);
        scanner.close();
    }
}

