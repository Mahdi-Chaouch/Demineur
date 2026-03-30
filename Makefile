# Makefile pour le Demineur

# Compilateur Java
JAVAC = javac
JAVA = java

# Options de compilation
JAVAC_FLAGS = -implicit:none -encoding UTF-8

# Source et classes
SOURCES = Case.java Marqueur.java EtatPartie.java Plateau.java Partie.java \
          Sauvegarde.java LanceurConsole.java \
          LanceurGraphique.java FenetrePrincipale.java PanneauMenu.java \
          PanneauConfiguration.java BoutonCase.java PanneauJeu.java PanneauFin.java

CLASSES = $(SOURCES:.java=.class)

# Dependances des fichiers
Case.class: Case.java Marqueur.class
	$(JAVAC) $(JAVAC_FLAGS) Case.java

Marqueur.class: Marqueur.java
	$(JAVAC) $(JAVAC_FLAGS) Marqueur.java

EtatPartie.class: EtatPartie.java
	$(JAVAC) $(JAVAC_FLAGS) EtatPartie.java

Plateau.class: Plateau.java Case.class Marqueur.class
	$(JAVAC) $(JAVAC_FLAGS) Plateau.java

Partie.class: Partie.java Plateau.class EtatPartie.class
	$(JAVAC) $(JAVAC_FLAGS) Partie.java

Sauvegarde.class: Sauvegarde.java Partie.class
	$(JAVAC) $(JAVAC_FLAGS) Sauvegarde.java

LanceurConsole.class: LanceurConsole.java Partie.class EtatPartie.class
	$(JAVAC) $(JAVAC_FLAGS) LanceurConsole.java

FenetrePrincipale.class: FenetrePrincipale.java Partie.class
	$(JAVAC) $(JAVAC_FLAGS) FenetrePrincipale.java

PanneauMenu.class: PanneauMenu.java FenetrePrincipale.class Partie.class Sauvegarde.class
	$(JAVAC) $(JAVAC_FLAGS) PanneauMenu.java

PanneauConfiguration.class: PanneauConfiguration.java FenetrePrincipale.class Partie.class
	$(JAVAC) $(JAVAC_FLAGS) PanneauConfiguration.java

BoutonCase.class: BoutonCase.java Case.class Marqueur.class
	$(JAVAC) $(JAVAC_FLAGS) BoutonCase.java

PanneauJeu.class: PanneauJeu.java FenetrePrincipale.java Partie.class Plateau.class \
                  BoutonCase.class Marqueur.class Sauvegarde.class
	$(JAVAC) $(JAVAC_FLAGS) PanneauJeu.java

PanneauFin.class: PanneauFin.java FenetrePrincipale.java Partie.class EtatPartie.class \
                  BoutonCase.class Marqueur.class
	$(JAVAC) $(JAVAC_FLAGS) PanneauFin.java

LanceurGraphique.class: LanceurGraphique.java FenetrePrincipale.class
	$(JAVAC) $(JAVAC_FLAGS) LanceurGraphique.java

# Cibles principales

.PHONY: all run console clean

# Cible par defaut
all: $(CLASSES)
	@echo "Compilation reussie!"

# Lancer la version graphique
run: all
	$(JAVA) LanceurGraphique

# Lancer la version console
console: all
	$(JAVA) LanceurConsole

# Nettoyer les fichiers compiles
clean:
	rm -f *.class sauvegarde.txt
	@echo "Nettoyage effectue."

# Aide
help:
	@echo "Cibles disponibles:"
	@echo "  make        : Compile le projet"
	@echo "  make run    : Lance la version graphique (GUI)"
	@echo "  make console: Lance la version console"
	@echo "  make clean  : Supprime les fichiers compiles"
	@echo "  make help   : Affiche cette aide"

