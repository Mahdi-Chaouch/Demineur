import javax.swing.*;
import java.awt.Color;
import java.awt.Font;


public class LanceurGraphique {


    static class Demarrage implements Runnable {
        public void run() {
            try {
                // Look and feel de base du systeme ou cross platform
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());

                // Couleurs de fond globales
                Color fondSombre = new Color(30, 34, 42);
                Color fondBouton = new Color(50, 56, 70);
                Color texteClair = new Color(240, 240, 240);

                UIManager.put("Panel.background", fondSombre);
                UIManager.put("Label.foreground", texteClair);
                UIManager.put("Button.background", fondBouton);
                UIManager.put("Button.foreground", texteClair);
                UIManager.put("Button.focus", new Color(0, 0, 0, 0)); // Pas de carre de focus
                UIManager.put("Button.font", new Font("Segoe UI", Font.BOLD, 14));
                UIManager.put("Label.font", new Font("Segoe UI", Font.PLAIN, 16));
                UIManager.put("TextField.background", new Color(40, 45, 55));
                UIManager.put("TextField.foreground", texteClair);
                UIManager.put("TextField.caretForeground", texteClair);
                UIManager.put("OptionPane.background", fondSombre);
                UIManager.put("OptionPane.messageForeground", texteClair);

            } catch (Exception e) {}

            FenetrePrincipale fenetre = new FenetrePrincipale();
            // On peut modifier le fond de la fentre principale aussit
            fenetre.getContentPane().setBackground(new Color(30, 34, 42));
            fenetre.setVisible(true);
        }
    }

    public static void main(String[] args) {
        // Lancer dans le thread UI de Swing
        SwingUtilities.invokeLater(new Demarrage());
    }
}
