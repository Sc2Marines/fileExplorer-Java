package com.esiea.pootd2.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import com.esiea.pootd2.models.FileInode;
import com.esiea.pootd2.models.FolderInode;

public class NanoCommand extends Command {

    private final FolderInode currentFolder;
    private String fileName;
    private static  String fileNotFoundError = "File not Found";

    public NanoCommand(FolderInode currentFolder, String fileName) {
        this.currentFolder = currentFolder;
        if (fileName == null) {
            this.fileName = ".";
        } else {
            this.fileName = fileName;
        }
    }

    @Override
    public String execute() {
        return nano();
    }

    private String nano() {
        List<String> parsedFolderName = Arrays.asList(fileName.trim().split("/"));
        FolderInode travelFolder = currentFolder;
        if (!parsedFolderName.isEmpty() && parsedFolderName.get(0).equals(".") && parsedFolderName.size() < 3) {
            FileInode targetFile = travelFolder.getSubFile(parsedFolderName.get(1));
            if (targetFile != null) {
                return editFile(targetFile);
            }
            else {
                return new ErrorCommand(fileNotFoundError).execute();
            }
        } else if (!parsedFolderName.isEmpty() && parsedFolderName.size() < 2) {
            
            FileInode targetFile = travelFolder.getSubFile(parsedFolderName.get(0));
            if (targetFile != null) {
                return editFile(targetFile);
            }
            else {
                return new ErrorCommand(fileNotFoundError).execute();
            }
        } else {
            if (fileName.equals("/")) {
                travelFolder = currentFolder.getParent();
                while (!travelFolder.getName().equals("/")) {
                    travelFolder = travelFolder.getParent();
                }
            }

            return this.nanoSub(parsedFolderName, travelFolder).execute();
        }
    }

    private Command nanoSub(List<String> parsedFolderName, FolderInode travelFolder) {
        FolderInode subFolder = travelFolder;
        for (int i = 1; i < parsedFolderName.size() - 1; i++) {
            subFolder = travelFolder.getSubFolder(parsedFolderName.get(i));
            if (subFolder == null) {
                return new ErrorCommand("Path invalid");
            }
            travelFolder = subFolder;
        }
        return new NanoCommand(subFolder, parsedFolderName.get(parsedFolderName.size() - 1));
    }

    private String editFile(FileInode targetFile) {
        try (Scanner scanner = new Scanner(System.in)) {
            List<String> editedContent = new ArrayList<>();
            System.out.print("\033[H\033[2J");
            System.out.println("Editing file: " + targetFile.getName());
            
            System.out.println("Enter new content \n(type \n':awq' to add and quit,\n ':owq' to overwrite and quit, \n ':q' to quit \n): \n");
            System.out.println(targetFile.getWholeFile());
            String operator;
            while (true) {
                String line = scanner.nextLine();
                if (":awq".equalsIgnoreCase(line) || ":owq".equalsIgnoreCase(line) || ":q".equalsIgnoreCase(line)) {
                    operator = line;
                    break;
                }
                editedContent.add(line);
            }

            if (":owq".equalsIgnoreCase(operator)){
                targetFile.clearFileContent();
            }
            
            if (!":q".equalsIgnoreCase(operator))
            {
                for (int i = 0; i < editedContent.size(); i++) {
                    String line = editedContent.get(i);
                    if (i == editedContent.size() - 1 && line.endsWith("\n")) {
                        line = line.substring(0, line.length() - 1);
                    }
                    targetFile.saveLine(line);
                }
                return "File saved successfully.";
            }
        }
        return "File exited";
    }
}
