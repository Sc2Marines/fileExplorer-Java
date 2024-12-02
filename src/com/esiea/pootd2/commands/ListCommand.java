package com.esiea.pootd2.commands;

import com.esiea.pootd2.models.FolderInode;

public class ListCommand extends Command {
    private final FolderInode currentFolder;

    public ListCommand(FolderInode currentFolder) {
        this.currentFolder = currentFolder;
    }

    @Override
    public String execute() {
        StringBuilder result = new StringBuilder();
        for (var inode : currentFolder.subInodes) {
            result.append(inode.getName()).append(" ").append(inode.getSize()).append("\n");
        }
        return result.toString();
    }
}
