package com.esiea.pootd2.commands;

import com.esiea.pootd2.models.FolderInode;

public class PwdCommand extends Command {

    FolderInode currentFolder;

    public PwdCommand(FolderInode currentFolder) {
        this.currentFolder = currentFolder;
    }

    @Override
    public String execute(){
        StringBuilder result = new StringBuilder();
        FolderInode travelFolder = currentFolder;
        result.append(travelFolder.getName());
        while (travelFolder.getParent() != null) {
            travelFolder = travelFolder.getParent();
            if (!travelFolder.getName().equals("/")) {
                result.insert(0, "/");
            }
            result.insert(0, travelFolder.getName());
        }
        return result.toString();
    }
}
