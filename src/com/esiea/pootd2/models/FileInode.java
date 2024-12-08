package com.esiea.pootd2.models;

import java.util.Random;
import java.util.List;
import java.util.ArrayList;
public class FileInode extends Inode {
    
    private List<String> fileContent;

    /**
     * The constructor of a file
     * @param name The name of the file
     */
    public FileInode(String name){
        super(name);
        Random rd = new Random();
        int size = rd.nextInt(100000) + 1;
        super.setSize(size);
    }

    /**
     * Save a line of text
     * @param line The line of text to save
     */
    public void saveLine(String line){
        if (this.fileContent == null){
            this.fileContent = new ArrayList<>();
        }
        this.fileContent.add(line);
    }

    /**
     * Clear the file content
     */
    public void clearFileContent()
    {
        if (this.fileContent != null)
        {
            fileContent.clear();
        }
    }

    /**
     * Get a specific line of text of the file
     * @param numLine The number of the line to fetch  
     * @return  The fetched line
     */
    public String getLine(Integer numLine){
        if (this.fileContent != null){
            if (fileContent.size() >= numLine){
                return fileContent.get(numLine-1);
            }
            else {
                return null;
            }
        }
        else {
            return null;
        }
    }

    /**
     * Get the whole file content
     * @return The whole file content as a string
     */
    public String getWholeFile(){
        if (this.fileContent == null){
            return "";
        }
        else {
            StringBuilder result = new StringBuilder();
            for (String line : this.fileContent) {
                result.append(line + "\n");
            }
            return result.toString().substring(0, result.length() - 1);
        }

    }
}
