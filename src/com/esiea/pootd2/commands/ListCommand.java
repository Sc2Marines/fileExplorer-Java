package com.esiea.pootd2.commands;

import java.util.Arrays;
import java.util.List;
import com.esiea.pootd2.models.FolderInode;

public class ListCommand extends Command {
    private final FolderInode currentFolder;
    private String folderName;

    public ListCommand(FolderInode currentFolder, String folderName) {
        this.currentFolder = currentFolder;
        if (folderName == null) {
            this.folderName = ".";
        } else {
            this.folderName = folderName;
        }
    }

    @Override
    public String execute() {
        return listFolder();
    }

    private String listFolder()
    {
        List<String> parsedFolderName = Arrays.asList(folderName.trim().split("/"));
        FolderInode travelFolder = currentFolder;
        if (!parsedFolderName.isEmpty() && parsedFolderName.get(0).equals("."))
        {
            StringBuilder result = new StringBuilder();
            for (var inode : currentFolder.subInodes) {
                result.append(inode.getName()).append(" ").append(inode.getSize()).append("\n");
            }
            return result.toString();
        }
        else
        {
            if (folderName.equals("/"))
            {
                travelFolder = currentFolder.getParent();
                while (!travelFolder.getName().equals("/")) {
                    travelFolder = travelFolder.getParent(); 
                }
            }
            
            return this.listSub(parsedFolderName, travelFolder).execute();
        }
    }

    private Command listSub(List<String> parsedFolderName, FolderInode travelFolder)
    {
        FolderInode subFolder = travelFolder;
        for (int i = 1; i < parsedFolderName.size() - 1; i ++)
        {
            subFolder = travelFolder.getSubFolder(parsedFolderName.get(i));
            if (subFolder == null)
            {
                return new ErrorCommand("path invalid");
            }
            travelFolder = subFolder;
        }
        return new ListCommand(subFolder, null);
    }
}
