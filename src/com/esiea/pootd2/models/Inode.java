package com.esiea.pootd2.models;

import java.util.ArrayList;
import java.util.List;

public abstract class Inode {

    private List<Inode> subInodes;
    private FolderInode parentInode;
    private int size;
    private String name;
    
    /**
     * Constructor of the Inode
     * @param name The name of the inode
     */
    protected Inode(String name){
        this.name = name;
        this.subInodes = new ArrayList<>();
    }

    /**
     * Get the name
     * @return The name as a string
     */
    public String getName(){
        return this.name;
    }

    /**
     * Get the size
     * @return The size as an int
     */
    public int getSize(){
        return this.size;
    }

    /**
     * Get the parent folder of this inode
     * @return The parent folder as a FolderInode
     */
    public FolderInode getParent(){
        return parentInode;
    }

    /**
     * Set the name
     * @param name The name
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * Set the size
     * @param size The size
     */
    public void setSize(int size){
        this.size = size;
    }

    /**
     * Get the list of all subInodes
     * @return The list of subInodes
     */
    public List<Inode> getSubInodes(){
        return this.subInodes;
    }

    /**
     * Add a subInode
     * @param inode The inode to add
     */
    public void addSubInode(Inode inode) {
        this.subInodes.add(inode);
    }

    /**
     * Set this inode's parent folder
     * @param parentFolder The parent folder
     */
    public void setParentInode(FolderInode parentFolder){
        this.parentInode = parentFolder;
    }

    /**
     * Get a specific inode (parent or child) by his name
     * @param name The name of the Inode to fetch
     * @return The fetched inode or null if not found
     */
    public Inode getSubInode(String name){
        if (name.equals("..")){
            return this.getParent();
        }
        else {
            for (Inode inode : subInodes) {
                if (inode.getName().equals(name)){
                    return inode;
                }
            }
            return null;
        }
    }
}
