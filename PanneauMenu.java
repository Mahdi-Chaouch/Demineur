import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Menu principal du Démineur.
 * Propose: Nouvelle partie, Reprendre, Quitter.
 *
 * @author Équipe SAE
 * @version 1.0
 */
public class PanneauMenu extends JPanel {

    /**
     * Construit le panneau du menu.
     *
     * @param fenetre la fenêtre principale
     */
    public PanneauMenu(FenetrePrincipale fenetre) {
        setLayout(new GridLayout(3, 1, 10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton boutonNouvelle = new JButton("Nouvelle partie");
        boutonNouvelle.addActionListener(e -> fenetre.afficherConfiguration());
        add(boutonNouvelle);

        JButton boutonReprendre = new JButton("Reprendre partie");
        boutonReprendre.setEnabled(Sauvegarde.sauvegardeExiste());
        boutonReprendre.addActionListener(e -> {
            try {
                Partie partie = Sauvegarde.charger();
                fenetre.afficherJeu(partie);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Pas de sauvegarde", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });
        add(boutonReprendre);

        JButton boutonQuitter = new JButton("Quitter");
        boutonQuitter.addActionListener(e -> System.exit(0));
        add(boutonQuitter);
    }
}
