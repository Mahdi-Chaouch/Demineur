/**
 * Gere l'etat d'une partie et les actions du joueur.
 */
public class Partie {
    private final Plateau plateau;
    private EtatPartie etat;

    /**
     * Cree une nouvelle partie avec un plateau genere aleatoirement.
     *
     * @param largeur  largeur de la grille
     * @param hauteur  hauteur de la grille
     * @param nbMines  nombre de mines
     */
    public Partie(int largeur, int hauteur, int nbMines) {
        this.plateau = new Plateau(largeur, hauteur, nbMines);
        this.etat = EtatPartie.EN_COURS;
    }

    /**
     * Tente de reveler la case aux coordonnees donnees.
     * Met a jour l'etat de la partie (PERDUE ou GAGNEE si necessaire).
     *
     * @param ligne   ligne de la case
     * @param colonne colonne de la case
     */
    public void reveler(int ligne, int colonne) {
        if (etat != EtatPartie.EN_COURS) {
            return;
        }

        boolean mineTouchee = plateau.revelerCase(ligne, colonne);
        if (mineTouchee) {
            etat = EtatPartie.PERDUE;
            return;
        }

        if (plateau.toutesCasesSuresRevelees()) {
            etat = EtatPartie.GAGNEE;
        }
    }

    /**
     * Bascule le marqueur de la case aux coordonnees donnees.
     *
     * @param ligne   ligne de la case
     * @param colonne colonne de la case
     */
    public void marquer(int ligne, int colonne) {
        if (etat != EtatPartie.EN_COURS) {
            return;
        }
        plateau.basculerMarqueur(ligne, colonne);
    }

    /**
     * Retourne le plateau de jeu.
     *
     * @return le plateau
     */
    public Plateau getPlateau() {
        return plateau;
    }

    /**
     * Retourne l'etat actuel de la partie.
     *
     * @return l'etat (EN_COURS, GAGNEE, PERDUE)
     */
    public EtatPartie getEtat() {
        return etat;
    }

    /**
     * Definit l'etat de la partie. Utilise lors du chargement d'une sauvegarde.
     *
     * @param etat le nouvel etat
     */
    public void setEtat(EtatPartie etat) {
        this.etat = etat;
    }
}