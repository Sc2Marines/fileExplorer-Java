package com.esiea.pootd2.commands;

import java.util.List;
import com.esiea.pootd2.commands.parsers.*;

public class CommandsListCommand extends Command{
    private UnixCommandParser parser;

    public CommandsListCommand()
    {
        this.parser = new UnixCommandParser();
    }

    @Override
    public String execute()
    {
        List<String> commands = parser.getListCommands();
        StringBuilder result = new StringBuilder();
        for (String string : commands) {
            result.append(string + "\n");
        }
        return result.toString();
    }
}
