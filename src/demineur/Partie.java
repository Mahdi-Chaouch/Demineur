package demineur;

public class Partie {
    private final Plateau plateau;
    private EtatPartie etat;

        public Partie(int largeur, int hauteur, int nbMines) {
        this.plateau = new Plateau(largeur, hauteur, nbMines);
        this.etat = EtatPartie.EN_COURS;
    }

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

        public void marquer(int ligne, int colonne) {
        if (etat != EtatPartie.EN_COURS) {
            return;
        }
        plateau.basculerMarqueur(ligne, colonne);
    }

        public Plateau getPlateau() {
        return plateau;
    }

        public EtatPartie getEtat() {
        return etat;
    }

        public void setEtat(EtatPartie etat) {
        this.etat = etat;
    }
}

