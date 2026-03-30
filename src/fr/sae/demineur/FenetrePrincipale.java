package fr.sae.demineur;

import javax.swing.*;
import java.awt.*;

/**
 * Fenêtre principale de l'application Démineur.
 * Gère l'affichage des différents panneaux.
 *
 * @author Équipe SAE
 * @version 1.0
 */
public class FenetrePrincipale extends JFrame {

    private JPanel panneauActuel;

    /**
     * Construit la fenêtre principale.
     */
    public FenetrePrincipale() {
        setTitle("Démineur");
        setSize(500, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        afficherPanneau(new PanneauMenu(this));
    }

    /**
     * Affiche un panneau.
     *
     * @param nouveau le panneau à afficher
     */
    public void afficherPanneau(JPanel nouveau) {
        if (panneauActuel != null) {
            getContentPane().remove(panneauActuel);
        }
        panneauActuel = nouveau;
        getContentPane().add(panneauActuel, BorderLayout.CENTER);
        getContentPane().revalidate();
        getContentPane().repaint();
    }

    /**
     * Retour au menu.
     */
    public void retournerAuMenu() {
        afficherPanneau(new PanneauMenu(this));
    }

    /**
     * Affiche l'écran de configuration.
     */
    public void afficherConfiguration() {
        afficherPanneau(new PanneauConfiguration(this));
    }

    /**
     * Affiche le jeu.
     *
     * @param partie la partie à jouer
     */
    public void afficherJeu(Partie partie) {
        afficherPanneau(new PanneauJeu(this, partie));
    }

    /**
     * Affiche la fin de partie.
     *
     * @param partie la partie terminée
     * @param temps  le temps écoulé en secondes
     */
    public void afficherFinPartie(Partie partie, int temps) {
        afficherPanneau(new PanneauFin(this, partie, temps));
    }

    /**
     * Rafraîchit le jeu en cours (non utilisé ici).
     */
    public void rafraichirJeu() {
        if (panneauActuel instanceof PanneauJeu) {
            ((PanneauJeu) panneauActuel).rafraichir();
        }
    }
}

