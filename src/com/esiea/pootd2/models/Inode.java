package com.esiea.pootd2.models;

import java.util.ArrayList;
import java.util.List;

abstract class Inode {

    //properties
    protected List<Inode> subInodes;
    private Inode parentInode;
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

    public Inode getParent()
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

    public void setParentInode(Inode inode)
    {
        this.parentInode = inode;
    }



    // others
}
