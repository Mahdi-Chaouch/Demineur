/**
 * Représente les marqueurs qu'un joueur peut poser sur une case cachée.
 * <ul>
 *   <li>{@link #VIDE} : aucun marqueur</li>
 *   <li>{@link #MINE} : le joueur est certain que la case est minée (★)</li>
 *   <li>{@link #SUSPECT} : le joueur a un simple soupçon (?)</li>
 * </ul>
 */
public class Marqueur {

    /** Aucun marqueur posé sur la case. */
    public static final int VIDE = 0;

    /** Marqueur ★ : le joueur est certain que la case est minée. */
    public static final int MINE = 1;

    /** Marqueur ? : le joueur soupçonne une mine. */
    public static final int SUSPECT = 2;
}
