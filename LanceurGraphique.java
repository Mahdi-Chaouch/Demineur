import javax.swing.*;

/**
 * Point d'entrée de l'application graphique du Démineur.
 * Lance la fenêtre principale en mode graphique.
 *
 * @author Équipe SAE
 * @version 1.0
 */
public class LanceurGraphique {

    /**
     * Contrôle principal de l'application GUI.
     * Crée et affiche la fenêtre principale.
     *
     * @param args arguments (non utilisés)
     */
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
