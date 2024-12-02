package com.esiea.pootd2.commands;

import com.esiea.pootd2.models.FolderInode;

public class ChangeDirectoryCommand extends Command {
    private final FolderInode currentFolder;
    private final String targetFolder;
    private FolderInode newFolder;

    public ChangeDirectoryCommand(FolderInode currentFolder, String targetFolder) {
        this.currentFolder = currentFolder;
        this.targetFolder = targetFolder;
    }

    @Override
    public String execute() {
        //TODO: refactor this code to handle recursive (cf other methods)
        if ("..".equals(targetFolder)) {
            if (currentFolder.getParent() != null) {
                newFolder = currentFolder.getParent();
                return "Dossier changé: " + newFolder.getName();
            } else {
                return "Erreur: Aucun dossier parent.";
            }
        } else {
            for (var inode : currentFolder.subInodes) {
                if (inode instanceof FolderInode && inode.getName().equals(targetFolder)) {
                    newFolder = (FolderInode) inode;
                    return "Dossier changé: " + newFolder.getName();
                }
            }
            return "Erreur: Dossier non trouvé.";
        }
    }

    public FolderInode getNewFolder() {
        return newFolder != null ? newFolder : currentFolder;
    }
}
