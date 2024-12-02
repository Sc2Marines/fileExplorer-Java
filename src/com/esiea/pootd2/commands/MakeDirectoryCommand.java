package com.esiea.pootd2.commands;

import com.esiea.pootd2.models.FolderInode;

public class MakeDirectoryCommand extends Command {
    private final FolderInode currentFolder;
    private final String folderName;

    public MakeDirectoryCommand(FolderInode currentFolder, String folderName) {
        this.currentFolder = currentFolder;
        this.folderName = folderName;
    }

    @Override
    public String execute() {
        FolderInode newFolder = new FolderInode(folderName);
        currentFolder.addSubInodes(newFolder);
        return "Dossier créé: " + folderName;
    }
}
