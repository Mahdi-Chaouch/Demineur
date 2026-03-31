package demineur;

import javax.swing.*;
import java.awt.*;

public class FenetrePrincipale extends JFrame {

    private JPanel panneauActuel;

    public FenetrePrincipale() {
        setTitle("Démineur");
        setMinimumSize(new Dimension(400, 400));
        setSize(520, 520);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(30, 39, 46));

        afficherPanneau(new PanneauMenu(this));
    }

    public void afficherPanneau(JPanel nouveau) {
        if (panneauActuel != null) getContentPane().remove(panneauActuel);
        panneauActuel = nouveau;
        getContentPane().add(panneauActuel, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(null);
        revalidate();
        repaint();
    }

    public void retournerAuMenu()              { afficherPanneau(new PanneauMenu(this)); }
    public void afficherConfiguration()        { afficherPanneau(new PanneauConfiguration(this)); }
    public void afficherJeu(Partie partie)     { afficherPanneau(new PanneauJeu(this, partie)); }

    public void afficherFinPartie(Partie partie, int temps) {
        afficherPanneau(new PanneauFin(this, partie, temps));
    }

    public void rafraichirJeu() {
        if (panneauActuel instanceof PanneauJeu) ((PanneauJeu) panneauActuel).rafraichir();
    }
}
