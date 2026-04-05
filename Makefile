# Makefile principal (Redirection vers le répertoire source)

.PHONY: all run console clean help

# Cible par défaut
all:
	@$(MAKE) -C src/jeu all

# Lancer la version graphique
run:
	@$(MAKE) -C src/jeu run

# Lancer la version console
console:
	@$(MAKE) -C src/jeu console

# Nettoyer les fichiers compilés
clean:
	@$(MAKE) -C src/jeu clean

# Aide
help:
	@echo "Cibles disponibles :"
	@echo "  make        : Compile le projet"
	@echo "  make run    : Lance la version graphique (GUI)"
	@echo "  make console: Lance la version console"
	@echo "  make clean  : Supprime les fichiers compilés"
