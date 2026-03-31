package demineur;

import javax.swing.*;

public class LanceurGraphique {

        public static void main(String[] args) {
        // Lancer dans le thread UI de Swing
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                FenetrePrincipale fenetre = new FenetrePrincipale();
                fenetre.setVisible(true);
            }
        });
    }
}

