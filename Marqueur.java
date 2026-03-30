/**
 * Représente les marqueurs qu'un joueur peut poser sur une case cachée.
 * <ul>
 *   <li>{@link #VIDE} : aucun marqueur</li>
 *   <li>{@link #MINE} : le joueur est certain que la case est minée (★)</li>
 *   <li>{@link #SUSPECT} : le joueur a un simple soupçon (?)</li>
 * </ul>
 *
 * @author Votre Nom
 * @version 1.0
 */
public enum Marqueur {

    /** Aucun marqueur posé sur la case. */
    VIDE,

    /** Marqueur ★ : le joueur est certain que la case est minée. */
    MINE,

    /** Marqueur ? : le joueur soupçonne une mine. */
    SUSPECT
}
