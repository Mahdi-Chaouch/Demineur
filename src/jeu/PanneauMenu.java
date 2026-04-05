import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Menu principal du Démineur.
 * Propose: Nouvelle partie, Reprendre, Quitter.
 *
 
 */
public class PanneauMenu extends JPanel {

    private FenetrePrincipale fenetre;

    /**
     * Construit le panneau du menu.
     *
     * @param fenetre la fenêtre principale
     */
    public PanneauMenu(FenetrePrincipale fenetre) {
        this.fenetre = fenetre;
        setLayout(new GridLayout(3, 1, 20, 20));
        setBorder(BorderFactory.createEmptyBorder(100, 100, 100, 100));

        JButton boutonNouvelle = new JButton("Nouvelle partie");
        boutonNouvelle.addActionListener(new EcouteurNouvelle());
        add(boutonNouvelle);

        JButton boutonReprendre = new JButton("Reprendre partie");
        boutonReprendre.setEnabled(Sauvegarde.sauvegardeExiste());
        boutonReprendre.addActionListener(new EcouteurReprendre());
        add(boutonReprendre);

        JButton boutonQuitter = new JButton("Quitter");
        boutonQuitter.addActionListener(new EcouteurQuitter());
        add(boutonQuitter);
    }

    private class EcouteurNouvelle implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            fenetre.afficherConfiguration();
        }
    }

    private class EcouteurReprendre implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                Partie partie = Sauvegarde.charger();
                fenetre.afficherJeu(partie);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(PanneauMenu.this, "Pas de sauvegarde", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private class EcouteurQuitter implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }
}
