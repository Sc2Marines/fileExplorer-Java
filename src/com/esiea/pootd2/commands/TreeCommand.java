package com.esiea.pootd2.commands;

import java.util.Arrays;
import java.util.List;
import com.esiea.pootd2.models.FolderInode;

public class TreeCommand extends Command {
    private final FolderInode currentFolder;
    private String folderName;

    /**
     * Constructor for the treeCommand
     * @param currentFolder The current user folder
     * @param folderName The path where the command must be launched 
     */
    public TreeCommand(FolderInode currentFolder, String folderName) {
        this.currentFolder = currentFolder;
        if (folderName == null) {
            this.folderName = ".";
        } else {
            this.folderName = folderName;
        }
    }

    /**
     * Execute the command
     * @return The result as a string
     */
    @Override
    public String execute() {
        return treeFolder();
    }

    /**
     * Launch the tree command at the specified path
     * @return The result as a String
     */
    private String treeFolder()
    {
        List<String> parsedFolderName = Arrays.asList(folderName.trim().split("/"));
        FolderInode travelFolder = currentFolder;
        if (!parsedFolderName.isEmpty() && parsedFolderName.get(0).equals("."))
        {
            StringBuilder result = new StringBuilder();
            
            result.append(currentFolder.displaySubInodes("", true)).append("\n");
            return result.toString();
        }
        else
        {
            if (folderName.equals("/"))
            {
                while (!travelFolder.getName().equals("/")) {
                    travelFolder = travelFolder.getParent(); 
                }
            }
            
            return this.treeSub(parsedFolderName, travelFolder).execute();
        }
    }

    /**
     * Travel through the file explorer to launch the tree command
     * @param parsedFolderName The parsed path
     * @param travelFolder The travel folder wich is used to travel through the file explorer
     * @return An Error command or a sucessCommand
     */
    private Command treeSub(List<String> parsedFolderName, FolderInode travelFolder)
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
        return new TreeCommand(subFolder, null);
    }
}
