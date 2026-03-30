package fr.sae.demineur;

import java.util.Random;

/**
 * Modele principal de la grille de Demineur.
 */
public class Plateau {
    private final int largeur;
    private final int hauteur;
    private final int nbMines;
    private final Case[][] cases;
    private int nbCasesRevelees;

    /**
     * Construit un plateau, place les mines, puis calcule les adjacences.
     *
     * @param largeur largeur de la grille
     * @param hauteur hauteur de la grille
     * @param nbMines nombre total de mines
     */
    public Plateau(int largeur, int hauteur, int nbMines) {
        if (largeur <= 0 || hauteur <= 0) {
            throw new IllegalArgumentException("Dimensions invalides.");
        }
        if (nbMines < 1 || nbMines >= largeur * hauteur) {
            throw new IllegalArgumentException("Nombre de mines invalide.");
        }

        this.largeur = largeur;
        this.hauteur = hauteur;
        this.nbMines = nbMines;
        this.cases = new Case[hauteur][largeur];
        this.nbCasesRevelees = 0;

        initialiserCases();
        placerMinesAleatoires();
        calculerMinesAdjacentes();
    }

    private void initialiserCases() {
        int l;
        int c;
        for (l = 0; l < hauteur; l++) {
            for (c = 0; c < largeur; c++) {
                cases[l][c] = new Case();
            }
        }
    }

    private void placerMinesAleatoires() {
        Random random = new Random();
        int minesPlacees = 0;
        while (minesPlacees < nbMines) {
            int l = random.nextInt(hauteur);
            int c = random.nextInt(largeur);
            if (!cases[l][c].estMinee()) {
                cases[l][c].setEstMinee(true);
                minesPlacees++;
            }
        }
    }

    private void calculerMinesAdjacentes() {
        int l;
        int c;
        for (l = 0; l < hauteur; l++) {
            for (c = 0; c < largeur; c++) {
                if (cases[l][c].estMinee()) {
                    continue;
                }
                cases[l][c].setMinesAdjacentes(compterMinesAutour(l, c));
            }
        }
    }

    private int compterMinesAutour(int ligne, int colonne) {
        int total = 0;
        int dl;
        int dc;
        for (dl = -1; dl <= 1; dl++) {
            for (dc = -1; dc <= 1; dc++) {
                if (dl == 0 && dc == 0) {
                    continue;
                }
                int nl = ligne + dl;
                int nc = colonne + dc;
                if (estDansBornes(nl, nc) && cases[nl][nc].estMinee()) {
                    total++;
                }
            }
        }
        return total;
    }

    private boolean estDansBornes(int ligne, int colonne) {
        return ligne >= 0 && ligne < hauteur && colonne >= 0 && colonne < largeur;
    }

    /**
     * Revele une case. Retourne true si une mine est touchee.
     *
     * @param ligne   ligne de la case
     * @param colonne colonne de la case
     * @return true si la case etait minee
     */
    public boolean revelerCase(int ligne, int colonne) {
        if (!estDansBornes(ligne, colonne)) {
            throw new IllegalArgumentException("Coordonnees hors bornes.");
        }

        Case cible = cases[ligne][colonne];
        if (cible.estRevelee()) {
            return false;
        }
        if (cible.getMarqueur() != Marqueur.VIDE) {
            return false;
        }

        if (cible.estMinee()) {
            cible.reveler();
            nbCasesRevelees++;
            return true;
        }

        revelerRecursif(ligne, colonne);
        return false;
    }

    /**
     * Revelation en chaine des cases vides adjacentes.
     */
    private void revelerRecursif(int ligne, int colonne) {
        if (!estDansBornes(ligne, colonne)) {
            return;
        }

        Case courante = cases[ligne][colonne];
        if (courante.estRevelee()) {
            return;
        }
        if (courante.getMarqueur() != Marqueur.VIDE) {
            return;
        }
        if (courante.estMinee()) {
            return;
        }

        courante.reveler();
        nbCasesRevelees++;

        if (courante.getMinesAdjacentes() > 0) {
            return;
        }

        int dl;
        int dc;
        for (dl = -1; dl <= 1; dl++) {
            for (dc = -1; dc <= 1; dc++) {
                if (dl == 0 && dc == 0) {
                    continue;
                }
                revelerRecursif(ligne + dl, colonne + dc);
            }
        }
    }

    /**
     * Bascule le marqueur de la case (VIDE -> MINE -> SUSPECT -> VIDE).
     *
     * @param ligne   ligne de la case
     * @param colonne colonne de la case
     */
    public void basculerMarqueur(int ligne, int colonne) {
        if (!estDansBornes(ligne, colonne)) {
            throw new IllegalArgumentException("Coordonnees hors bornes.");
        }
        cases[ligne][colonne].changerMarqueur();
    }

    /**
     * Indique si toutes les cases non minees ont ete revelees.
     *
     * @return true si la partie est gagnee
     */
    public boolean toutesCasesSuresRevelees() {
        return nbCasesRevelees == (largeur * hauteur - nbMines);
    }

    /**
     * Retourne le nombre de cases portant le marqueur MINE (★).
     * Utilise pour afficher : nbMines - nbMarqueursMine pendant la partie.
     *
     * @return nombre de marqueurs ★ poses sur la grille
     */
    public int getNbMarqueursMine() {
        int total = 0;
        int l;
        int c;
        for (l = 0; l < hauteur; l++) {
            for (c = 0; c < largeur; c++) {
                if (cases[l][c].getMarqueur() == Marqueur.MINE) {
                    total++;
                }
            }
        }
        return total;
    }

    /**
     * Retourne la largeur de la grille.
     *
     * @return largeur
     */
    public int getLargeur() {
        return largeur;
    }

    /**
     * Retourne la hauteur de la grille.
     *
     * @return hauteur
     */
    public int getHauteur() {
        return hauteur;
    }

    /**
     * Retourne le nombre total de mines.
     *
     * @return nbMines
     */
    public int getNbMines() {
        return nbMines;
    }

    /**
     * Retourne le nombre de cases revelees (mines comprises).
     *
     * @return nbCasesRevelees
     */
    public int getNbCasesRevelees() {
        return nbCasesRevelees;
    }

    /**
     * Retourne la case aux coordonnees donnees.
     *
     * @param ligne   ligne
     * @param colonne colonne
     * @return la case
     */
    public Case getCase(int ligne, int colonne) {
        if (!estDansBornes(ligne, colonne)) {
            throw new IllegalArgumentException("Coordonnees hors bornes.");
        }
        return cases[ligne][colonne];
    }

    /**
     * Affiche le plateau en ASCII.
     *
     * @param afficherMines true pour debug ou fin de partie
     * @return representation texte
     */
    public String toAscii(boolean afficherMines) {
        StringBuilder sb = new StringBuilder();
        int l;
        int c;

        sb.append("   ");
        for (c = 0; c < largeur; c++) {
            if (c < 10) {
                sb.append(' ');
            }
            sb.append(c).append(' ');
        }
        sb.append('\n');

        for (l = 0; l < hauteur; l++) {
            if (l < 10) {
                sb.append(' ');
            }
            sb.append(l).append(' ');

            for (c = 0; c < largeur; c++) {
                Case cellule = cases[l][c];
                char symbole;

                if (cellule.estRevelee()) {
                    if (cellule.estMinee()) {
                        symbole = '*';
                    } else if (cellule.getMinesAdjacentes() == 0) {
                        symbole = ' ';
                    } else {
                        symbole = (char) ('0' + cellule.getMinesAdjacentes());
                    }
                } else if (cellule.getMarqueur() == Marqueur.MINE) {
                    symbole = 'F';
                } else if (cellule.getMarqueur() == Marqueur.SUSPECT) {
                    symbole = '?';
                } else if (afficherMines && cellule.estMinee()) {
                    symbole = '*';
                } else {
                    symbole = '.';
                }

                sb.append('[').append(symbole).append(']');
            }
            sb.append('\n');
        }
        return sb.toString();
    }
}

