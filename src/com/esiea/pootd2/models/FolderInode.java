package com.esiea.pootd2.models;

public class FolderInode extends Inode{
    
    public FolderInode(String name)
    {   
        super(name);
        super.setSize(0);
    }

    public void addSubInodes(Inode inode)
    {
        super.subInodes.add(inode);
    }
}
