package com.esiea.pootd2.commands;

public class SuccessCommand extends Command {
    private final String successMessage;

    public SuccessCommand(String successMessage) {
        this.successMessage = successMessage;
    }

    @Override
    public String execute() {
        return "Success: " + successMessage;
    }
}
