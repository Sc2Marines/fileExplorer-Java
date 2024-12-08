package com.esiea.pootd2.commands;

import java.util.Arrays;
import java.util.List;

import com.esiea.pootd2.models.FolderInode;

public class ListCommand extends Command {
    private final FolderInode currentFolder;
    private String folderName;
    private static  String pathInvalidError = "Path invalid";

    /**
     * Constructor for the list command
     * @param currentFolder The folder where the user is located
     * @param folderName The folder to list
     */
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

    /**
     * List the list of inodes present in the current folder
     * @return the list as a string
     */
    private String listFolder(){
        List<String> parsedFolderName = Arrays.asList(folderName.trim().split("/"));
        FolderInode travelFolder = currentFolder;
        if (!parsedFolderName.isEmpty() && parsedFolderName.get(0).equals(".") && parsedFolderName.size() < 3) {
            StringBuilder result = new StringBuilder();
            for (var inode : currentFolder.getSubInodes()) {
                result.append(inode.getName()).append(" ").append(inode.getSize()).append("\n");
            }
            return result.toString();
        }
        else if (!parsedFolderName.isEmpty() && parsedFolderName.size() < 3) {
            StringBuilder result = new StringBuilder();
            if (parsedFolderName.size() == 1){
                travelFolder = travelFolder.getSubFolder(parsedFolderName.get(0));
            }
            else {
                travelFolder = travelFolder.getSubFolder(parsedFolderName.get(1));
            }
            
            if (travelFolder != null){
                for (var inode : travelFolder.getSubInodes()) {
                    result.append(inode.getName()).append(" ").append(inode.getSize()).append("\n");
                }
                return result.toString();
            }
            else {
                return new ErrorCommand(pathInvalidError).execute();
            }
        }
        else{
            if (folderName.equals("/")){
                while (!travelFolder.getName().equals("/")) {
                    travelFolder = travelFolder.getParent(); 
                }
            }
            return this.listSub(parsedFolderName, travelFolder).execute();
        }
    }

    /**
     * Travel through the parsed folder name to get the list of the inodes inside the destination
     * @param parsedFolderName The parsed path 
     * @param travelFolder The current folder used for travelling
     * @return The list as a String
     */
    private Command listSub(List<String> parsedFolderName, FolderInode travelFolder){
        FolderInode subFolder = travelFolder;
        for (int i = 1; i < parsedFolderName.size(); i ++){
            subFolder = travelFolder.getSubFolder(parsedFolderName.get(i));
            if (subFolder == null){
                return new ErrorCommand(pathInvalidError);
            }
            travelFolder = subFolder;
        }
        return new ListCommand(subFolder, null);
    }
}
