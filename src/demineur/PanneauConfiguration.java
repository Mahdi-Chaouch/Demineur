package demineur;

import javax.swing.*;
import java.awt.*;

public class PanneauConfiguration extends JPanel {

    private static final Color BG = new Color(30, 39, 46);

    private final JTextField tfLargeur;
    private final JTextField tfHauteur;
    private final JTextField tfMines;

    public PanneauConfiguration(FenetrePrincipale fenetre) {
        setBackground(BG);
        setLayout(new GridBagLayout());

        JPanel carte = new JPanel();
        carte.setBackground(new Color(44, 62, 80));
        carte.setLayout(new BoxLayout(carte, BoxLayout.Y_AXIS));
        carte.setBorder(BorderFactory.createEmptyBorder(25, 40, 25, 40));

        JLabel titre = new JLabel("⚙  Configuration");
        titre.setFont(new Font("Arial", Font.BOLD, 22));
        titre.setForeground(Color.WHITE);
        titre.setAlignmentX(CENTER_ALIGNMENT);
        carte.add(titre);
        carte.add(Box.createVerticalStrut(20));

        // Presets
        JPanel presets = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 0));
        presets.setBackground(new Color(44, 62, 80));
        presets.setAlignmentX(CENTER_ALIGNMENT);

        JButton facile = creerPreset("Facile",   new Color(46, 204, 113));
        JButton moyen  = creerPreset("Moyen",    new Color(230, 126, 34));
        JButton difficile = creerPreset("Difficile", new Color(231, 76, 60));

        tfLargeur = new JTextField("10", 4);
        tfHauteur = new JTextField("10", 4);
        tfMines   = new JTextField("10", 4);

        facile.addActionListener(e -> { tfLargeur.setText("9");  tfHauteur.setText("9");  tfMines.setText("10"); });
        moyen.addActionListener(e  -> { tfLargeur.setText("16"); tfHauteur.setText("16"); tfMines.setText("40"); });
        difficile.addActionListener(e -> { tfLargeur.setText("30"); tfHauteur.setText("16"); tfMines.setText("99"); });

        presets.add(facile);
        presets.add(moyen);
        presets.add(difficile);
        carte.add(presets);
        carte.add(Box.createVerticalStrut(18));

        // Champs
        JPanel champs = new JPanel(new GridLayout(3, 2, 10, 8));
        champs.setBackground(new Color(44, 62, 80));
        champs.setAlignmentX(CENTER_ALIGNMENT);

        champs.add(label("Largeur (4–30) :"));
        styleChamp(tfLargeur);
        champs.add(tfLargeur);

        champs.add(label("Hauteur (4–30) :"));
        styleChamp(tfHauteur);
        champs.add(tfHauteur);

        champs.add(label("Mines :"));
        styleChamp(tfMines);
        champs.add(tfMines);

        carte.add(champs);
        carte.add(Box.createVerticalStrut(20));

        // Boutons
        JPanel boutons = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        boutons.setBackground(new Color(44, 62, 80));
        boutons.setAlignmentX(CENTER_ALIGNMENT);

        JButton demarrer = creerBouton("▶  Démarrer", new Color(52, 152, 219));
        demarrer.addActionListener(e -> {
            try {
                int larg = Integer.parseInt(tfLargeur.getText());
                int haut = Integer.parseInt(tfHauteur.getText());
                int mines = Integer.parseInt(tfMines.getText());
                if (larg < 4 || larg > 30 || haut < 4 || haut > 30)
                    throw new Exception("Dimensions invalides (4-30)");
                if (mines < 1 || mines >= larg * haut)
                    throw new Exception("Nombre de mines invalide");
                fenetre.afficherJeu(new Partie(larg, haut, mines));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erreur : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton retour = creerBouton("← Retour", new Color(100, 100, 100));
        retour.addActionListener(e -> fenetre.retournerAuMenu());

        boutons.add(demarrer);
        boutons.add(retour);
        carte.add(boutons);

        add(carte);
    }

    private JLabel label(String texte) {
        JLabel l = new JLabel(texte);
        l.setForeground(new Color(189, 195, 199));
        l.setFont(new Font("Arial", Font.PLAIN, 13));
        return l;
    }

    private void styleChamp(JTextField tf) {
        tf.setBackground(new Color(52, 73, 94));
        tf.setForeground(Color.WHITE);
        tf.setCaretColor(Color.WHITE);
        tf.setFont(new Font("Arial", Font.PLAIN, 13));
        tf.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(100, 120, 140), 1),
            BorderFactory.createEmptyBorder(4, 6, 4, 6)));
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
        btn.setPreferredSize(new Dimension(130, 36));
        return btn;
    }

    private JButton creerPreset(String texte, Color couleur) {
        JButton btn = new JButton(texte);
        btn.setFont(new Font("Arial", Font.BOLD, 12));
        btn.setForeground(Color.WHITE);
        btn.setBackground(couleur);
        btn.setOpaque(true);
        btn.setContentAreaFilled(true);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(90, 30));
        return btn;
    }
}
