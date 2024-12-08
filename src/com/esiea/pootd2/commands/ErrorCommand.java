package com.esiea.pootd2.commands;

public class ErrorCommand extends Command {
    private final String errorMessage;

    /**
     * Constructor for the ErrorCommand
     * @param errorMessage The error message
     */
    public ErrorCommand(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * Execute the error command
     * @return the error message
     */
    @Override
    public String execute() {
        return "Error: " + errorMessage;
    }
}
