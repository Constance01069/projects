package ihm;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import personnes.Personne;
import personnes.Proprietaire;
import graphes.Sortie;

public class Methodes_menus {
    public static String NOMAPP = "Coloc'APP";
    public static int WIDTH = 1920;
    public static int HEIGHT = 1080;
    public static int PROPRIETAIRE = 0;
    public static int COLOCATAIRE = 1;
    public static Color COULEUR_FOND = new Color(240, 245, 245);
    public static Color COULEUR_TITRE_RETOUR = new Color(251, 145, 243);
    public static Color COULEUR_FOND_BOUTONS = new Color(100, 113, 255);
    public static Color COULEUR_TEXTE = new Color(120, 113, 255);
    public static String PSEUDO = "";
    protected static String[] LISTE_BARS = Sortie.listeBars();

    protected static JButton creerBouton(String nomBouton) {
        JButton bouton = new JButton(nomBouton);
        if ((nomBouton == "Retour") || (nomBouton == "Déconnexion")) {
            bouton.setBackground(COULEUR_TITRE_RETOUR);
        }
        else {
            bouton.setBackground(COULEUR_FOND_BOUTONS);
        }

        return bouton;
    }

    protected static JButton creerBouton(Icon iconBouton) {
        JButton bouton = new JButton(iconBouton);
        bouton.setBackground(Color.WHITE);

        return bouton;
    }

    protected static JButton[] creerBoutons(String[] nomBoutons) {
        JButton[] boutons = new JButton[nomBoutons.length];

        for (int i = 0; i < nomBoutons.length; i++) {
            boutons[i] = creerBouton(nomBoutons[i]);
        }

        return boutons;
    }

    protected static JButton[] creerBoutons(Icon[] iconsBoutons) {
        JButton[] boutons = new JButton[iconsBoutons.length];

        for (int i = 0; i < iconsBoutons.length; i++) {
            boutons[i] = creerBouton(iconsBoutons[i]);
        }

        return boutons;
    }

    protected static JLabel creerLabel(String txt, Color couleurTexte) {
        JLabel label = new JLabel(txt);
        label.setForeground(couleurTexte);
        
        return label;
    }

    protected static JPanel creerLePanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(COULEUR_FOND);

        return panel;
    }

    protected static JPanel creerUnPanelInterieur() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);

        return panel;
    }

    protected static JPanel creerUnPanelInterieur(Color couleur) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(true);
        panel.setBackground(couleur);

        return panel;
    }

    public static Dimension calculerDimFrame(JFrame frame) {
        Dimension dim = new Dimension(frame.getWidth()/2, frame.getHeight()/2);

        return dim;
    }

    protected static Dimension calculerDimBouton(JFrame frame) {
        Dimension dim = new Dimension(frame.getWidth()/4, frame.getHeight()/15);
        
        return dim;
    }

    protected static Dimension calculerDimRetour(JFrame frame) {
        Dimension dim = new Dimension(calculerDimBouton(frame).width/2, calculerDimBouton(frame).height);
        
        return dim;
    }

    protected static Dimension calculerTextArea(JFrame frame) {
        Dimension dim = new Dimension(calculerDimBouton(frame).width*2, calculerDimBouton(frame).height*5);
        
        return dim;
    }
    protected static Dimension calculerTextPane(JFrame frame) {
        Dimension dim = new Dimension(calculerDimBouton(frame).width/2, calculerDimBouton(frame).height*5);
        
        return dim;
    }

    protected static Font fontTitre(JFrame frame) {
        int taille = (frame.getWidth()/2 + frame.getHeight())/25;
        Font font = new Font("Serif", Font.BOLD, taille);

        return font;
    }

    protected static Font fontAutre(JFrame frame) {
        int taille = (frame.getWidth()/2 + frame.getHeight())/50;
        Font font = new Font("Serif", Font.BOLD, taille);

        return font;
    }

    protected static void setAnchorWeightY(GridBagConstraints gbc, int anchor, double weighty) {
        gbc.anchor = anchor;
        gbc.weighty = weighty;
    }

    protected static void setGridXGridY(GridBagConstraints gbc, int gridx, int gridy) {
        gbc.gridx = gridx;
        gbc.gridy = gridy;
    }

    protected static void ajouterListeBoutons(JPanel panel, JButton[] boutons, GridBagConstraints gbc) {
        for (JButton bouton : boutons) {
            panel.add(bouton, gbc);
        }
    }

    protected static void ajouterRetour(JPanel panel, JButton retour, GridBagConstraints gbc) {
        setAnchorWeightY(gbc, GridBagConstraints.LAST_LINE_END, 0);
        gbc.insets = new Insets(30, 30, 30, 30);
        gbc.weightx = 1;
        panel.add(retour, gbc);
    }

    protected static void centrerTxt(JTextPane txtPane) { // pour centrer le texte dans un JTextPane
        StyledDocument doc = txtPane.getStyledDocument();   
        MutableAttributeSet center = new SimpleAttributeSet();     
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, 0, center, false);
    }

    protected static void changerPanel(JFrame frame, JPanel panel) {
        frame.getContentPane().removeAll();
        frame.getContentPane().add(panel);
        frame.revalidate();
        frame.repaint();
    }

    protected static ArrayList<String> selectionCheckBox(JCheckBox[] cb) {
        ArrayList<String> selection = new ArrayList<>();
        for(JCheckBox box : cb) {
            if (box.isSelected()) {
                selection.add(box.getText());
            }
        }

        return selection;
    }

    protected static Proprietaire getProprietaire(ArrayList<Personne> users) {
        return (Proprietaire) users.get(0);
    }

    // protected static void ajouterColocs(ArrayList<Personne> users) {
    //     // Thread ajouterColoc = new Thread(() -> {
    //     //     try {
    //     //         while (true) {
    //     //             users.addAll(getProprietaire(users).getListeLocataires());
    //     //         }
    //     //     } catch (Exception e) {
    //     //         e.printStackTrace();
    //     //     }
    //     // });
    //     // ajouterColoc.start();
    //     users.removeAll(users);
    //     users.add(getProprietaire(users));
    //     users.addAll(getProprietaire(users).getListeLocataires());
    // }
}