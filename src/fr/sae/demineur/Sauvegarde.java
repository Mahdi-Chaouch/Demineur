package fr.sae.demineur;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Sauvegarde {
    private static final String FICHIER = "sauvegarde.txt";
    private static final String FICHIER_SCORE = "meilleur_score.txt";

    public static void sauvegarder(Partie partie) throws IOException {
        Plateau plateau = partie.getPlateau();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FICHIER))) {
            writer.write(String.valueOf(plateau.getLargeur()));
            writer.newLine();
            writer.write(String.valueOf(plateau.getHauteur()));
            writer.newLine();
            writer.write(String.valueOf(plateau.getNbMines()));
            writer.newLine();
            writer.write(partie.getEtat().toString());
            writer.newLine();

            for (int l = 0; l < plateau.getHauteur(); l++) {
                for (int c = 0; c < plateau.getLargeur(); c++) {
                    Case cellule = plateau.getCase(l, c);
                    writer.write(
                        (cellule.estMinee() ? "1" : "0") + ";" +
                        (cellule.estRevelee() ? "1" : "0") + ";" +
                        cellule.getMarqueur().toString() + ";" +
                        cellule.getMinesAdjacentes()
                    );
                    writer.newLine();
                }
            }
        }
    }

    public static Partie charger() throws IOException {
        File fichier = new File(FICHIER);
        if (!fichier.exists()) {
            return null;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(FICHIER))) {
            int largeur = Integer.parseInt(reader.readLine().trim());
            int hauteur = Integer.parseInt(reader.readLine().trim());
            int nbMines = Integer.parseInt(reader.readLine().trim());
            EtatPartie etat = EtatPartie.fromString(reader.readLine().trim());

            Partie partie = new Partie(largeur, hauteur, nbMines);
            Plateau plateau = partie.getPlateau();

            for (int l = 0; l < hauteur; l++) {
                for (int c = 0; c < largeur; c++) {
                    String[] parts = reader.readLine().trim().split(";");
                    Case cellule = plateau.getCase(l, c);

                    cellule.setEstMinee("1".equals(parts[0]));
                    if ("1".equals(parts[1])) {
                        cellule.reveler();
                    }
                    cellule.setMarqueur(Marqueur.fromString(parts[2]));
                    cellule.setMinesAdjacentes(Integer.parseInt(parts[3]));
                }
            }
            partie.setEtat(etat);
            return partie;
        }
    }

    public static boolean sauvegardeExiste() {
        return new File(FICHIER).exists();
    }

    public static void supprimerSauvegarde() {
        new File(FICHIER).delete();
    }

    public static int lireMeilleurScore() {
        File fichier = new File(FICHIER_SCORE);
        if (!fichier.exists()) {
            return -1;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(FICHIER_SCORE))) {
            String ligne = reader.readLine();
            return ligne == null ? -1 : Integer.parseInt(ligne.trim());
        } catch (Exception e) {
            return -1;
        }
    }

    public static void enregistrerMeilleurScore(int temps) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FICHIER_SCORE))) {
            writer.write(String.valueOf(temps));
        } catch (IOException ignored) {
        }
    }
}
