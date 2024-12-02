package com.esiea.pootd2.models;

public class FolderInode extends Inode{
    public FolderInode(String name)
    {   
        super(name);
        super.setSize(0);
    }

    public void addSubInodes(Inode inode)
    {
        inode.setParentInode(this);
        super.subInodes.add(inode);
        this.updateFolderSize();
    }

    public void updateFolderSize()
    {
        int tmpSize = 0;
        for (Inode inode : super.subInodes) {
            tmpSize+= inode.getSize();
        }
        this.setSize(tmpSize);

        FolderInode parent = super.getParent();
        if (parent != null)
        {
            parent.updateFolderSize();
        }
    }

    public FolderInode getSubFolder(String name)
    {
        Inode matchingInode = this.getSubInode(name);
        if (matchingInode instanceof FolderInode)
        {
            return (FolderInode)matchingInode;
        }
        return null;
    }

    public void displaySubInodes(String prefix) {
        System.out.println(prefix + "|_ " + this.getName() + " " + this.getSize());
        for (Inode inode : this.subInodes) {
            if (inode instanceof FolderInode) {
                ((FolderInode) inode).displaySubInodes(prefix + "    ");
            } else {
                System.out.println(prefix + "    |_ " + inode.getName() + " " + inode.getSize());
            }
        }
    }
}
