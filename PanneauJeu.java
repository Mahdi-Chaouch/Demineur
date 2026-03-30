import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Panneau du jeu affichant la grille cliquable.
 *
 * @author Équipe SAE
 * @version 1.0
 */
public class PanneauJeu extends JPanel {

    private FenetrePrincipale fenetre;
    private Partie partie;
    private BoutonCase[][] grille;
    private JLabel labelMines;
    private JLabel labelTemps;
    private Timer chrono;
    private int secondes;

    /**
     * Construit le panneau du jeu.
     *
     * @param fenetre la fenêtre principale
     * @param partie la partie à jouer
     */
    public PanneauJeu(FenetrePrincipale fenetre, Partie partie) {
        this.fenetre = fenetre;
        this.partie = partie;
        this.secondes = 0;

        setLayout(new BorderLayout(5, 5));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Barre du haut : mines restantes + chronomètre
        Plateau p = partie.getPlateau();
        JPanel panneauHaut = new JPanel(new BorderLayout());
        labelMines = new JLabel("Mines restantes: " + p.getNbMines());
        labelTemps = new JLabel("Temps: 0s");
        labelTemps.setHorizontalAlignment(SwingConstants.RIGHT);
        panneauHaut.add(labelMines, BorderLayout.WEST);
        panneauHaut.add(labelTemps, BorderLayout.EAST);
        add(panneauHaut, BorderLayout.NORTH);

        // Grille
        JPanel panneauGrille = new JPanel();
        panneauGrille.setLayout(new GridLayout(p.getHauteur(), p.getLargeur(), 2, 2));

        grille = new BoutonCase[p.getHauteur()][p.getLargeur()];

        for (int l = 0; l < p.getHauteur(); l++) {
            for (int c = 0; c < p.getLargeur(); c++) {
                Case caseModele = p.getCase(l, c);
                BoutonCase btn = new BoutonCase(caseModele, l, c);

                final int ligne = l, colonne = c;

                btn.addMouseListener(new MouseAdapter() {
                    public void mousePressed(MouseEvent e) {
                        if (SwingUtilities.isLeftMouseButton(e)) {
                            partie.reveler(ligne, colonne);
                        } else if (SwingUtilities.isRightMouseButton(e)) {
                            partie.marquer(ligne, colonne);
                        }

                        if (partie.getEtat() != EtatPartie.EN_COURS) {
                            arreterChrono();
                            fenetre.afficherFinPartie(partie);
                        } else {
                            rafraichir();
                        }
                    }
                });

                grille[l][c] = btn;
                panneauGrille.add(btn);
            }
        }

        add(panneauGrille, BorderLayout.CENTER);

        // Boutons bas
        JPanel panneauBas = new JPanel();

        JButton boutonSauvegarder = new JButton("Sauvegarder");
        boutonSauvegarder.addActionListener(e -> {
            try {
                Sauvegarde.sauvegarder(partie);
                JOptionPane.showMessageDialog(this, "Partie sauvegardée.", "Sauvegarde", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erreur lors de la sauvegarde.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });
        panneauBas.add(boutonSauvegarder);

        JButton boutonQuitter = new JButton("Quitter");
        boutonQuitter.addActionListener(e -> {
            arreterChrono();
            fenetre.retournerAuMenu();
        });
        panneauBas.add(boutonQuitter);

        add(panneauBas, BorderLayout.SOUTH);

        // Démarrage du chronomètre
        chrono = new Timer(1000, e -> {
            secondes++;
            labelTemps.setText("Temps: " + secondes + "s");
        });
        chrono.start();
    }

    /**
     * Arrête le chronomètre.
     */
    private void arreterChrono() {
        if (chrono != null && chrono.isRunning()) {
            chrono.stop();
        }
    }

    /**
     * Retourne le temps écoulé en secondes.
     *
     * @return secondes écoulées
     */
    public int getSecondes() {
        return secondes;
    }

    /**
     * Rafraîchit l'affichage.
     */
    public void rafraichir() {
        Plateau p = partie.getPlateau();
        for (int l = 0; l < p.getHauteur(); l++) {
            for (int c = 0; c < p.getLargeur(); c++) {
                grille[l][c].mettrAJourAffichage();
            }
        }

        int minesRestantes = p.getNbMines() - p.getNbMarqueursMine();
        labelMines.setText("Mines restantes: " + minesRestantes);
        repaint();
    }
}
