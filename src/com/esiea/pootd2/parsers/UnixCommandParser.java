package com.esiea.pootd2.parsers;

import java.util.List;
import java.util.ArrayList;

public class UnixCommandParser implements ICommandParser {
    
    public UnixCommandParser()
    {

    }

    public Object parse(String userCommand)
    {
        List<String> parsedCommand = this.splitArguments(userCommand);
        return this.MapCommand(parsedCommand);
    }


    private List<String> splitArguments(String userCommand)
    {
        List<String> parsedCommand = new ArrayList<>();
        userCommand.split(" ");
        return parsedCommand;
    }

    private Object MapCommand(List<String> parsedCommand)
    {
        if (parsedCommand == null || parsedCommand.size() == 0)
        {
            return new Object(); // ErrorCommand Object
        }
        else 
        {
            String commandName = parsedCommand.get(0);
            Object command;
            switch (commandName) {
                case "cd":
                    command = new Object();
                    break;
                case "ls":
                    command = new Object();
                    break;
                case "mkdir":
                    command = new Object();
                    break;
                case "touch":
                    command = new Object();
                    break;
                default:
                    command = new Object();
                    // implement ErrorCommand (command not found)
                    break;
            }
            return command;
        }
        
    }

}
