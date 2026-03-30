package fr.sae.demineur;

public class Case {
    private boolean estMinee;
    private boolean estRevelee;
    private Marqueur marqueur;
    private int minesAdjacentes;

    public Case() {
        this.estMinee = false;
        this.estRevelee = false;
        this.marqueur = Marqueur.VIDE;
        this.minesAdjacentes = 0;
    }

    public boolean estMinee() {
        return estMinee;
    }

    public void setEstMinee(boolean estMinee) {
        this.estMinee = estMinee;
    }

    public boolean estRevelee() {
        return estRevelee;
    }

    public void reveler() {
        this.estRevelee = true;
    }

    public Marqueur getMarqueur() {
        return marqueur;
    }

    public void setMarqueur(Marqueur marqueur) {
        this.marqueur = marqueur;
    }

    public void changerMarqueur() {
        if (estRevelee) {
            return;
        }

        if (marqueur == Marqueur.VIDE) {
            marqueur = Marqueur.MINE;
        } else if (marqueur == Marqueur.MINE) {
            marqueur = Marqueur.SUSPECT;
        } else {
            marqueur = Marqueur.VIDE;
        }
    }

    public int getMinesAdjacentes() {
        return minesAdjacentes;
    }

    public void setMinesAdjacentes(int minesAdjacentes) {
        this.minesAdjacentes = minesAdjacentes;
    }
}
