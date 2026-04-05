import javax.swing.*;
import java.awt.*;

/**
 * Bouton représentant une case du Démineur.
 */
public class BoutonCase extends JButton {

    private static final Color COULEUR_CACHEE = new Color(50, 56, 70);
    private static final Color COULEUR_REVELEE = new Color(30, 34, 42);
    
    // Couleurs distinctes adaptées pour un thème sombre (style Démineur classique mais lumineux)
    private static final Color[] COULEURS_NOMBRES = {
        Color.BLACK,              // 0 (non utilisé)
        new Color(64, 156, 255),  // 1 - Bleu ciel vif
        new Color(75, 215, 100),  // 2 - Vert pomme
        new Color(255, 85, 85),   // 3 - Rouge vif
        new Color(175, 100, 255), // 4 - Violet clair
        new Color(255, 160, 50),  // 5 - Orange
        new Color(50, 240, 240),  // 6 - Cyan/Turquoise
        new Color(255, 230, 80),  // 7 - Jaune citron
        new Color(220, 220, 220)  // 8 - Gris clair
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

        setFont(new Font("Segoe UI", Font.BOLD, 22));
        setPreferredSize(new Dimension(50, 50));
        setMargin(new Insets(0, 0, 0, 0));
        setFocusPainted(false);
        setContentAreaFilled(true);
        // Bordure subtile
        setBorder(BorderFactory.createLineBorder(new Color(25, 30, 35), 1));

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
                setForeground(new Color(255, 50, 50));
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
            setForeground(Color.WHITE);
            
            int marqueur = caseModele.getMarqueur();
            if (marqueur == Marqueur.MINE) {
                setText("★");
                setForeground(new Color(255, 215, 0)); // Doré
            } else if (marqueur == Marqueur.SUSPECT) {
                setText("?");
                setForeground(new Color(255, 150, 50)); // Orange
            } else {
                setText("");
            }
        }
        setOpaque(true);
    }

    /**
     * Astuce Swing : force le bouton à se peindre avec ses couleurs normales
     * même s'il est désactivé (pour éviter que le texte ne devienne illisible).
     */
    @Override
    protected void paintComponent(Graphics g) {
        boolean e = isEnabled();
        if (!e) setEnabled(true);
        super.paintComponent(g);
        if (!e) setEnabled(false);
    }

    public int getLigne() {
        return ligne;
    }

    public int getColonne() {
        return colonne;
    }
}
