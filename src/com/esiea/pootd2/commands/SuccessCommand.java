package com.esiea.pootd2.commands;

public class SuccessCommand extends Command {
    private final String successMessage;

    /**
     * Constructor for the SucessCommand
     * @param successMessage The success message
     */
    public SuccessCommand(String successMessage) {
        this.successMessage = successMessage;
    }

    /**
     * Execute the command
     * @return the sucessMessage as a String
     */
    @Override
    public String execute() {
        return "Success: " + successMessage;
    }
}
