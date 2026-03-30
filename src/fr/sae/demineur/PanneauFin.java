package fr.sae.demineur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Panneau de fin de partie.
 *
 * @author Équipe SAE
 * @version 1.0
 */
public class PanneauFin extends JPanel {

    /**
     * Construit le panneau de fin.
     *
     * @param fenetre la fenêtre principale
     * @param partie  la partie terminée
     * @param temps   le temps écoulé en secondes
     */
    public PanneauFin(FenetrePrincipale fenetre, Partie partie, int temps) {
        setLayout(new BorderLayout(5, 5));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Message résultat
        String message = (partie.getEtat() == EtatPartie.GAGNEE) ? "VICTOIRE!" : "DEFAITE!";
        JLabel lblResultat = new JLabel(message);
        lblResultat.setFont(new Font("Arial", Font.BOLD, 24));
        lblResultat.setHorizontalAlignment(JLabel.CENTER);
        add(lblResultat, BorderLayout.NORTH);

        // Grille
        Plateau p = partie.getPlateau();
        JPanel panneauGrille = new JPanel();
        panneauGrille.setLayout(new GridLayout(p.getHauteur(), p.getLargeur(), 2, 2));

        for (int l = 0; l < p.getHauteur(); l++) {
            for (int c = 0; c < p.getLargeur(); c++) {
                Case caseModele = p.getCase(l, c);
                BoutonCase btn = new BoutonCase(caseModele, l, c);
                btn.setEnabled(false);
                btn.mettrAJourAffichage();
                panneauGrille.add(btn);
            }
        }

        // Panneau central : infos de temps / score + grille
        JPanel panneauCentre = new JPanel(new BorderLayout(5, 5));

        JLabel lblTemps = new JLabel("Temps : " + temps + "s", JLabel.CENTER);

        int meilleur = Sauvegarde.lireMeilleurScore();
        String texteScore;

        if (partie.getEtat() == EtatPartie.GAGNEE) {
            if (meilleur == -1 || temps < meilleur) {
                Sauvegarde.enregistrerMeilleurScore(temps);
                meilleur = temps;
                texteScore = "Nouveau meilleur temps : " + meilleur + "s";
            } else {
                texteScore = "Meilleur temps : " + meilleur + "s";
            }
        } else {
            if (meilleur == -1) {
                texteScore = "Aucun meilleur temps enregistré.";
            } else {
                texteScore = "Meilleur temps : " + meilleur + "s";
            }
        }

        JLabel lblScore = new JLabel(texteScore, JLabel.CENTER);

        JPanel panneauInfos = new JPanel(new GridLayout(2, 1, 0, 2));
        panneauInfos.add(lblTemps);
        panneauInfos.add(lblScore);

        panneauCentre.add(panneauInfos, BorderLayout.NORTH);
        panneauCentre.add(panneauGrille, BorderLayout.CENTER);

        add(panneauCentre, BorderLayout.CENTER);

        // Bouton retour
        JButton btnRetour = new JButton("Retour menu");
        btnRetour.addActionListener(e -> fenetre.retournerAuMenu());
        add(btnRetour, BorderLayout.SOUTH);
    }
}
