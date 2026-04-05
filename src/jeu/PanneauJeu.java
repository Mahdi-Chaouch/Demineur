import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Panneau du jeu affichant la grille cliquable.
 *
 
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
        panneauHaut.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        
        labelMines = new JLabel("Mines restantes: " + p.getNbMines());
        labelMines.setFont(new Font("Segoe UI", Font.BOLD, 16));
        labelMines.setForeground(new Color(255, 150, 150));
        
        labelTemps = new JLabel("Temps: 0s");
        labelTemps.setHorizontalAlignment(SwingConstants.RIGHT);
        labelTemps.setFont(new Font("Segoe UI", Font.BOLD, 16));
        labelTemps.setForeground(new Color(150, 200, 255));
        
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

                btn.addMouseListener(new EcouteurCase(l, c));

                grille[l][c] = btn;
                panneauGrille.add(btn);
            }
        }

        add(panneauGrille, BorderLayout.CENTER);

        // Boutons bas
        JPanel panneauBas = new JPanel();

        JButton boutonSauvegarderQuitter = new JButton("Sauver et Quitter");
        boutonSauvegarderQuitter.addActionListener(new EcouteurSauverQuitter());
        panneauBas.add(boutonSauvegarderQuitter);

        add(panneauBas, BorderLayout.SOUTH);

        // Démarrage du chronomètre
        chrono = new Timer(1000, new EcouteurChrono());
        chrono.start();
    }

    private class EcouteurCase extends MouseAdapter {
        private int ligne;
        private int colonne;

        public EcouteurCase(int ligne, int colonne) {
            this.ligne = ligne;
            this.colonne = colonne;
        }

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
    }

    private class EcouteurSauverQuitter implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            sauvegarderPartie();
            arreterChrono();
            fenetre.retournerAuMenu();
        }
    }

    private class EcouteurChrono implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            secondes++; 
            partie.setTemps(secondes);
            labelTemps.setText("Temps: " + secondes + "s");
        }
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

    /**
     * Effectue une sauvegarde silencieuse si la partie est encore en cours.
     */
    public void sauvegarderPartie() {
        try {
            if (partie.getEtat() == EtatPartie.EN_COURS) {
                Sauvegarde.sauvegarder(partie);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erreur lors de la sauvegarde automatique.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
}
