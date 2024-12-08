package com.esiea.pootd2.commands;

import com.esiea.pootd2.models.FileInode;
import com.esiea.pootd2.models.FolderInode;

public class AddBashRCCommand extends Command{
    
    private FolderInode currentFolder;
    private String cmd;

    /**
     * Constructor for AddbashRCCommand
     * @param currentFolder The current folder of the user
     * @param cmd The user command
     */
    public AddBashRCCommand(FolderInode currentFolder, String cmd){
        this.currentFolder = currentFolder;
        this.cmd = cmd;
    }

    /**
     * Store in the bashrc file the user command
     */
    @Override
    public String execute() {
        FolderInode travelFolder = currentFolder;
        if (!travelFolder.getName().equals("/"))
        {
            while (!travelFolder.getName().equals("/")) {
                travelFolder = travelFolder.getParent(); 
            }
        }
        FileInode bashrc = travelFolder.getSubFile(".bashrc");
        bashrc.saveLine(cmd);
        return "";
    }
}
