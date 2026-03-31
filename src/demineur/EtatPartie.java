package demineur;

public final class EtatPartie {
    public static final EtatPartie EN_COURS = new EtatPartie("EN_COURS");
    public static final EtatPartie GAGNEE = new EtatPartie("GAGNEE");
    public static final EtatPartie PERDUE = new EtatPartie("PERDUE");

    private final String nom;

    private EtatPartie(String nom) {
        this.nom = nom;
    }

    public static EtatPartie fromString(String texte) {
        if ("GAGNEE".equals(texte)) return GAGNEE;
        if ("PERDUE".equals(texte)) return PERDUE;
        return EN_COURS;
    }

    @Override
    public String toString() {
        return nom;
    }
}
