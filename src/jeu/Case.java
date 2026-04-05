/**
 * Représente une case de la grille du Démineur.
 * Une case peut être cachée ou révélée, minée ou non,
 * et peut porter un marqueur (★ ou ?).
 */
public class Case {

    /** Indique si cette case contient une mine. */
    private boolean estMinee;

    /** Indique si cette case a été révélée par le joueur. */
    private boolean estRevelee;

    /** Marqueur posé par le joueur (VIDE, MINE ou SUSPECT). */
    private int marqueur;

    /** Nombre de mines dans les cases adjacentes (0 à 8). */
    private int minesAdjacentes;

    /**
     * Construit une case initialement cachée, non minée,
     * sans marqueur et sans mines adjacentes connues.
     */
    public Case() {
        this.estMinee = false;
        this.estRevelee = false;
        this.marqueur = Marqueur.VIDE;
        this.minesAdjacentes = 0;
    }

    /**
     * Indique si la case contient une mine.
     *
     * @return true si la case est minée, false sinon
     */
    public boolean estMinee() {
        return estMinee;
    }

    /**
     * Définit si la case contient une mine.
     *
     * @param estMinee true pour miner la case, false sinon
     */
    public void setEstMinee(boolean estMinee) {
        this.estMinee = estMinee;
    }

    /**
     * Indique si la case a été révélée.
     *
     * @return true si la case est révélée, false sinon
     */
    public boolean estRevelee() {
        return estRevelee;
    }

    /**
     * Révèle la case (passe à l'état révélé).
     */
    public void reveler() {
        this.estRevelee = true;
    }

    /**
     * Retourne le marqueur posé sur cette case.
     *
     * @return le marqueur actuel (VIDE, MINE ou SUSPECT)
     */
    public int getMarqueur() {
        return marqueur;
    }

    /**
     * Fait tourner le marqueur dans l'ordre :
     * VIDE → MINE (★) → SUSPECT (?) → VIDE.
     * N'a aucun effet si la case est déjà révélée.
     */
    public void changerMarqueur() {
        if (estRevelee) return;

        switch (marqueur) {
            case Marqueur.VIDE:
                marqueur = Marqueur.MINE;
                break;
            case Marqueur.MINE:
                marqueur = Marqueur.SUSPECT;
                break;
            case Marqueur.SUSPECT:
                marqueur = Marqueur.VIDE;
                break;
        }
    }

    /**
     * Retourne le nombre de mines dans les cases adjacentes.
     *
     * @return un entier entre 0 et 8
     */
    public int getMinesAdjacentes() {
        return minesAdjacentes;
    }

    /**
     * Définit le nombre de mines adjacentes à cette case.
     *
     * @param minesAdjacentes le nombre de mines voisines (0 à 8)
     */
    public void setMinesAdjacentes(int minesAdjacentes) {
        this.minesAdjacentes = minesAdjacentes;
    }
}
