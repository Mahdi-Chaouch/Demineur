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
     * @param partie la partie terminée
     */
    public PanneauFin(FenetrePrincipale fenetre, Partie partie) {
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

        add(panneauGrille, BorderLayout.CENTER);

        // Bouton retour
        JButton btnRetour = new JButton("Retour menu");
        btnRetour.addActionListener(e -> fenetre.retournerAuMenu());
        add(btnRetour, BorderLayout.SOUTH);
    }
}
