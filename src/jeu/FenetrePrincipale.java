import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Fenêtre principale de l'application Démineur.
 * Gère l'affichage des différents panneaux.
 *
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
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        addWindowListener(new EcouteurFenetre());
        afficherPanneau(new PanneauMenu(this));
    }

    private class EcouteurFenetre extends WindowAdapter {
        @Override
        public void windowClosing(WindowEvent e) {
            sauvegarderSiEnJeu();
            System.exit(0);
        }
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
     */
    public void afficherFinPartie(Partie partie) {
        afficherPanneau(new PanneauFin(this, partie));
    }

    /**
     * Rafraîchit le jeu en cours (non utilisé ici).
     */
    public void rafraichirJeu() {
        if (panneauActuel instanceof PanneauJeu) {
            ((PanneauJeu) panneauActuel).rafraichir();
        }
    }

    /**
     * Effectue la sauvegarde de la partie si un jeu est en cours.
     */
    public void sauvegarderSiEnJeu() {
        if (panneauActuel instanceof PanneauJeu) {
            ((PanneauJeu) panneauActuel).sauvegarderPartie();
        }
    }
}
