package com.esiea.pootd2.controllers;

import com.esiea.pootd2.commands.*;
import com.esiea.pootd2.models.FolderInode;

public class ExplorerController implements IExplorerController {
    private FolderInode currentFolder;

    public ExplorerController() {
        // Initialiser le dossier racine
        this.currentFolder = new FolderInode("/");
    }

    @Override
    public String executeCommand(String commandStr) {
        String[] parts = commandStr.split(" ");
        String commandName = parts[0];
        String argument = parts.length > 1 ? parts[1] : "";

        Command command;
        switch (commandName) {
            case "ls":
                command = new ListCommand(currentFolder);
                break;
            case "cd":
                ChangeDirectoryCommand cdCommand = new ChangeDirectoryCommand(currentFolder, argument);
                String result = cdCommand.execute();
                currentFolder = cdCommand.getNewFolder(); // Mettre à jour le dossier courant
                return result;
            case "mkdir":
                command = new MakeDirectoryCommand(currentFolder, argument);
                break;
            case "touch":
                command = new TouchCommand(currentFolder, argument);
                break;
            default:
                command = new ErrorCommand("Commande non reconnue: " + commandName);
                break;
        }
        return command.execute();
    }
}
