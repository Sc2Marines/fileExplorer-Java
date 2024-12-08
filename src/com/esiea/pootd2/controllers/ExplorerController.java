package com.esiea.pootd2.controllers;

import com.esiea.pootd2.commands.*;
import com.esiea.pootd2.models.FileInode;
import com.esiea.pootd2.models.FolderInode;
import com.esiea.pootd2.commands.parsers.UnixCommandParser;

public class ExplorerController implements IExplorerController {
    private FolderInode currentFolder;

    /**
     * Contstructor for the file explorer
     */
    public ExplorerController() {
        this.currentFolder = new FolderInode("/");
    }

    /**
     * Create a basiq file and folder architecture
     */
    public void initialiseBaseArchitecture() {
        FolderInode folder1 = new FolderInode("POO");
        FolderInode folder2 = new FolderInode("TD2");
        currentFolder.addSubInodes(folder1);
        folder1.addSubInodes(folder2);
        FileInode file1 = new FileInode("TD1.pdf");
        FileInode file2 = new FileInode("Main.java");
        FileInode file3 = new FileInode("Main.class");
        FileInode bashrc = new FileInode(".bashrc");
        currentFolder.addSubInodes(bashrc);
        folder1.addSubInodes(file1);
        folder2.addSubInodes(file2);
        folder2.addSubInodes(file3);
    }

    /**
     * Execute a command
     * 
     * @param commandStr The command as String
     */
    @Override
    public String executeCommand(String commandStr) {
        Command command = new UnixCommandParser().parse(commandStr, currentFolder);
        AddBashRCCommand saveBashRCCommand = new AddBashRCCommand(currentFolder, commandStr);
        this.doCommand(saveBashRCCommand);

        if (command instanceof ChangeDirectoryCommand) {
            return this.doCommand((ChangeDirectoryCommand) command);
        } else if (command instanceof ErrorCommand) {
            return this.doCommand((ErrorCommand) command);
        } else if (command instanceof SuccessCommand) {
            return this.doCommand((SuccessCommand) command);
        } else if (command instanceof ListCommand) {
            return this.doCommand((ListCommand) command);
        } else if (command instanceof MakeDirectoryCommand) {
            return this.doCommand((MakeDirectoryCommand) command);
        } else if (command instanceof TouchCommand) {
            return this.doCommand((TouchCommand) command);
        } else if (command instanceof TreeCommand) {
            return this.doCommand((TreeCommand) command);
        } else if (command instanceof ClearCommand) {
            return this.doCommand((ClearCommand) command);
        } else if (command instanceof CommandsListCommand) {
            return this.doCommand((CommandsListCommand) command);
        } else if (command instanceof CatCommand) {
            return this.doCommand((CatCommand) command);
        } else if (command instanceof NanoCommand) {
            return this.doCommand((NanoCommand) command);
        } else if (command instanceof AddBashRCCommand) {
            return this.doCommand((AddBashRCCommand) command);
        } else if (command instanceof PwdCommand) {
            return this.doCommand((PwdCommand) command);
        } else {
            return this.doCommand(command);
        }
    }

    /**
     * execute command method
     * 
     * @param cmd The command as Command
     * @return The command output execution as a String
     */
    private String doCommand(Command cmd) {
        return cmd.execute();
    }

    /**
     * execute command method
     * 
     * @param cmd The command as ChangeDirectoryCommand
     * @return The command output execution as a String
     */
    private String doCommand(ChangeDirectoryCommand cmd) {
        String res = cmd.execute();
        if (cmd instanceof ChangeDirectoryCommand) {
            this.currentFolder = (cmd).getNewFolder();
        }
        return res;
    }

    /**
     * execute command method
     * 
     * @param cmd The command as SuccessCommand
     * @return The command output execution as a String
     */
    private String doCommand(SuccessCommand cmd) {
        return cmd.execute();
    }

    /**
     * execute command method
     * 
     * @param cmd The command as ErrorCommand
     * @return The command output execution as a String
     */
    private String doCommand(ErrorCommand cmd) {
        return cmd.execute();
    }

    /**
     * execute command method
     * 
     * @param cmd The command as ListCommand
     * @return The command output execution as a String
     */
    private String doCommand(ListCommand cmd) {
        return cmd.execute();
    }

    /**
     * execute command method
     * 
     * @param cmd The command as MakeDirectoryCommand
     * @return The command output execution as a String
     */
    private String doCommand(MakeDirectoryCommand cmd) {
        return cmd.execute();
    }

    /**
     * execute command method
     * 
     * @param cmd The command as TouchCommand
     * @return The command output execution as a String
     */
    private String doCommand(TouchCommand cmd) {
        return cmd.execute();
    }

    /**
     * execute command method
     * 
     * @param cmd The command as TreeCommand
     * @return The command output execution as a String
     */
    private String doCommand(TreeCommand cmd) {
        return cmd.execute();
    }

    /**
     * execute command method
     * 
     * @param cmd The command as ClearCommand
     * @return The command output execution as a String
     */
    private String doCommand(ClearCommand cmd) {
        return cmd.execute();
    }

    /**
     * execute command method
     * 
     * @param cmd The command as CommandsListCommand
     * @return The command output execution as a String
     */
    private String doCommand(CommandsListCommand cmd) {
        return cmd.execute();
    }

    /**
     * execute command method
     * 
     * @param cmd The command as CatCommand
     * @return The command output execution as a String
     */
    private String doCommand(CatCommand cmd) {
        return cmd.execute();
    }

    /**
     * execute command method
     * 
     * @param cmd The command as NanoCommand
     * @return The command output execution as a String
     */
    private String doCommand(NanoCommand cmd) {
        return cmd.execute();
    }

    /**
     * execute command method
     * 
     * @param cmd The command as AddBashRCCommand
     * @return The command output execution as a String
     */
    private String doCommand(AddBashRCCommand cmd) {
        return cmd.execute();
    }

    /**
     * execute command method
     * 
     * @param cmd The command as PwdCommand
     * @return The command output execution as a String
     */
    private String doCommand(PwdCommand cmd) {
        return cmd.execute();
    }
}