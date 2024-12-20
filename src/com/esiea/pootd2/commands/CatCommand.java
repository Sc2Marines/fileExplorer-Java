package com.esiea.pootd2.commands;

import java.util.Arrays;
import java.util.List;
import com.esiea.pootd2.models.FileInode;
import com.esiea.pootd2.models.FolderInode;

public class CatCommand extends Command {
    private final FolderInode currentFolder;
    private String fileName;
    private static  String fileNotFoundError = "File not Found";

    /**
     * Constructor for the CatCommand, define the filename
     * @param currentFolder The current user folder
     * @param fileName The filename to cat
     */
    public CatCommand(FolderInode currentFolder, String fileName) {
        this.currentFolder = currentFolder;
        if (fileName == null) {
            this.fileName = ".";
        } else {
            this.fileName = fileName;
        }
    }

    /**
     * Execute the cat command
     */
    @Override
    public String execute() {
        return cat();
    }

    /**
     * Get the file content by traveling through the folders 
     * @return The file content or ErrorCommands
     */
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

    /**
     * Set the travelFolder (this folder is th eparent folder of the file to cat)
     * @param parsedFolderName The parsed folder name
     * @param travelFolder  The folder to travel through the file explorer
     * @return CatCommand or ErrorCommand
     */
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
