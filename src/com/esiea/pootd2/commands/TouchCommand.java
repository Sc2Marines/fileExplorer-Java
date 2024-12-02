package com.esiea.pootd2.commands;

import com.esiea.pootd2.models.FileInode;
import com.esiea.pootd2.models.FolderInode;

public class TouchCommand extends Command {
    private final FolderInode currentFolder;
    private final String fileName;

    public TouchCommand(FolderInode currentFolder, String fileName) {
        this.currentFolder = currentFolder;
        this.fileName = fileName;
    }

    @Override
    public String execute() {
        FileInode newFile = new FileInode(fileName);
        currentFolder.addSubInodes(newFile);
        return "Fichier créé: " + fileName;
    }
}
