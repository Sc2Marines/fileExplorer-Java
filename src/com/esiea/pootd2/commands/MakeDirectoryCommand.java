package com.esiea.pootd2.commands;

import java.util.Arrays;
import java.util.List;

import com.esiea.pootd2.models.FolderInode;

public class MakeDirectoryCommand extends Command {
    private final FolderInode currentFolder;
    private final String folderName;

    /**
     * Constructor for the MakeDirectoryCommand
     * 
     * @param currentFolder The current user folder
     * @param folderName    The path for the new folder
     */
    public MakeDirectoryCommand(FolderInode currentFolder, String folderName) {
        this.currentFolder = currentFolder;
        this.folderName = folderName;
    }

    /**
     * Execute the command
     * 
     * @return the result of the command
     */
    @Override
    public String execute() {
        return this.createFolder().execute();
    }

    /**
     * Create a folder to the specified destination path
     * 
     * @return The command to execute
     */
    private Command createFolder() {
        List<String> parsedFolderName = Arrays.asList(folderName.trim().split("/"));
        FolderInode travelFolder = currentFolder;
        FolderInode newFolder = new FolderInode(parsedFolderName.get(parsedFolderName.size() - 1));
        SuccessCommand successCommand = new SuccessCommand("Folder created: " + folderName);
        if (parsedFolderName.get(0).equals(".")) {
            return this.createSub(parsedFolderName, travelFolder, newFolder, successCommand);
        } else if (!parsedFolderName.get(0).equals("")) {
            currentFolder.addSubInodes(newFolder);
            return successCommand;
        } else {
            FolderInode travelInode = currentFolder;

            while (!travelInode.getName().equals("/")) {
                travelInode = travelInode.getParent();
            }
            return this.createSub(parsedFolderName, travelInode, newFolder, successCommand);
        }
    }

    /**
     * Create a sub inode to the destination
     * 
     * @param parsedFolderName The parsed destination path
     * @param travelFolder The travelling folder
     * @param newFolder The new folder to add
     * @param successCommand The command if the folder is successfully created
     * @return A success or an error command
     */
    private Command createSub(List<String> parsedFolderName, FolderInode travelFolder, FolderInode newFolder,
            Command successCommand) {
        FolderInode subFolder;
        for (int i = 1; i < parsedFolderName.size() - 1; i++) {
            subFolder = travelFolder.getSubFolder(parsedFolderName.get(i));
            if (subFolder == null) {
                return new ErrorCommand("Path invalid");
            }
            travelFolder = subFolder;
        }
        travelFolder.addSubInodes(newFolder);
        return successCommand;
    }
}
