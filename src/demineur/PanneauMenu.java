package demineur;

import javax.swing.*;
import java.awt.*;

public class PanneauMenu extends JPanel {

    private static final Color BG       = new Color(30, 39, 46);
    private static final Color BTN_NEW  = new Color(52, 152, 219);
    private static final Color BTN_LOAD = new Color(46, 204, 113);
    private static final Color BTN_QUIT = new Color(231, 76, 60);

    public PanneauMenu(FenetrePrincipale fenetre) {
        setBackground(BG);
        setLayout(new GridBagLayout());

        JPanel carte = new JPanel();
        carte.setBackground(new Color(44, 62, 80));
        carte.setLayout(new BoxLayout(carte, BoxLayout.Y_AXIS));
        carte.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        JLabel titre = new JLabel("DEMINEUR");
        titre.setFont(new Font("Arial", Font.BOLD, 30));
        titre.setForeground(Color.WHITE);
        titre.setAlignmentX(CENTER_ALIGNMENT);
        carte.add(titre);
        carte.add(Box.createVerticalStrut(8));

        JLabel sousTitre = new JLabel("Trouvez toutes les mines !");
        sousTitre.setFont(new Font("Arial", Font.PLAIN, 13));
        sousTitre.setForeground(new Color(149, 165, 166));
        sousTitre.setAlignmentX(CENTER_ALIGNMENT);
        carte.add(sousTitre);
        carte.add(Box.createVerticalStrut(30));

        JButton boutonNouvelle = creerBouton("Nouvelle partie", BTN_NEW);
        boutonNouvelle.addActionListener(e -> fenetre.afficherConfiguration());
        carte.add(boutonNouvelle);
        carte.add(Box.createVerticalStrut(12));

        JButton boutonReprendre = creerBouton("Reprendre partie", BTN_LOAD);
        boutonReprendre.setEnabled(Sauvegarde.sauvegardeExiste());
        boutonReprendre.addActionListener(e -> {
            try {
                Partie partie = Sauvegarde.charger();
                fenetre.afficherJeu(partie);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Pas de sauvegarde valide.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });
        carte.add(boutonReprendre);
        carte.add(Box.createVerticalStrut(12));

        JButton boutonQuitter = creerBouton("Quitter", BTN_QUIT);
        boutonQuitter.addActionListener(e -> System.exit(0));
        carte.add(boutonQuitter);

        add(carte);
    }

    private JButton creerBouton(String texte, Color couleur) {
        JButton btn = new JButton(texte);
        btn.setFont(new Font("Arial", Font.BOLD, 15));
        btn.setForeground(Color.WHITE);
        btn.setBackground(couleur);
        btn.setOpaque(true);
        btn.setContentAreaFilled(true);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setAlignmentX(CENTER_ALIGNMENT);
        btn.setMaximumSize(new Dimension(220, 42));
        btn.setPreferredSize(new Dimension(220, 42));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }
}
