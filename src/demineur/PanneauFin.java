package demineur;

import javax.swing.*;
import java.awt.*;

public class PanneauFin extends JPanel {

    private static final Color BG = new Color(30, 39, 46);

    public PanneauFin(FenetrePrincipale fenetre, Partie partie, int temps) {
        setBackground(BG);
        setLayout(new BorderLayout(0, 10));
        setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        boolean victoire = partie.getEtat() == EtatPartie.GAGNEE;

        // --- En-tête résultat ---
        JPanel header = new JPanel();
        header.setBackground(victoire ? new Color(39, 174, 96) : new Color(192, 57, 43));
        header.setBorder(BorderFactory.createEmptyBorder(12, 10, 12, 10));

        JLabel lblResultat = new JLabel(victoire ? "VICTOIRE !" : "DEFAITE !");
        lblResultat.setFont(new Font("Arial", Font.BOLD, 26));
        lblResultat.setForeground(Color.WHITE);
        header.add(lblResultat);
        add(header, BorderLayout.NORTH);

        // --- Grille ---
        Plateau p = partie.getPlateau();
        JPanel panneauGrille = new JPanel(new GridLayout(p.getHauteur(), p.getLargeur(), 2, 2));
        panneauGrille.setBackground(new Color(44, 62, 80));
        panneauGrille.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));

        for (int l = 0; l < p.getHauteur(); l++) {
            for (int c = 0; c < p.getLargeur(); c++) {
                BoutonCase btn = new BoutonCase(p.getCase(l, c), l, c);
                btn.setEnabled(false);
                panneauGrille.add(btn);
            }
        }

        // --- Infos score ---
        int meilleur = Sauvegarde.lireMeilleurScore();
        String texteScore;
        if (victoire) {
            if (meilleur == -1 || temps < meilleur) {
                Sauvegarde.enregistrerMeilleurScore(temps);
                texteScore = "Nouveau record : " + temps + "s !";
            } else {
                texteScore = "Meilleur temps : " + meilleur + "s";
            }
        } else {
            texteScore = meilleur == -1 ? "Aucun record enregistre." : "Meilleur temps : " + meilleur + "s";
        }

        JPanel infos = new JPanel(new GridLayout(2, 1, 0, 4));
        infos.setBackground(new Color(44, 62, 80));
        infos.setBorder(BorderFactory.createEmptyBorder(6, 10, 6, 10));

        JLabel lblTemps = new JLabel("Temps : " + temps + "s", JLabel.CENTER);
        lblTemps.setFont(new Font("Arial", Font.BOLD, 14));
        lblTemps.setForeground(new Color(52, 152, 219));

        JLabel lblScore = new JLabel(texteScore, JLabel.CENTER);
        lblScore.setFont(new Font("Arial", Font.PLAIN, 13));
        lblScore.setForeground(new Color(241, 196, 15));

        infos.add(lblTemps);
        infos.add(lblScore);

        JPanel centre = new JPanel(new BorderLayout(0, 6));
        centre.setBackground(BG);
        centre.add(infos, BorderLayout.NORTH);
        centre.add(panneauGrille, BorderLayout.CENTER);
        add(centre, BorderLayout.CENTER);

        // --- Bouton retour ---
        JPanel bas = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bas.setBackground(BG);
        JButton btnRetour = new JButton("Menu principal");
        btnRetour.setFont(new Font("Arial", Font.BOLD, 13));
        btnRetour.setForeground(Color.WHITE);
        btnRetour.setBackground(new Color(100, 100, 100));
        btnRetour.setOpaque(true);
        btnRetour.setContentAreaFilled(true);
        btnRetour.setBorderPainted(false);
        btnRetour.setFocusPainted(false);
        btnRetour.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnRetour.setPreferredSize(new Dimension(160, 36));
        btnRetour.addActionListener(e -> fenetre.retournerAuMenu());
        bas.add(btnRetour);
        add(bas, BorderLayout.SOUTH);
    }
}
