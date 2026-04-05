import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Panneau de fin de partie.
 *
 */
public class PanneauFin extends JPanel {

    private FenetrePrincipale fenetre;

    /**
     * Construit le panneau de fin.
     *
     * @param fenetre la fenêtre principale
     * @param partie la partie terminée
     */
    public PanneauFin(FenetrePrincipale fenetre, Partie partie) {
        this.fenetre = fenetre;
        setLayout(new BorderLayout(5, 5));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Message résultat
        boolean gagnee = (partie.getEtat() == EtatPartie.GAGNEE);
        String message = gagnee ? "VICTOIRE!" : "DEFAITE!";
        JLabel lblResultat = new JLabel(message);
        lblResultat.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblResultat.setForeground(gagnee ? new Color(120, 255, 120) : new Color(255, 100, 100));
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
                btn.mettrAJourAffichage();

                // Affichage spécifique en cas de défaite
                if (partie.getEtat() == EtatPartie.PERDUE) {
                    if (l == partie.getLigneMineExplosee() && c == partie.getColonneMineExplosee()) {
                        btn.setBackground(new Color(200, 40, 40));
                        btn.setText("X");
                        btn.setForeground(Color.WHITE);
                    } else if (caseModele.estMinee() && caseModele.getMarqueur() != Marqueur.MINE) {
                        btn.setBackground(new Color(60, 60, 60));
                        btn.setText("M");
                        btn.setForeground(new Color(255, 150, 150));
                    } else if (!caseModele.estMinee() && caseModele.getMarqueur() == Marqueur.MINE) {
                        btn.setBackground(new Color(150, 50, 50));
                        btn.setText("X");
                        btn.setForeground(Color.WHITE);
                    }
                }
                
                // Désactive l'interaction visuelle
                btn.setEnabled(false);
                panneauGrille.add(btn);
            }
        }

        add(panneauGrille, BorderLayout.CENTER);

        // Bouton retour
        JButton btnRetour = new JButton("Retour menu");
        btnRetour.addActionListener(new EcouteurRetour());
        add(btnRetour, BorderLayout.SOUTH);
    }

    private class EcouteurRetour implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            fenetre.retournerAuMenu();
        }
    }
}
