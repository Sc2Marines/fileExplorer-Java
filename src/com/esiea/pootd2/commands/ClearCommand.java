package com.esiea.pootd2.commands;

public class ClearCommand extends Command {

    public ClearCommand() {
        // no porperties needed
    }

    @Override
    public String execute() {
        // ANSI escape code to clear the screen
        return "\033[H\033[2J";
    }
}
