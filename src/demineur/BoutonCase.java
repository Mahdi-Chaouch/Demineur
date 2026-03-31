package demineur;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;

public class BoutonCase extends JButton {

    // Couleurs des cases
    private static final Color COULEUR_CACHEE  = new Color(189, 189, 189);
    private static final Color COULEUR_REVELEE = new Color(155, 155, 155);
    private static final Color COULEUR_MINE    = new Color(220, 50,  50);

    // Bordures 3D
    private static final Border BORDURE_CACHEE =
        BorderFactory.createBevelBorder(BevelBorder.RAISED,
            new Color(220, 220, 220), new Color(90, 90, 90));
    private static final Border BORDURE_REVELEE =
        BorderFactory.createLineBorder(new Color(110, 110, 110), 1);

    // Couleurs des chiffres : index = nombre de mines adjacentes
    // 1 = vert, 2 = bleu, 3 = rouge (comme demandé)
    private static final Color[] COULEURS_NOMBRES = {
        Color.BLACK,                 // 0 (inutilisé)
        new Color(0,   160, 0),      // 1 vert
        new Color(0,   0,   200),    // 2 bleu
        new Color(210, 0,   0),      // 3 rouge
        new Color(0,   0,   128),    // 4 bleu foncé
        new Color(128, 0,   0),      // 5 bordeaux
        new Color(0,   128, 128),    // 6 teal
        new Color(0,   0,   0),      // 7 noir
        new Color(105, 105, 105),    // 8 gris
    };

    private final Case caseModele;
    private final int ligne;
    private final int colonne;

    // Couleur utilisée dans paintComponent (contourne le gris du L&F sur disabled)
    private Color couleurAffichage = Color.BLACK;

    public BoutonCase(Case caseModele, int ligne, int colonne) {
        this.caseModele = caseModele;
        this.ligne   = ligne;
        this.colonne = colonne;

        setUI(new BasicButtonUI());
        setFont(new Font("Arial", Font.BOLD, 15));
        setPreferredSize(new Dimension(38, 38));
        setMargin(new Insets(0, 0, 0, 0));
        setFocusPainted(false);
        setOpaque(true);
        setContentAreaFilled(false); // on gère le fond nous-mêmes

        mettrAJourAffichage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                            RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        // Fond
        g2.setColor(getBackground());
        g2.fillRect(0, 0, getWidth(), getHeight());

        // Texte centré avec notre couleur (pas celle du L&F)
        String texte = getText();
        if (texte != null && !texte.isEmpty()) {
            g2.setFont(getFont());
            g2.setColor(couleurAffichage);
            FontMetrics fm = g2.getFontMetrics();
            int x = (getWidth()  - fm.stringWidth(texte)) / 2;
            int y = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
            g2.drawString(texte, x, y);
        }
        g2.dispose();
    }

    public void mettrAJourAffichage() {
        if (caseModele.estRevelee()) {
            setEnabled(false);
            setBorder(BORDURE_REVELEE);

            if (caseModele.estMinee()) {
                setBackground(COULEUR_MINE);
                couleurAffichage = Color.WHITE;
                setText("X");
            } else if (caseModele.getMinesAdjacentes() == 0) {
                setBackground(COULEUR_REVELEE);
                couleurAffichage = Color.BLACK;
                setText("");
            } else {
                int nb = caseModele.getMinesAdjacentes();
                setBackground(COULEUR_REVELEE);
                couleurAffichage = COULEURS_NOMBRES[nb];
                setText(String.valueOf(nb));
            }
        } else {
            setEnabled(true);
            setBackground(COULEUR_CACHEE);
            setBorder(BORDURE_CACHEE);

            Marqueur marqueur = caseModele.getMarqueur();
            if (marqueur == Marqueur.MINE) {
                couleurAffichage = new Color(180, 0, 0);
                setText("F");
            } else if (marqueur == Marqueur.SUSPECT) {
                couleurAffichage = new Color(200, 120, 0);
                setText("?");
            } else {
                couleurAffichage = Color.BLACK;
                setText("");
            }
        }
        repaint();
    }

    public int getLigne()   { return ligne; }
    public int getColonne() { return colonne; }
}
