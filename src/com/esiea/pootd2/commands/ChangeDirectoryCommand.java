package com.esiea.pootd2.commands;

import java.util.Arrays;
import java.util.List;

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
        return this.changeFolder();
    }

    private String changeFolder()
    {
        List<String> parsedFolderName = Arrays.asList(targetFolder.trim().split("/"));
        FolderInode travelFolder = currentFolder;
        if (!parsedFolderName.isEmpty() && parsedFolderName.get(0).equals(".") && parsedFolderName.size() < 2)
        {
            newFolder = travelFolder;
            return "Folder changed";
        }
        else
        {
            if (parsedFolderName.get(0).equals(""))
            {
                travelFolder = currentFolder.getParent();
                while (!travelFolder.getName().equals("/")) {
                    travelFolder = travelFolder.getParent(); 
                }
            }
            Command  cmd= this.listSub(parsedFolderName, travelFolder);
            String ret = cmd.execute();
            newFolder = ((ChangeDirectoryCommand)cmd).getNewFolder();
            return ret;
        }
    }

    private Command listSub(List<String> parsedFolderName, FolderInode travelFolder)
    {
        FolderInode subFolder = travelFolder;
        for (int i = 1; i < parsedFolderName.size(); i ++)
        {
            subFolder = travelFolder.getSubFolder(parsedFolderName.get(i));
            if (subFolder == null)
            {
                return new ErrorCommand("path invalid");
            }
            travelFolder = subFolder;
        }
        return new ChangeDirectoryCommand(subFolder, ".");
    }

    public FolderInode getNewFolder() {
        return newFolder != null ? newFolder : currentFolder;
    }
}
