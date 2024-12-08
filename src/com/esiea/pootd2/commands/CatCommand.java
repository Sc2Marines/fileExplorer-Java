package com.esiea.pootd2.commands;

import java.util.Arrays;
import java.util.List;
import com.esiea.pootd2.models.FileInode;
import com.esiea.pootd2.models.FolderInode;

public class CatCommand extends Command {
    private final FolderInode currentFolder;
    private String fileName;
    private static  String fileNotFoundError = "File not Found";

    public CatCommand(FolderInode currentFolder, String fileName) {
        this.currentFolder = currentFolder;
        if (fileName == null) {
            this.fileName = ".";
        } else {
            this.fileName = fileName;
        }
    }

    @Override
    public String execute() {
        return cat();
    }

    private String cat()
    {
        List<String> parsedFolderName = Arrays.asList(fileName.trim().split("/"));
        FolderInode travelFolder = currentFolder;
        if (!parsedFolderName.isEmpty() && parsedFolderName.get(0).equals(".") && parsedFolderName.size() < 3)
        {
            try {
                FileInode targetFile = (FileInode)travelFolder.getSubInode(parsedFolderName.get(1));
                return targetFile.getWholeFile();
            } catch (NullPointerException e) {
                return new ErrorCommand(fileNotFoundError).execute();
            }
        }
        else if (!parsedFolderName.isEmpty() && parsedFolderName.size() < 2)
        {
            try {
                FileInode targetFile = (FileInode)travelFolder.getSubInode(parsedFolderName.get(0));
                return targetFile.getWholeFile();
            } catch (NullPointerException e) {
                return new ErrorCommand(fileNotFoundError).execute();
            }
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
                return new ErrorCommand("Path invalid");
            }
            travelFolder = subFolder;
        }
        return new CatCommand(subFolder, parsedFolderName.get(parsedFolderName.size() - 1));
    }
}
