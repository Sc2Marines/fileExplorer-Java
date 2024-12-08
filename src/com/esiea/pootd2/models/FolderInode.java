package com.esiea.pootd2.models;

public class FolderInode extends Inode{

    /**
     * Constructor for the folder
     * @param name The name of the folder
     */
    public FolderInode(String name){  
        super(name);
        super.setSize(0);
    }

    /**
     * Add a subInode to the folder
     * @param inode The inode to add
     */
    public void addSubInodes(Inode inode){
        inode.setParentInode(this);
        super.addSubInode(inode);
        this.updateFolderSize();
    }

    /**
     * Update the folder size by getting all the subInodes sizes
     * When the size is updated, the parent size are being updated
     */
    public void updateFolderSize(){
        int tmpSize = 0;
        for (Inode inode : super.getSubInodes()) {
            tmpSize+= inode.getSize();
        }
        this.setSize(tmpSize);

        FolderInode parent = super.getParent();
        if (parent != null){
            parent.updateFolderSize();
        }
    }

    /**
     * Get a specific file of this folder
     * @param name The name of the file
     * @return The file's inode
     */
    public FileInode getSubFile(String name){
        Inode matchingInode = this.getSubInode(name);
        if (matchingInode instanceof FileInode){
            return (FileInode)matchingInode;
        }
        return null;
    }

    /**
     * Get a specific folder of this folder
     * @param name The name of the folder
     * @return The folder's inode
     */
    public FolderInode getSubFolder(String name){
        Inode matchingInode = this.getSubInode(name);
        if (matchingInode instanceof FolderInode){
            return (FolderInode)matchingInode;
        }
        return null;
    }

    /**
     * Display recursivly all the sub elements of this folder in a tree format
     * @param prefix The prefix to add before each line
     * @param isFirstCall A boolean used for the recusive loop to avoid displaying useless |_ for the first element (must be true when called)
     * @return The tree as a string
     */
    public String displaySubInodes(String prefix, boolean isFirstCall) {
        StringBuilder result = new StringBuilder();
        if (!isFirstCall) {
            result.append(prefix + "|_ ");
        }
        result.append(this.getName() + " " + this.getSize() + "\n");
        for (Inode inode : this.getSubInodes()) {
            if (inode instanceof FolderInode) {
                result.append(((FolderInode) inode).displaySubInodes(prefix + "  ", false));
            } else {
                result.append(prefix + "  |_ " + inode.getName() + " " + inode.getSize() + "\n");
            }
        }
        return result.toString();
    }
}
