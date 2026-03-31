package demineur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PanneauJeu extends JPanel {

    private static final Color BG_DARK   = new Color(30, 39, 46);
    private static final Color BG_HEADER = new Color(44, 62, 80);

    private final Partie partie;
    private final BoutonCase[][] grille;
    private final JLabel labelMines;
    private final JLabel labelTemps;
    private final Timer chrono;
    private int secondes;

    public PanneauJeu(FenetrePrincipale fenetre, Partie partie) {
        this.partie   = partie;
        this.secondes = 0;

        setBackground(BG_DARK);
        setLayout(new BorderLayout(0, 8));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // --- En-tête ---
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(BG_HEADER);
        header.setBorder(BorderFactory.createEmptyBorder(8, 14, 8, 14));

        Plateau p = partie.getPlateau();

        labelMines = new JLabel("Mines : " + p.getNbMines());
        labelMines.setFont(new Font("Arial", Font.BOLD, 16));
        labelMines.setForeground(new Color(231, 76, 60));

        labelTemps = new JLabel("Temps : 0s");
        labelTemps.setFont(new Font("Arial", Font.BOLD, 16));
        labelTemps.setForeground(new Color(52, 152, 219));
        labelTemps.setHorizontalAlignment(SwingConstants.RIGHT);

        header.add(labelMines, BorderLayout.WEST);
        header.add(labelTemps, BorderLayout.EAST);
        add(header, BorderLayout.NORTH);

        // --- Grille ---
        JPanel panneauGrille = new JPanel();
        panneauGrille.setBackground(new Color(44, 62, 80));
        panneauGrille.setLayout(new GridLayout(p.getHauteur(), p.getLargeur(), 2, 2));
        panneauGrille.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));

        grille = new BoutonCase[p.getHauteur()][p.getLargeur()];
        for (int l = 0; l < p.getHauteur(); l++) {
            for (int c = 0; c < p.getLargeur(); c++) {
                BoutonCase btn = new BoutonCase(p.getCase(l, c), l, c);
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
                            fenetre.afficherFinPartie(partie, secondes);
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

        // --- Bas ---
        JPanel panneauBas = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        panneauBas.setBackground(BG_DARK);
        panneauBas.setBorder(BorderFactory.createEmptyBorder(4, 0, 0, 0));

        JButton boutonSauvegarder = creerBouton("Sauvegarder", new Color(155, 89, 182));
        boutonSauvegarder.addActionListener(e -> {
            try {
                Sauvegarde.sauvegarder(partie);
                JOptionPane.showMessageDialog(this, "Partie sauvegardée.", "Sauvegarde", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erreur lors de la sauvegarde.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton boutonQuitter = creerBouton("Menu", new Color(100, 100, 100));
        boutonQuitter.addActionListener(e -> {
            arreterChrono();
            fenetre.retournerAuMenu();
        });

        panneauBas.add(boutonSauvegarder);
        panneauBas.add(boutonQuitter);
        add(panneauBas, BorderLayout.SOUTH);

        // --- Chrono ---
        chrono = new Timer(1000, e -> {
            secondes++;
            labelTemps.setText("Temps : " + secondes + "s");
        });
        chrono.start();
    }

    private JButton creerBouton(String texte, Color couleur) {
        JButton btn = new JButton(texte);
        btn.setFont(new Font("Arial", Font.BOLD, 13));
        btn.setForeground(Color.WHITE);
        btn.setBackground(couleur);
        btn.setOpaque(true);
        btn.setContentAreaFilled(true);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private void arreterChrono() {
        if (chrono != null && chrono.isRunning()) chrono.stop();
    }

    public int getSecondes() { return secondes; }

    public void rafraichir() {
        Plateau p = partie.getPlateau();
        for (int l = 0; l < p.getHauteur(); l++)
            for (int c = 0; c < p.getLargeur(); c++)
                grille[l][c].mettrAJourAffichage();
        int restantes = p.getNbMines() - p.getNbMarqueursMine();
        labelMines.setText("Mines : " + restantes);
        repaint();
    }
}
