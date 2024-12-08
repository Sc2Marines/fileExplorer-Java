package com.esiea.pootd2.commands;

import com.esiea.pootd2.models.FolderInode;

public class PwdCommand extends Command {

    FolderInode currentFolder;

    /**
     * Constructor for the pwdCommand
     * @param currentFolder The current user folder
     */
    public PwdCommand(FolderInode currentFolder) {
        this.currentFolder = currentFolder;
    }

    /**
     * execute the pwd command
     * Travel back to the root folder and building the path to the current folder
     */
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
