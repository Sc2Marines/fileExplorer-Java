package com.esiea.pootd2.controllers;

import com.esiea.pootd2.commands.*;
import com.esiea.pootd2.models.FileInode;
import com.esiea.pootd2.models.FolderInode;
import com.esiea.pootd2.commands.parsers.UnixCommandParser;

public class ExplorerController implements IExplorerController {
    private FolderInode currentFolder;

    public ExplorerController() {
        this.currentFolder = new FolderInode("/");
    }

    public void initialiseBaseArchitecture()
    {
        FolderInode folder1 = new FolderInode("POO");
        FolderInode folder2 = new FolderInode("TD2");
        currentFolder.addSubInodes(folder1);
        folder1.addSubInodes(folder2);
        FileInode file1 = new FileInode("TD1.pdf");
        FileInode file2 = new FileInode("Main.java");
        FileInode file3 = new FileInode("Main.class");
        folder1.addSubInodes(file1);
        folder2.addSubInodes(file2);
        folder2.addSubInodes(file3);
    }

    @Override
    public String executeCommand(String commandStr) {
        Command command = new UnixCommandParser().parse(commandStr, currentFolder);
        
        if (command instanceof ChangeDirectoryCommand){
            return this.doCommand((ChangeDirectoryCommand)command);
        }
        else if (command instanceof ErrorCommand){
            return this.doCommand((ErrorCommand)command);
        }
        else if (command instanceof SuccessCommand){
            return this.doCommand((SuccessCommand)command);
        }
        else if (command instanceof ListCommand){
            return this.doCommand((ListCommand)command);
        }
        else if (command instanceof MakeDirectoryCommand){
            return this.doCommand((MakeDirectoryCommand)command);
        }
        else if (command instanceof TouchCommand){
            return this.doCommand((TouchCommand)command);
        }
        else if (command instanceof TreeCommand){
            return this.doCommand((TreeCommand)command);
        }
        else {
            return this.doCommand(command);
        }
    }

    private String doCommand(Command cmd){
        return cmd.execute();
    }

    private String doCommand(ChangeDirectoryCommand cmd){
        String res = cmd.execute();
        if (cmd instanceof ChangeDirectoryCommand){
            this.currentFolder = ((ChangeDirectoryCommand)cmd).getNewFolder();
        }
        return res;
    }
    
    private String doCommand(SuccessCommand cmd){
        return cmd.execute();
    }

    private String doCommand(ErrorCommand cmd){
        return cmd.execute();
    }

    private String doCommand(ListCommand cmd){
        return cmd.execute();
    }

    private String doCommand(MakeDirectoryCommand cmd){
        return cmd.execute();
    }

    private String doCommand(TouchCommand cmd){
        return cmd.execute();
    }

    private String doCommand(TreeCommand cmd) {
        return cmd.execute();
    }
}
