package demineur;

import java.util.Scanner;

public class LanceurConsole {
    public static void main(String[] args) {
        Partie partie = new Partie(9, 9, 10);
        Scanner scanner = new Scanner(System.in);

        while (partie.getEtat() == EtatPartie.EN_COURS) {
            System.out.println(partie.getPlateau().toAscii(false));
            System.out.println("Commande: r ligne colonne | m ligne colonne | q");
            System.out.print("> ");

            String commande = scanner.next();
            if ("q".equals(commande)) {
                break;
            }

            try {
                int ligne = scanner.nextInt();
                int colonne = scanner.nextInt();

                if ("r".equals(commande)) {
                    partie.reveler(ligne, colonne);
                } else if ("m".equals(commande)) {
                    partie.marquer(ligne, colonne);
                } else {
                    System.out.println("Commande inconnue.");
                }
            } catch (Exception e) {
                System.out.println("Entree invalide.");
                scanner.nextLine();
            }
        }

        System.out.println();
        if (partie.getEtat() == EtatPartie.GAGNEE) {
            System.out.println("Victoire !");
        } else if (partie.getEtat() == EtatPartie.PERDUE) {
            System.out.println("Defaite !");
        } else {
            System.out.println("Partie arretee.");
        }

        System.out.println(partie.getPlateau().toAscii(true));
        scanner.close();
    }
}

