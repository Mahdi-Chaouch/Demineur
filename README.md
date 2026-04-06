#  SAÉ 2.1 - Démineur en Java

Ce projet est une réimplémentation complète du jeu de Démineur classique en **Java**, réalisé dans le cadre de la **SAÉ 2.1** . 

L'objectif du jeu est de révéler l'ensemble des cases non minées d'une grille sans faire exploser une seule mine, à l'aide d'informations numériques indiquant le nombre de cases adjacentes piégées.

##  Fonctionnalités implémentées

- **Configuration de Partie** : Possibilité de configurer les dimensions de la grille (lignes et colonnes de 4 à 30) ainsi que le nombre de mines.
- **Marquage et Révélation** :
  - **Clic Gauche** : Révèle une case (et révèle automatiquement en cascade les cases adjacentes si le danger est nul).
  - **Clic Droit** : Permet de poser des marqueurs tactiques (★ pour mine certifiée, ? pour soupçon).
- **Interface "Dark Mode" Moderne** : Un rendu plat, aux bords doux et une typographie claire avec des couleurs de chiffres ultra-lumineuses, s'éloignant du vieux look par défaut de Swing.
- **Sauvegarde et Reprise** : Possibilité de sauvegarder une partie en cours en cliquant sur « Sauver et Quitter » ou en fermant la fenêtre. La partie peut être reprise lors du redémarrage sans perte de données.
- **Statistiques en Temps Réel** : Présence d'un chronomètre et d'un compte de mines restantes (mines moins marqueurs) en jeu.
- **Bilan de fin de Partie** : Visualisation claire du résultat (Victoire ou Défaite). En cas de défaite, révélation de la mine qui a explosé, repérage des mines non-détectées par le joueur, et révélation des fausses suspicions (marqueurs placés à tort).

##  Compilation et Exécution

Ce projet suit à la lettre les consignes de l'IUT et peut être compilé et lancé de différentes manières.

### Via le Makefile
Si vous utilisez un système Unix (Linux / macOS) ou un terminal équipé de `make` :

```bash
# Pour compiler l'intégralité du code source
make

# Pour compiler (si besoin) et lancer directement la version graphique (GUI)
make run

# Pour nettoyer les fichiers classes générés
make clean
```

### Via les commandes Java natives (si vous êtes sous Windows sans Make)
Si vous travaillez via Windows PowerShell :

```bash
# Compilation manuelle :
javac -implicit:none -encoding UTF-8 src/jeu/*.java

# Lancement manuel de l'interface graphique :
java -cp src/jeu LanceurGraphique
```

##  Structure Technique

- Aucune librairie tierce n'a été utilisée. Seule l'API `javax.swing` et `java.awt` ont été réquisitionnées, dans le strict contournement des expressions lambdas ou classes anonymes (consignes du projet obligent, tous les EventListeners sont définis classiquement).
- Le patron *Modèle-Vue-Contrôleur* y est partiellement appliqué pour bien isoler la gestion de la `Partie` (EtatPartie, Plateau, Marqueur) des panneaux d'affichages (`PanneauJeu`, `PanneauConfiguration`, `BoutonCase`).

##  Auteurs

- **Mahdi CHAOUCH**
- **Rayan Ishac BELABED** 
