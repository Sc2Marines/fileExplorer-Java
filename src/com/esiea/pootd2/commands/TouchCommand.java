package com.esiea.pootd2.commands;

import java.util.Arrays;
import java.util.List;

import com.esiea.pootd2.models.FileInode;
import com.esiea.pootd2.models.FolderInode;

public class TouchCommand extends Command {
    private final FolderInode currentFolder;
    private final String fileName;

    /**
     * Constructor for the TouchCommand
     * @param currentFolder The current user folder
     * @param fileName The filename path
     */
    public TouchCommand(FolderInode currentFolder, String fileName) {
        this.currentFolder = currentFolder;
        this.fileName = fileName;
    }

    /**
     * Execute the command
     * @return The result of the command as a String
     */
    @Override
    public String execute() {
        return this.createFile().execute();
    }

    /**
     * Create the file at the destination path
     * @return A sucessCommand or an ErrorCommand
     */
    private Command createFile()
    {
        List<String> parsedFilename = Arrays.asList(fileName.trim().split("/"));
        FolderInode travelFolder = currentFolder;
        FileInode newFile = new FileInode(parsedFilename.get(parsedFilename.size()-1));
        SuccessCommand successCommand = new SuccessCommand("File created: " + fileName);
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
            FolderInode travelInode = currentFolder;
            while (!travelInode.getName().equals("/")) {
                travelInode = travelInode.getParent(); 
            }
            return this.createSub(parsedFilename, travelInode, newFile, successCommand);
        }
    }

    /**
     * Add a new file to the destination
     * @param parsedFilename The parsed destination path
     * @param travelFolder The folder used to travel through the file explorer
     * @param newFile The new file to add
     * @param successCommand The sucessCommand to return
     * @return A sucessCommand or an ErrorCommand
     */
    private Command createSub(List<String> parsedFilename, FolderInode travelFolder, FileInode newFile, Command successCommand)
    {
        FolderInode subFolder;
        for (int i = 1; i < parsedFilename.size() - 1; i ++)
            {
                subFolder = travelFolder.getSubFolder(parsedFilename.get(i));
                if (subFolder == null)
                {
                    return new ErrorCommand("Path invalid");
                }
                travelFolder = subFolder;
            }
            travelFolder.addSubInodes(newFile);
            return successCommand;
    }
}
