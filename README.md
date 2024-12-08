**Réalisé par GAUTIER Gabriel et FOLL Alexandre**


# Explorateur de Fichiers Java (TD2)

Ce projet Java simule un explorateur de fichiers avec une interface en ligne de commande, s'inspirant des commandes Unix. Explorez et manipulez une arborescence virtuelle de fichiers et de dossiers.

## Fonctionnalités

* **Navigation :** `cd <chemin>` (changer de répertoire), `pwd` (afficher le chemin courant).  Supporte les chemins relatifs et absolus, ainsi que `.` et `..`.
* **Liste du contenu :** `ls <chemin>` (lister les fichiers et dossiers d'un répertoire).
* **Création :** `mkdir <chemin>` (créer un dossier), `touch <chemin>` (créer un fichier). Les chemins peuvent inclure des dossiers intermédiaires qui seront créés si nécessaire.
* **Affichage de l'arborescence :** `tree <chemin>` (afficher l'arborescence des fichiers et dossiers à partir d'un répertoire donné).
* **Deux interfaces :**  L'application est conçue pour supporter une interface texte et une interface web (HTTP), bien que cette dernière ne soit pas encore pleinement implémentée (certaines commandes ne sont pas supportés par l'interface web).


## Exécution avec VS Code

**Prérequis :** Java 17 LTS, extension Java pour VS Code. Maven ou Gradle si utilisé.

**1. Compilation :**

* **Maven :** `mvn clean compile` dans le terminal.
* **Gradle :** `gradlew clean build`.
* **Manuelle :** Dans un terminal ouvert à la racine du projet (`fileExplorer-Java-develop\fileExplorer-Java-develop\`), utilisez :
 ```bash
	javac -d bin src/com/esiea/pootd2/*.java src/com/esiea/pootd2/commands/*.java src/com/esiea/pootd2/commands/parsers/*.java src/com/esiea/pootd2/controllers/*.java src/com/esiea/pootd2/interfaces/*.java src/com/esiea/pootd2/models/*.java
```

**2. Exécution :**

* **VS Code :** Ouvrez `ExplorerApp.java` et cliquez sur "Run".
* **Terminal (Maven/Gradle) :** Commande appropriée (ex: `mvn exec:java`, `gradlew run`).
* **Terminal (manuel) :**  Après compilation manuelle, dans le même répertoire que l'étape de compilation manuelle, exécutez :
```bash
	java -cp bin com.esiea.pootd2.ExplorerApp [cli|web]
```


## Améliorations Apportées

* Ajout de la commande `tree <chemin>` (liste sous forme d'arbre toute l'architecture des fichiers et dossiers à partir d'un répertoire donné).
* Ajout de la commande `cmds` (affiche toutes les commandes disponibles pour l'uitilisateur).
* Ajout de la commande `clear` (efface le terminal, attention ne fonctionne que sur l'interface cli).
* Ajout de la commande `cat` (affiche le contenu d'un fichier).
* Ajout de la commande `nano` (permet de modifier le contenu d'un fichier).
* Ajout de la commande `pwd` (affiche le chemin courant).
