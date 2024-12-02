package com.esiea.pootd2.models;

import java.util.ArrayList;
import java.util.List;

public abstract class Inode {

    //properties
    public List<Inode> subInodes;
    private FolderInode parentInode;
    private int size;
    private String name;
    

    //constructor
    protected Inode(String name)
    {
        this.name = name;
        this.subInodes = new ArrayList<>();
    }

    //getters
    public String getName()
    {
        return this.name;
    }

    public int getSize()
    {
        return this.size;
    }

    public FolderInode getParent()
    {
        return parentInode;
    }

    //setters 
    public void setName(String name)
    {
        this.name = name;
    }

    public void setSize(int size)
    {
        this.size = size;
    }

    public void setParentInode(FolderInode parentFolder)
    {
        this.parentInode = parentFolder;
    }

    public Inode getSubInode(String name)
    {
        if (name.equals(".."))
        {
            return this.getParent();
        }
        else 
        {
            for (Inode inode : subInodes) {
                if (inode.getName().equals(name))
                {
                    return inode;
                }
            }
            return null;
        }
    }
}
