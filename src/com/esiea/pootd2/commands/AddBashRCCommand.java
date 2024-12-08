package com.esiea.pootd2.commands;

import com.esiea.pootd2.models.FileInode;
import com.esiea.pootd2.models.FolderInode;

public class AddBashRCCommand extends Command{
    
    private FolderInode currentFolder;
    private String cmd;

    public AddBashRCCommand(FolderInode currentFolder, String cmd){
        this.currentFolder = currentFolder;
        this.cmd = cmd;
    }

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
