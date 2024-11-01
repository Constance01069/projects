package ihm;

import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import personnes.Personne;
import graphes.Sortie;

public class Menu_colocataire extends Menus {
    public static JPanel profil(JFrame frame, ArrayList<Personne> users) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
        UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");

        JPanel lePanel = creerLePanel();
            GridBagConstraints constraints = new GridBagConstraints();

            JLabel titre = creerLabel("Mon profil", COULEUR_TEXTE);

            String[] nomBoutons = {"Tâches", "Sorties", "Publier annonce"};
            JButton[] listeBoutons = creerBoutons(nomBoutons);
            listeBoutons[0].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try {
                        changerPanel(frame, taches(frame, users));
                    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                            | UnsupportedLookAndFeelException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                }
            });
            listeBoutons[1].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try {
                        changerPanel(frame, sorties(frame, users));
                    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                            | UnsupportedLookAndFeelException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                }
            });
            listeBoutons[2].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try {
                        changerPanel(frame, publierAnnonce(frame, COLOCATAIRE, PSEUDO, users));
                    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                            | UnsupportedLookAndFeelException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                }
            });
            

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
            constraints.weightx = 0.5;
            constraints.gridwidth = 2;
            lePanel.add(titre, constraints);
            
            setAnchorWeightY(constraints, GridBagConstraints.CENTER, 0.5);
            ajouterListeBoutons(lePanel, listeBoutons, constraints);

            setGridXGridY(constraints, 1, GridBagConstraints.RELATIVE);

            setAnchorWeightY(constraints, GridBagConstraints.CENTER, 0.5);

            ajouterRetour(lePanel, deconnexion, constraints);

        resizeProfil(frame, lePanel, titre, listeBoutons, deconnexion);
    
        return lePanel;
    }

    protected static void resizeProfil(JFrame frame, JPanel panel, JLabel label1, JButton[] boutons, JButton retour) {
        panel.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                label1.setFont(fontTitre(frame));

                for (JButton bouton : boutons) {
                    bouton.setPreferredSize(calculerDimBouton(frame));
                    bouton.setFont(fontAutre(frame));
                }

                retour.setPreferredSize(calculerDimBouton(frame));
                retour.setFont(fontAutre(frame));
            }
        });
    }
    public static JPanel taches(JFrame frame, ArrayList<Personne> users) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
        UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        JPanel lePanel = creerLePanel();
    GridBagConstraints constraints = new GridBagConstraints();

    JLabel titre = creerLabel("Tâches", COULEUR_TEXTE);

    // Création du tableau
    String[][] data = {
        // {"Tâche 1", "Responsable 1", "F"},
        // {"Tâche 2", "Responsable 2", "NF"},
        // {"Tâche 3", "Responsable 3", "F"},
        {"Tâche 1", "F"},
        {"Tâche 2", "NF"},
        {"Tâche 3", "F"},
        // ... Ajoutez autant de lignes que nécessaire
    };

    // String[] columnNames = {"Tâches", "Responsable", "Etat (F/NF)"};
    String[] columnNames = {"Tâche", "Etat (F/NF)"};
    JTable tableau = new JTable(data, columnNames);

    JScrollPane scrollPane = new JScrollPane(tableau);
    tableau.setFillsViewportHeight(true);

    JButton retour = creerBouton("Retour");
    retour.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            try {
                changerPanel(frame, profil(frame, users));
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                    | UnsupportedLookAndFeelException e1) {
                e1.printStackTrace();
            }
        }
    });

    setGridXGridY(constraints, 0, GridBagConstraints.RELATIVE);

    setAnchorWeightY(constraints, GridBagConstraints.PAGE_START, 0.5);
    lePanel.add(titre, constraints);

    setAnchorWeightY(constraints, GridBagConstraints.CENTER, 1);
    lePanel.add(scrollPane, constraints);

    ajouterRetour(lePanel, retour, constraints);

    resizeTaches(frame, lePanel, titre, tableau, retour);

    return lePanel;
}

protected static void resizeTaches(JFrame frame, JPanel panel, JLabel label1, JTable tableau, JButton retour) {
    panel.addComponentListener(new ComponentAdapter() {
        public void componentResized(ComponentEvent e) {
            label1.setFont(fontTitre(frame));
            
            tableau.setPreferredSize(calculerTextArea(frame));

            retour.setPreferredSize(calculerDimRetour(frame));
            retour.setFont(fontAutre(frame));
        }
    });
}




    public static JPanel sorties(JFrame frame, ArrayList<Personne> users) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
        UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");

        JPanel lePanel = creerLePanel();
            GridBagConstraints constraints = new GridBagConstraints();

            JLabel titre = creerLabel("Sorties", COULEUR_TEXTE);

            String[] nomBoutons = {"Liste Bars", "Tournée des bars", "Plus courte soif"};
            JButton[] listeBoutons = creerBoutons(nomBoutons);
            listeBoutons[0].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try {
                        changerPanel(frame, listeBars(frame, users));
                    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                            | UnsupportedLookAndFeelException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                }
            });
            listeBoutons[1].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try {
                        changerPanel(frame, tourneeDesBars(frame, users));
                    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                            | UnsupportedLookAndFeelException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                }
            });
            listeBoutons[2].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try {
                        changerPanel(frame, plusCourtChemin(frame, users));
                    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                            | UnsupportedLookAndFeelException e1) {
                        // TODO Auto-generated catch block
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
            
            setAnchorWeightY(constraints, GridBagConstraints.CENTER, 0.15);
            ajouterListeBoutons(lePanel, listeBoutons, constraints);

            ajouterRetour(lePanel, retour, constraints);

        resizeSorties(frame, lePanel, titre, listeBoutons, retour);
    
        return lePanel;
    }

    protected static void resizeSorties(JFrame frame, JPanel panel, JLabel label1, JButton[] boutons, JButton retour) {
        panel.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                label1.setFont(fontTitre(frame));

                for (JButton bouton : boutons) {
                    bouton.setPreferredSize(calculerDimBouton(frame));
                    bouton.setFont(fontAutre(frame));
                }

                retour.setPreferredSize(calculerDimRetour(frame));
                retour.setFont(fontAutre(frame));
            }
        });
    }

    public static JPanel listeBars(JFrame frame, ArrayList<Personne> users) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
        UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");

        JPanel lePanel = creerLePanel();
            GridBagConstraints constraints = new GridBagConstraints();

            JLabel titre = creerLabel("Liste de bars", COULEUR_TEXTE);

            JTextArea bars = new JTextArea();
            for (String bar : LISTE_BARS) {
                bars.append("- " + bar + "\n");
            }
            JScrollPane scrollBars = new JScrollPane(bars);

            JButton retour = creerBouton("Retour");
            retour.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try {
                        changerPanel(frame, sorties(frame, users));
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
            
            setAnchorWeightY(constraints, GridBagConstraints.CENTER, 0.5);
            lePanel.add(scrollBars, constraints);

            ajouterRetour(lePanel, retour, constraints);

        resizeListeBars(frame, lePanel, titre, scrollBars, bars, retour);
    
        return lePanel;
    }

    protected static void resizeListeBars(JFrame frame, JPanel panel, JLabel label1, JScrollPane sp1, JTextArea ta1, JButton retour) {
        panel.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                label1.setFont(fontTitre(frame));

                sp1.setPreferredSize(calculerTextArea(frame));
                ta1.setFont(fontAutre(frame));

                retour.setPreferredSize(calculerDimRetour(frame));
                retour.setFont(fontAutre(frame));
            }
        });
    }

    public static JPanel tourneeDesBars(JFrame frame, ArrayList<Personne> users) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
        UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");

        JPanel lePanel = creerLePanel();
            GridBagConstraints constraints = new GridBagConstraints();

            JLabel titre = creerLabel("Tournée des bars", COULEUR_TEXTE);

            JCheckBox[] bars = new JCheckBox[LISTE_BARS.length];
            for (int i = 0 ; i < LISTE_BARS.length ; i++) {
                bars[i] = new JCheckBox(LISTE_BARS[i]);
            }

            JButton valider = creerBouton("Valider");
            valider.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try {
                        changerPanel(frame, resultatBarathon(frame, users, Sortie.barathonDepuisBar(selectionCheckBox(bars).get(0))));
                    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                            | UnsupportedLookAndFeelException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                }
            });

            JButton retour = creerBouton("Retour");
            retour.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try {
                        changerPanel(frame, sorties(frame, users));
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

            setAnchorWeightY(constraints, GridBagConstraints.CENTER, 0.1);            
            for (JCheckBox bar : bars) {
                lePanel.add(bar, constraints);
            }

            lePanel.add(valider, constraints);

            ajouterRetour(lePanel, retour, constraints);

        resizeTourneeDesBars(frame, lePanel, titre, bars, valider, retour);
    
        return lePanel;
    }

    protected static void resizeTourneeDesBars(JFrame frame, JPanel panel, JLabel label1, JCheckBox[] ListeCb, JButton bouton1, JButton retour) {
        panel.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                label1.setFont(fontTitre(frame));

            for (JCheckBox cb : ListeCb) {
                cb.setFont(fontAutre(frame));
            }

            bouton1.setPreferredSize(calculerDimBouton(frame));
            bouton1.setFont(fontAutre(frame));

            retour.setPreferredSize(calculerDimRetour(frame));
            retour.setFont(fontAutre(frame));
            }
        });
    }

    public static JPanel plusCourtChemin(JFrame frame, ArrayList<Personne> users) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
        UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");

        JPanel lePanel = creerLePanel();
            GridBagConstraints constraints = new GridBagConstraints();

            JLabel titre = creerLabel("Plus courte soif", COULEUR_TEXTE);

            JCheckBox[] bars = new JCheckBox[LISTE_BARS.length];
            for (int i = 0 ; i < LISTE_BARS.length ; i++) {
                bars[i] = new JCheckBox(LISTE_BARS[i]);
            }

            JButton valider = creerBouton("Valider");
            valider.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try {
                        changerPanel(frame, resultatBarathon(frame, users, Sortie.plusCourtCheminEntre2Bars(selectionCheckBox(bars).get(0), selectionCheckBox(bars).get(1))));
                    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                            | UnsupportedLookAndFeelException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                }
            });

            JButton retour = creerBouton("Retour");
            retour.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try {
                        changerPanel(frame, sorties(frame, users));
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

            setAnchorWeightY(constraints, GridBagConstraints.CENTER, 0.1);            
            for (JCheckBox bar : bars) {
                lePanel.add(bar, constraints);
            }

            lePanel.add(valider, constraints);

            ajouterRetour(lePanel, retour, constraints);

        resizePlusCourtChemin(frame, lePanel, titre, bars, valider, retour);
    
        return lePanel;
    }

    protected static void resizePlusCourtChemin(JFrame frame, JPanel panel, JLabel label1, JCheckBox[] ListeCb, JButton bouton1, JButton retour) {
        panel.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                label1.setFont(fontTitre(frame));

            for (JCheckBox cb : ListeCb) {
                cb.setFont(fontAutre(frame));
            }

            bouton1.setPreferredSize(calculerDimBouton(frame));
            bouton1.setFont(fontAutre(frame));

            retour.setPreferredSize(calculerDimRetour(frame));
            retour.setFont(fontAutre(frame));
            }
        });
    }

    public static JPanel resultatBarathon(JFrame frame, ArrayList<Personne> users, ArrayList<String> listeBars) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
        UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");

        JPanel lePanel = creerLePanel();
            GridBagConstraints constraints = new GridBagConstraints();

            JLabel titre = creerLabel("Liste de bars à visiter dans l'ordre", COULEUR_TEXTE);

            JTextArea bars = new JTextArea();
            for (int i = 0 ; i < listeBars.size() ; i++) {
                bars.append(i + ". " + listeBars.get(i) + "\n");
            }
            JScrollPane scrollBars = new JScrollPane(bars);

            JButton retour = creerBouton("Retour");
            retour.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try {
                        changerPanel(frame, sorties(frame, users));
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
            
            setAnchorWeightY(constraints, GridBagConstraints.CENTER, 0.5);
            lePanel.add(scrollBars, constraints);

            ajouterRetour(lePanel, retour, constraints);

        resizeResultatBarathon(frame, lePanel, titre, scrollBars, bars, retour);
    
        return lePanel;
    }

    protected static void resizeResultatBarathon(JFrame frame, JPanel panel, JLabel label1, JScrollPane sp1, JTextArea ta1, JButton retour) {
        panel.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                label1.setFont(fontTitre(frame));

                sp1.setPreferredSize(calculerTextArea(frame));
                ta1.setFont(fontAutre(frame));

                retour.setPreferredSize(calculerDimRetour(frame));
                retour.setFont(fontAutre(frame));
            }
        });
    }
}
