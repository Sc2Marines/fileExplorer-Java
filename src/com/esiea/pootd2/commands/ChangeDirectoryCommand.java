package com.esiea.pootd2.commands;

import java.util.Arrays;
import java.util.List;

import com.esiea.pootd2.models.FolderInode;

public class ChangeDirectoryCommand extends Command {
    private final FolderInode currentFolder;
    private final String targetFolder;
    private FolderInode newFolder;
    private static final String successMessage = "Folder changed";

    /**
     * Constructor for ChangeDirectoryCommand
     * @param currentFolder The current user folder
     * @param targetFolder The destination folder path 
     */
    public ChangeDirectoryCommand(FolderInode currentFolder, String targetFolder) {
        this.currentFolder = currentFolder;
        this.targetFolder = targetFolder;
    }

    /**
     * Execute the command
     */
    @Override
    public String execute() {
        return this.changeFolder();
    }

    /**
     * Change the folder to the destination folder
     * @return The result of the operation
     */
    private String changeFolder(){
        List<String> parsedFolderName = Arrays.asList(targetFolder.trim().split("/"));
        FolderInode travelFolder = currentFolder;
        if (!parsedFolderName.isEmpty() && parsedFolderName.get(0).equals(".") && parsedFolderName.size() < 2){
            newFolder = travelFolder;
            return new SuccessCommand(successMessage).execute();
        }
        else if (!parsedFolderName.isEmpty() && parsedFolderName.size() < 2){
            FolderInode tmpFolder = currentFolder.getSubFolder(parsedFolderName.get(0)); 
            if (tmpFolder != null){
                newFolder = tmpFolder;
                return new SuccessCommand(successMessage).execute();
            }
            else {
                return new ErrorCommand("Folder not Found").execute();
            }
        }
        else{
            if (parsedFolderName.isEmpty() || parsedFolderName.get(0).equals("")){
                while (!travelFolder.getName().equals("/")) {
                    travelFolder = travelFolder.getParent(); 
                }
            }
            if (parsedFolderName.isEmpty()){
                newFolder = travelFolder;
                return new SuccessCommand(successMessage).execute();
            }
            else {
                Command cmd = this.listSub(parsedFolderName, travelFolder);
                String ret = cmd.execute();
                newFolder = ((ChangeDirectoryCommand)cmd).getNewFolder();
                return ret;
            }
        }
    }

    /**
     * Set the travelFolder (this folder is the parent folder of the folder to go)
     * @param parsedFolderName The parsed folder name
     * @param travelFolder  The folder to travel through the file explorer
     * @return ChangeDirectoryCommand or ErrorCommand
     */
    private Command listSub(List<String> parsedFolderName, FolderInode travelFolder)
    {
        FolderInode subFolder = travelFolder;
        for (int i = 1; i < parsedFolderName.size(); i ++)
        {
            subFolder = travelFolder.getSubFolder(parsedFolderName.get(i));
            if (subFolder == null){
                return new ErrorCommand("Path invalid");
            }
            travelFolder = subFolder;
        }
        return new ChangeDirectoryCommand(subFolder, ".");
    }

    /**
     * Get the new folder
     * @return the new folder or the current folder if not set
     */
    public FolderInode getNewFolder() {
        return newFolder != null ? newFolder : currentFolder;
    }
}
