package demineur;

public final class Marqueur {
    public static final Marqueur VIDE = new Marqueur("VIDE");
    public static final Marqueur MINE = new Marqueur("MINE");
    public static final Marqueur SUSPECT = new Marqueur("SUSPECT");

    private final String nom;

    private Marqueur(String nom) {
        this.nom = nom;
    }

    public static Marqueur fromString(String texte) {
        if ("MINE".equals(texte)) return MINE;
        if ("SUSPECT".equals(texte)) return SUSPECT;
        return VIDE;
    }

    @Override
    public String toString() {
        return nom;
    }
}
