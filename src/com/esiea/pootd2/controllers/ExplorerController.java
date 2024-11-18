package com.esiea.pootd2.controllers;

public class ExplorerController implements IExplorerController {

    @Override
    public String executeCommand(String commandStr) {
        // Implémentation basique pour tester
        switch (commandStr) {
            case "ls":
                return "Aucun fichier trouvé.";
            case "mkdir":
                return "Dossier créé.";
            case "touch":
                return "Fichier créé.";
            case "cd":
                return "Changement de dossier.";
            default:
                return "Commande non reconnue.";
        }
    }
}
