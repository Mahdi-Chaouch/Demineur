package fr.sae.demineur;

import javax.swing.*;
import java.awt.*;

/**
 * Bouton représentant une case du Démineur.
 *
 * @author Équipe SAE
 * @version 1.0
 */
public class BoutonCase extends JButton {

    private static final Color COULEUR_CACHEE = new Color(192, 192, 192);
    private static final Color COULEUR_REVELEE = new Color(240, 240, 240);
    
    private static final Color[] COULEURS_NOMBRES = {
        Color.BLACK,        // 0 (non utilisé)
        Color.BLUE,         // 1
        new Color(0, 128, 0),  // 2 vert
        Color.RED,          // 3 rouge
        new Color(0, 0, 128),  // 4 bleu foncé
        new Color(128, 0, 0),  // 5 rouge foncé
        new Color(0, 128, 128), // 6 cyan
        Color.BLACK,        // 7 noir
        Color.GRAY          // 8 gris
    };

    private Case caseModele;
    private int ligne;
    private int colonne;

    /**
     * Construit un bouton-case.
     *
     * @param caseModele la case du modèle
     * @param ligne la ligne
     * @param colonne la colonne
     */
    public BoutonCase(Case caseModele, int ligne, int colonne) {
        this.caseModele = caseModele;
        this.ligne = ligne;
        this.colonne = colonne;

        setFont(new Font("Arial", Font.BOLD, 24));
        setPreferredSize(new Dimension(50, 50));
        setMargin(new Insets(0, 0, 0, 0));
        setFocusPainted(false);
        setContentAreaFilled(true);
        setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));

        mettrAJourAffichage();
    }

    /**
     * Met à jour l'affichage de la case.
     */
    public void mettrAJourAffichage() {
        if (caseModele.estRevelee()) {
            setEnabled(false);
            setBackground(COULEUR_REVELEE);
            
            if (caseModele.estMinee()) {
                setText("X");
                setForeground(Color.RED);
            } else if (caseModele.getMinesAdjacentes() == 0) {
                setText("");
            } else {
                int nb = caseModele.getMinesAdjacentes();
                setText(String.valueOf(nb));
                setForeground(COULEURS_NOMBRES[nb]);
            }
        } else {
            setEnabled(true);
            setBackground(COULEUR_CACHEE);
            setForeground(Color.BLACK);
            
            Marqueur marqueur = caseModele.getMarqueur();
            if (marqueur == Marqueur.MINE) {
                setText("★");
            } else if (marqueur == Marqueur.SUSPECT) {
                setText("?");
            } else {
                setText("");
            }
        }
        setOpaque(true);
    }

    public int getLigne() {
        return ligne;
    }

    public int getColonne() {
        return colonne;
    }
}
