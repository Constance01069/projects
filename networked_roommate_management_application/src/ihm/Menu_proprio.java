package ihm;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import personnes.Colocataire;
import personnes.Personne;

public class Menu_proprio extends Menus {
    public static JPanel profil(JFrame frame, ArrayList<Personne> users) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
        UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
       
        JPanel lePanel = creerLePanel();
            GridBagConstraints constraints = new GridBagConstraints();
            
            JLabel titre = creerLabel("Mon Profil", COULEUR_TITRE_RETOUR);
            
            String[] nomBoutons = {"Suivi paiements", "Publier annonce"};
            JButton[] listeBoutons = creerBoutons(nomBoutons);
            listeBoutons[0].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try {
                        changerPanel(frame, suivipaiements(frame, users));
                    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                            | UnsupportedLookAndFeelException e1) {
                        e1.printStackTrace();
                    }
                }
            });
            listeBoutons[1].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try {
                        changerPanel(frame, publierAnnonce(frame, PROPRIETAIRE, PSEUDO, users));
                    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                            | UnsupportedLookAndFeelException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                }
            });

            JLabel nom = creerLabel("Nom : " + getProprietaire(users).getNom(), COULEUR_TEXTE); 
            JLabel prenom = creerLabel("Prenom : " + getProprietaire(users).getPrenom(), COULEUR_TEXTE);

            // JButton publierAnnonce = creerBouton("Publier annonce");

            JButton deconnexion = creerBouton("Déconnexion");
            deconnexion.addActionListener(new ActionListener() {
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



            setGridXGridY(constraints, 0, GridBagConstraints.RELATIVE);

            setAnchorWeightY(constraints, GridBagConstraints.PAGE_START, 0.5);
            // constraints.weightx = 0.5;
            // constraints.gridwidth = 2;
            lePanel.add(titre, constraints);

            setGridXGridY(constraints, 0, GridBagConstraints.RELATIVE);
            setAnchorWeightY(constraints, GridBagConstraints.CENTER, 0.5);
            constraints.weightx = 0;
            lePanel.add(nom,constraints);
            lePanel.add(prenom, constraints);

            ajouterListeBoutons(lePanel, listeBoutons, constraints);

            // setGridXGridY(constraints, 1, GridBagConstraints.RELATIVE);

            // setAnchorWeightY(constraints, GridBagConstraints.CENTER, 0.5);

            ajouterRetour(lePanel, deconnexion, constraints);

        resizeProfil(frame, lePanel, titre, nom, prenom, listeBoutons, deconnexion);
    
        return lePanel;
    }

    protected static void resizeProfil(JFrame frame, JPanel panel, JLabel label1, JLabel label2, JLabel label3, JButton[] boutons, JButton retour) {
        panel.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                label1.setFont(fontTitre(frame));
                label2.setFont(fontAutre(frame));
                label3.setFont(fontAutre(frame));

                for (JButton bouton : boutons) {
                    bouton.setPreferredSize(calculerDimBouton(frame));
                    bouton.setFont(fontAutre(frame));
                }
            
                retour.setPreferredSize(calculerDimBouton(frame));
                retour.setFont(fontAutre(frame));
            }
        });
    }

    public static JPanel suivipaiements(JFrame frame, ArrayList<Personne> users) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
        UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");

        JPanel lePanel = creerLePanel();
            GridBagConstraints constraints = new GridBagConstraints();

            JLabel titre = creerLabel("Suivi des paiements", COULEUR_TEXTE);


        JPanel panelChoixTxt = creerUnPanelInterieur();
            GridBagConstraints constraintsChoixTxt = new GridBagConstraints();
            constraintsChoixTxt.insets = new Insets(30, 0, 30, 0);
            setGridXGridY(constraintsChoixTxt, 0, GridBagConstraints.RELATIVE);
            
            JComboBox<String> choixColoc = new JComboBox<>();
                choixColoc.addItem("Choisir le locataire");

                // Ajout des options spécifiques pour chaque colocataire
                for (Colocataire coloc : getProprietaire(users).getListeLocataires()) {
                    choixColoc.addItem(coloc.getPrenom() + " " + coloc.getNom());
                }

            panelChoixTxt.add(choixColoc, constraintsChoixTxt);

            String[] nomBoutons = {"Ajouter ", "Supprimer", "Valider"};
            JButton[] listeBoutons = creerBoutons(nomBoutons);

            listeBoutons[0].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try {
                        changerPanel(frame, ajouterLocataire(frame, users));
                    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                            | UnsupportedLookAndFeelException e1) {
                        e1.printStackTrace();
                    }
                }
            });
            listeBoutons[1].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try {
                        changerPanel(frame, supprimerLocataire(frame, users));
                    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                            | UnsupportedLookAndFeelException e1) {
                        e1.printStackTrace();
                    }
                }
            });

            listeBoutons[2].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try {
                        // Récupérez le locataire sélectionné
                        // String locataireSelectionne = (String) choixColoc.getSelectedItem();
            
                        // Divisez le nom et prénom du locataire
                        // String[] nomPrenom = locataireSelectionne.split(" ");
                        // String nomLocataire = nomPrenom[0];
                        // String prenomLocataire = nomPrenom[1];
            
                        // Changez le panel vers la page "Locataire" avec les informations nécessaires
                        changerPanel(frame, locataire(frame, users));
                    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                            | UnsupportedLookAndFeelException e1) {
                        e1.printStackTrace();
                    }
                }
            });
            

            JButton retour = creerBouton("Retour");
            retour.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try {
                        changerPanel(frame, profil(frame, users));
                    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                            | UnsupportedLookAndFeelException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                }
            });

            setGridXGridY(constraints, 0, GridBagConstraints.RELATIVE);

            setAnchorWeightY(constraints, GridBagConstraints.CENTER, 0.5);
            lePanel.add(titre, constraints);

            lePanel.add(panelChoixTxt, constraints);  

            
            setAnchorWeightY(constraints, GridBagConstraints.CENTER, 0.15);
            ajouterListeBoutons(lePanel, listeBoutons, constraints);

            ajouterRetour(lePanel, retour, constraints);

             
            resizePaiement(frame, lePanel, titre, choixColoc, listeBoutons, retour);
    
        return lePanel;
    }


       protected static void resizePaiement(JFrame frame, JPanel panel, JLabel label1, JComboBox<String> comb1, JButton[] boutons, JButton retour) {
        panel.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                label1.setFont(fontTitre(frame));

                comb1.setPreferredSize(calculerDimBouton(frame));
                comb1.setFont(fontAutre(frame));

                for (JButton bouton : boutons) {
                    bouton.setPreferredSize(calculerDimBouton(frame));
                    bouton.setFont(fontAutre(frame));
                }

                retour.setPreferredSize(calculerDimRetour(frame));
                retour.setFont(fontAutre(frame));
            }
        });
    }

    public static JPanel ajouterLocataire(JFrame frame, ArrayList<Personne> users) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
        UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");

        JPanel lePanel = creerLePanel();
        GridBagConstraints constraints = new GridBagConstraints();

        JLabel titre = creerLabel("Ajouter Locataire", COULEUR_TEXTE);

        JPanel panelSaisie = creerUnPanelInterieur();
        GridBagConstraints constraintsSaisie = new GridBagConstraints();
        constraintsSaisie.insets = new Insets(30, 0, 30, 0);
        setGridXGridY(constraintsSaisie, 0, GridBagConstraints.RELATIVE);

        JLabel labelNom = creerLabel("Nom :", Color.BLACK);
        JTextField tfNom = new JTextField();

        JLabel labelPrenom = creerLabel("Prénom :", Color.BLACK);
        JTextField tfPrenom = new JTextField();

        panelSaisie.add(labelNom, constraintsSaisie);
        panelSaisie.add(tfNom, constraintsSaisie);
        panelSaisie.add(labelPrenom, constraintsSaisie);
        panelSaisie.add(tfPrenom, constraintsSaisie);

        JButton valider = creerBouton("Valider");
        JButton retour = creerBouton("Retour");

        retour.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    changerPanel(frame, suivipaiements(frame, users));
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                        | UnsupportedLookAndFeelException e1) {
                    e1.printStackTrace();
                }
            }
        });

        valider.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nom = tfNom.getText();
                String prenom = tfPrenom.getText();
                Colocataire nouveauColocataire = new Colocataire(nom, prenom);
                getProprietaire(users).ajouterColocataire(nouveauColocataire);
                // users.addAll(getProprietaire(users).getListeLocataires());
            }
        });
        

        setGridXGridY(constraints, 0, GridBagConstraints.RELATIVE);

        setAnchorWeightY(constraints, GridBagConstraints.CENTER, 0.5);
        lePanel.add(titre, constraints);

        lePanel.add(panelSaisie, constraints);

        setAnchorWeightY(constraints, GridBagConstraints.CENTER, 0.15);
        lePanel.add(valider, constraints);
        lePanel.add(retour, constraints);

        resizeAjouterLocataire(frame, lePanel, titre, labelNom, tfNom, labelPrenom, tfPrenom, valider, retour);

        return lePanel;
    }

    protected static void resizeAjouterLocataire(JFrame frame, JPanel panel, JLabel label1, JLabel label2, JTextField tf1, JLabel label3, JTextField tf2, JButton bouton1, JButton bouton2) {
        panel.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                label1.setFont(fontTitre(frame));
                label2.setFont(fontAutre(frame));
                label3.setFont(fontAutre(frame));

                tf1.setPreferredSize(calculerDimBouton(frame));
                tf2.setPreferredSize(calculerDimBouton(frame));

                bouton1.setPreferredSize(calculerDimBouton(frame));
                bouton1.setFont(fontAutre(frame));

                bouton2.setPreferredSize(calculerDimBouton(frame));
                bouton2.setFont(fontAutre(frame));
            }
        });
    }

    public static JPanel supprimerLocataire(JFrame frame, ArrayList<Personne> users) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
        UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");

        JPanel lePanel = creerLePanel();
        GridBagConstraints constraints = new GridBagConstraints();

        JLabel titre = creerLabel("Supprimer Locataire", COULEUR_TEXTE);

        JPanel panelChoixTxt = creerUnPanelInterieur();
        GridBagConstraints constraintsChoixTxt = new GridBagConstraints();
        constraintsChoixTxt.insets = new Insets(30, 0, 30, 0);
        setGridXGridY(constraintsChoixTxt, 0, GridBagConstraints.RELATIVE);

        JComboBox<String> choixLocataire = new JComboBox<>();
        choixLocataire.addItem("Choisir le locataire");
        choixLocataire.setToolTipText("Locataire à supprimer");

        // Récupérer la liste des colocataires du propriétaire
        ArrayList<Colocataire> listeColocataires = getProprietaire(users).getListeLocataires();

        // Ajout des noms des colocataires à la liste déroulante
        for (Colocataire colocataire : listeColocataires) {
            choixLocataire.addItem(colocataire.getNom() + " " + colocataire.getPrenom());
        }

        panelChoixTxt.add(choixLocataire, constraintsChoixTxt);

        JButton valider = creerBouton("Valider");
        JButton retour = creerBouton("Retour");
        retour.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    changerPanel(frame, suivipaiements(frame, users));
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                        | UnsupportedLookAndFeelException e1) {
                    e1.printStackTrace();
                }
            }
        });

        valider.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Récupérer l'option sélectionnée dans le JComboBox
                String choixSelectionne = (String) choixLocataire.getSelectedItem();

                // Diviser le nom et prénom du locataire
                String[] nomPrenom = choixSelectionne.split(" ");
                String nomLocataire = nomPrenom[0];
                String prenomLocataire = nomPrenom[1];
                Colocataire colocataireASupprimer = trouverColocataireParNomPrenom(nomLocataire, prenomLocataire, listeColocataires);
            
                getProprietaire(users).supprimerColocataire(colocataireASupprimer);
                // users.addAll(getProprietaire(users).getListeLocataires());
            }
    });

        setGridXGridY(constraints, 0, GridBagConstraints.RELATIVE);

        setAnchorWeightY(constraints, GridBagConstraints.CENTER, 0.5);
        lePanel.add(titre, constraints);

        lePanel.add(panelChoixTxt, constraints);

        setAnchorWeightY(constraints, GridBagConstraints.CENTER, 0.15);
        lePanel.add(valider, constraints);
        lePanel.add(retour, constraints);

        resizeSupprimerLocataire(frame, lePanel, titre, choixLocataire, valider, retour);

        return lePanel;
    }



    protected static void resizeSupprimerLocataire(JFrame frame, JPanel panel, JLabel label1, JComboBox<String> combo1, JButton bouton1, JButton bouton2) {
        panel.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                label1.setFont(fontTitre(frame));

                combo1.setPreferredSize(calculerDimBouton(frame));
                combo1.setFont(fontAutre(frame));

                bouton1.setPreferredSize(calculerDimBouton(frame));
                bouton1.setFont(fontAutre(frame));

                bouton2.setPreferredSize(calculerDimBouton(frame));
                bouton2.setFont(fontAutre(frame));
            }
        });
    }

    // Méthode pour trouver un colocataire dans la liste par son nom complet
    private static Colocataire trouverColocataireParNomPrenom(String nom, String prenom, ArrayList<Colocataire> listeLocataires ) {
        for (Colocataire colocataire : listeLocataires) {
            if (colocataire.getNom().equals(nom) && colocataire.getPrenom().equals(prenom)) {
                return colocataire;
            }
        }
        return null; // Si aucun colocataire correspondant n'est trouvé
    }

    public static JPanel locataire(JFrame frame, ArrayList<Personne> users) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
        UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");

        JPanel lePanel = creerLePanel();
        GridBagConstraints constraints = new GridBagConstraints();

        JLabel titre = creerLabel("Locataire", COULEUR_TEXTE);

        // Créez le tableau 3x3 avec le titre "Résumé des paiements"
        String[] entetes = {"Mois", "Montant dû", "Etat P/N"};
        String[][] donnees = {
                {"Janvier", "", ""},
                {"Février", "", ""},
                {"Mars", "", ""},
        };
        JTable tableauPaiements = new JTable(donnees, entetes);
        JScrollPane scrollPane = new JScrollPane(tableauPaiements);


        JButton enregistrerLoyer = creerBouton("Enregistrer loyer");
        // enregistrerLoyer.addActionListener(new ActionListener() {
        //     public void actionPerformed(ActionEvent e) {
        //         try {
        //             changerPanel(frame, enregistrerLoyer(frame));
        //         } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
        //                 | UnsupportedLookAndFeelException e1) {
        //             e1.printStackTrace();
        //         }
        //     }
        // });
        JButton retour = creerBouton("Retour");
        retour.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    changerPanel(frame, suivipaiements(frame, users));
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                        | UnsupportedLookAndFeelException e1) {
                    e1.printStackTrace();
                }
            }
        });

        setGridXGridY(constraints, 0, GridBagConstraints.RELATIVE);

        setAnchorWeightY(constraints, GridBagConstraints.CENTER, 0.5);
        lePanel.add(titre, constraints);

        setAnchorWeightY(constraints, GridBagConstraints.CENTER, 0.5);
        lePanel.add(scrollPane, constraints);

        
        lePanel.add(enregistrerLoyer, constraints);
        lePanel.add(retour,constraints);

        resizeLocataire(frame, lePanel, titre, tableauPaiements, enregistrerLoyer, retour);

        return lePanel;
    }

    protected static void resizeLocataire(JFrame frame, JPanel panel, JLabel label1, JTable tableau, JButton enregistrerLoyer, JButton retour) {
        panel.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                label1.setFont(fontTitre(frame));

                // Ajoutez le redimensionnement nécessaire pour le tableau et le bouton, si nécessaire
                int newWidth = frame.getWidth();  // Obtenez la nouvelle largeur de la fenêtre
                int newHeight = frame.getHeight();  // Obtenez la nouvelle hauteur de la fenêtre

                // Redimensionnez le tableau en fonction des nouvelles dimensions de la fenêtre
                int tableauWidth = (int) (newWidth * 0.9);  // Ajustez selon vos besoins
                int tableauHeight = (int) (newHeight * 0.7);  // Ajustez selon vos besoins
                tableau.setPreferredSize(new Dimension(tableauWidth, tableauHeight));

                enregistrerLoyer.setPreferredSize(calculerDimBouton(frame));
                enregistrerLoyer.setFont(fontAutre(frame));

                retour.setPreferredSize(calculerDimRetour(frame));
                retour.setFont(fontAutre(frame));

            }
        });
    }
}
