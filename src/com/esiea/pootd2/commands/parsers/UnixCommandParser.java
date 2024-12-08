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

    /**
     * The constructor of the parser, initialise a map of all commands available
     */
    public UnixCommandParser() {
        commandMap.put("cd", () -> new ChangeDirectoryCommand(curFolder, arg1));
        commandMap.put("ls", () -> new ListCommand(curFolder, arg1));
        commandMap.put("mkdir", () -> new MakeDirectoryCommand(curFolder, arg1));
        commandMap.put("touch", () -> new TouchCommand(curFolder, arg1));
        commandMap.put("tree", () -> new TreeCommand(curFolder, arg1));
        commandMap.put("cmds", CommandsListCommand::new);
        commandMap.put("clear", ClearCommand::new);
        commandMap.put("cat", () -> new CatCommand(curFolder, arg1));
        commandMap.put("nano", () -> new NanoCommand(curFolder, arg1));
        commandMap.put("pwd", () -> new PwdCommand(curFolder));
    }

    /**
     * Get all the command mapped
     * @return The list of command as a List of String
     */
    public List<String> getListCommands()
    {
        List<String> commands = new ArrayList<>();
        for (String string : this.commandMap.keySet()) {
            commands.add(string);
        }

        return commands;
    }

    /**
     * Parse an user command
     * @param userCommand The user command
     * @param folderInode The current user folder location
     * @return The mapped method wich conrrespond to the user command
     */
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

    /**
     * Split userCommand in multiple arguments
     * @param userCommand The user Command
     * @return The splitted list as an List of String
     */
    private List<String> splitArguments(String userCommand)
    {
        return Arrays.asList(userCommand.trim().split(" "));
    }

    /**
     * Get a command from the parsed command
     * @return The corresponding command or an ErrorCommand
     */
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
