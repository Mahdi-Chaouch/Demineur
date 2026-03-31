package demineur;

import java.util.Random;

public class Plateau {
    private final int largeur;
    private final int hauteur;
    private final int nbMines;
    private final Case[][] cases;
    private int nbCasesRevelees;

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

        public void basculerMarqueur(int ligne, int colonne) {
        if (!estDansBornes(ligne, colonne)) {
            throw new IllegalArgumentException("Coordonnees hors bornes.");
        }
        cases[ligne][colonne].changerMarqueur();
    }

        public boolean toutesCasesSuresRevelees() {
        return nbCasesRevelees == (largeur * hauteur - nbMines);
    }

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

        public int getLargeur() {
        return largeur;
    }

        public int getHauteur() {
        return hauteur;
    }

        public int getNbMines() {
        return nbMines;
    }

        public int getNbCasesRevelees() {
        return nbCasesRevelees;
    }

        public Case getCase(int ligne, int colonne) {
        if (!estDansBornes(ligne, colonne)) {
            throw new IllegalArgumentException("Coordonnees hors bornes.");
        }
        return cases[ligne][colonne];
    }

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

