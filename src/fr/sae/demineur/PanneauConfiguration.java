package fr.sae.demineur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Panneau de configuration d'une nouvelle partie.
 * Permet de choisir les dimensions et le nombre de mines.
 *
 * @author Équipe SAE
 * @version 1.0
 */
public class PanneauConfiguration extends JPanel {

    private JTextField tfLargeur;
    private JTextField tfHauteur;
    private JTextField tfMines;

    /**
     * Construit le panneau de configuration.
     * 
     * @param fenetre la fenêtre principale
     */
    public PanneauConfiguration(FenetrePrincipale fenetre) {
        setLayout(new GridLayout(5, 2, 10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Largeur
        add(new JLabel("Largeur (4-30):"));
        tfLargeur = new JTextField("10", 5);
        add(tfLargeur);

        // Hauteur
        add(new JLabel("Hauteur (4-30):"));
        tfHauteur = new JTextField("10", 5);
        add(tfHauteur);

        // Mines
        add(new JLabel("Nombre de mines:"));
        tfMines = new JTextField("10", 5);
        add(tfMines);

        // Bouton Démarrer
        JButton boutonDemarrer = new JButton("Démarrer");
        boutonDemarrer.addActionListener(e -> {
            try {
                int largeur = Integer.parseInt(tfLargeur.getText());
                int hauteur = Integer.parseInt(tfHauteur.getText());
                int mines = Integer.parseInt(tfMines.getText());

                if (largeur < 4 || largeur > 30 || hauteur < 4 || hauteur > 30) {
                    throw new Exception("Dimensions invalides (4-30)");
                }
                if (mines < 1 || mines >= largeur * hauteur) {
                    throw new Exception("Nombre de mines invalide");
                }

                Partie partie = new Partie(largeur, hauteur, mines);
                fenetre.afficherJeu(partie);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erreur: " + ex.getMessage());
            }
        });
        add(boutonDemarrer);

        // Bouton Retour
        JButton boutonRetour = new JButton("Retour");
        boutonRetour.addActionListener(e -> fenetre.retournerAuMenu());
        add(boutonRetour);
    }
}
