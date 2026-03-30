package fr.sae.demineur;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Gere la sauvegarde et le chargement d'une partie de Demineur.
 * Le format du fichier est un texte simple : chaque ligne correspond
 * a une donnee de la partie (dimensions, etat, cases).
 *
 * Format du fichier :
 * <pre>
 * largeur
 * hauteur
 * nbMines
 * etat (EN_COURS, GAGNEE, PERDUE)
 * pour chaque case : estMinee;estRevelee;marqueur;minesAdjacentes
 * </pre>
 *
 * @author Votre Nom
 * @version 1.0
 */
public class Sauvegarde {

    /** Nom du fichier de sauvegarde par defaut. */
    private static final String FICHIER = "sauvegarde.txt";
    /** Nom du fichier contenant le meilleur score (meilleur temps). */
    private static final String FICHIER_SCORE = "meilleur_score.txt";

    /**
     * Sauvegarde l'etat complet d'une partie dans le fichier de sauvegarde.
     *
     * @param partie la partie a sauvegarder
     * @throws IOException en cas d'erreur d'ecriture
     */
    public static void sauvegarder(Partie partie) throws IOException {
        Plateau plateau = partie.getPlateau();
        BufferedWriter writer = new BufferedWriter(new FileWriter(FICHIER));

        writer.write(String.valueOf(plateau.getLargeur()));
        writer.newLine();
        writer.write(String.valueOf(plateau.getHauteur()));
        writer.newLine();
        writer.write(String.valueOf(plateau.getNbMines()));
        writer.newLine();
        writer.write(partie.getEtat().name());
        writer.newLine();

        int l;
        int c;
        for (l = 0; l < plateau.getHauteur(); l++) {
            for (c = 0; c < plateau.getLargeur(); c++) {
                Case cellule = plateau.getCase(l, c);
                writer.write(
                    (cellule.estMinee() ? "1" : "0") + ";" +
                    (cellule.estRevelee() ? "1" : "0") + ";" +
                    cellule.getMarqueur().name() + ";" +
                    cellule.getMinesAdjacentes()
                );
                writer.newLine();
            }
        }

        writer.close();
    }

    /**
     * Charge une partie depuis le fichier de sauvegarde.
     * Reconstruit le plateau case par case sans replacer les mines aleatoirement.
     *
     * @return la partie chargee, ou null si aucune sauvegarde n'existe
     * @throws IOException en cas d'erreur de lecture
     */
    public static Partie charger() throws IOException {
        java.io.File fichier = new java.io.File(FICHIER);
        if (!fichier.exists()) {
            return null;
        }

        BufferedReader reader = new BufferedReader(new FileReader(FICHIER));

        int largeur = Integer.parseInt(reader.readLine().trim());
        int hauteur = Integer.parseInt(reader.readLine().trim());
        int nbMines = Integer.parseInt(reader.readLine().trim());
        EtatPartie etat = EtatPartie.valueOf(reader.readLine().trim());

        Partie partie = new Partie(largeur, hauteur, nbMines);
        Plateau plateau = partie.getPlateau();

        int l;
        int c;
        for (l = 0; l < hauteur; l++) {
            for (c = 0; c < largeur; c++) {
                String[] parts = reader.readLine().trim().split(";");
                Case cellule = plateau.getCase(l, c);

                cellule.setEstMinee("1".equals(parts[0]));
                if ("1".equals(parts[1])) {
                    cellule.reveler();
                }
                appliquerMarqueur(cellule, parts[2]);
                cellule.setMinesAdjacentes(Integer.parseInt(parts[3]));
            }
        }

        reader.close();
        partie.setEtat(etat);
        return partie;
    }

    /**
     * Applique un marqueur a une case a partir de son nom.
     *
     * @param cellule la case cible
     * @param nom     le nom du marqueur (VIDE, MINE, SUSPECT)
     */
    private static void appliquerMarqueur(Case cellule, String nom) {
        Marqueur cible = Marqueur.valueOf(nom);
        while (cellule.getMarqueur() != cible) {
            cellule.changerMarqueur();
        }
    }

    /**
     * Indique si une sauvegarde existe sur le disque.
     *
     * @return true si le fichier de sauvegarde est present
     */
    public static boolean sauvegardeExiste() {
        return new java.io.File(FICHIER).exists();
    }

    /**
     * Supprime le fichier de sauvegarde (apres une partie terminee).
     */
    public static void supprimerSauvegarde() {
        new java.io.File(FICHIER).delete();
    }

    /**
     * Lit le meilleur temps en secondes enregistre sur le disque.
     *
     * @return le meilleur temps, ou -1 si aucun n'est enregistre ou en cas d'erreur
     */
    public static int lireMeilleurScore() {
        java.io.File fichier = new java.io.File(FICHIER_SCORE);
        if (!fichier.exists()) {
            return -1;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(FICHIER_SCORE))) {
            String ligne = reader.readLine();
            if (ligne == null) {
                return -1;
            }
            return Integer.parseInt(ligne.trim());
        } catch (Exception e) {
            return -1;
        }
    }

    /**
     * Enregistre un nouveau meilleur temps sur le disque.
     *
     * @param temps le temps en secondes a enregistrer
     */
    public static void enregistrerMeilleurScore(int temps) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FICHIER_SCORE))) {
            writer.write(String.valueOf(temps));
        } catch (IOException e) {
            // En cas d'erreur d'ecriture, on ignore simplement.
        }
    }
}
