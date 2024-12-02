package com.esiea.pootd2.commands.parsers;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import com.esiea.pootd2.commands.*;
import com.esiea.pootd2.models.FolderInode;

public class UnixCommandParser implements ICommandParser {
    
    private Map<String, Supplier<Command>> commandMap = new HashMap<>();

    private FolderInode curFolder;
    private List<String> parsedCommand;
    private String arg1 = "";

    public UnixCommandParser() {
        commandMap.put("cd", () -> new ChangeDirectoryCommand(curFolder, arg1));
        commandMap.put("ls", () -> new ListCommand(curFolder, arg1));
        commandMap.put("mkdir", () -> new MakeDirectoryCommand(curFolder, arg1));
        commandMap.put("touch", () -> new TouchCommand(curFolder, arg1));
    }

    public Command parse(String userCommand, FolderInode folderInode)
    {
        this.curFolder = folderInode;
        this.parsedCommand = this.splitArguments(userCommand);
        if (parsedCommand != null && parsedCommand.size() > 1)
        {
            arg1 = parsedCommand.get(1);
        }
        return this.mapCommand();
    }


    private List<String> splitArguments(String userCommand)
    {
        return Arrays.asList(userCommand.trim().split(" "));
    }

    private Command mapCommand() {
        if (parsedCommand == null || parsedCommand.isEmpty()) {
            return new ErrorCommand("no commands provided");
        } else {
            String commandName = parsedCommand.get(0);
            Supplier<Command> commandSupplier = commandMap.get(commandName);
            if (commandSupplier != null) {
                return commandSupplier.get();
            } else {
                return new ErrorCommand("command not found");
            }
        }
    }

}
