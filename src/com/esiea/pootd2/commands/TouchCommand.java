package com.esiea.pootd2.commands;

import java.util.Arrays;
import java.util.List;

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
        return this.createFile().execute();
    }

    private Command createFile()
    {
        List<String> parsedFilename = Arrays.asList(fileName.trim().split("/"));
        FolderInode travelFolder = currentFolder;
        FileInode newFile = new FileInode(parsedFilename.get(parsedFilename.size()-1));
        ErrorCommand successCommand = new ErrorCommand("Fichier créé: " + fileName);
        if (parsedFilename.get(0).equals("."))
        {
            return this.createSub(parsedFilename, travelFolder, newFile, successCommand);
        }
        else if (!parsedFilename.get(0).equals("")) 
        {
            currentFolder.addSubInodes(newFile);
            return successCommand;
        }
        else 
        {
            FolderInode travelInode = currentFolder.getParent();
            while (!travelInode.getName().equals("/")) {
                travelInode = travelInode.getParent(); 
            }
            return this.createSub(parsedFilename, travelInode, newFile, successCommand);
        }
    }

    private Command createSub(List<String> parsedFilename, FolderInode travelFolder, FileInode newFile, Command successCommand)
    {
        FolderInode subFolder;
        for (int i = 1; i < parsedFilename.size() - 1; i ++)
            {
                subFolder = travelFolder.getSubFolder(parsedFilename.get(i));
                if (subFolder == null)
                {
                    return new ErrorCommand("path invalid");
                }
                travelFolder = subFolder;
            }
            travelFolder.addSubInodes(newFile);
            return successCommand;
    }
}
