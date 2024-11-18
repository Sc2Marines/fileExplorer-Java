package com.esiea.pootd2.commands;

public class ErrorCommand extends Command {
    private final String errorMessage;

    public ErrorCommand(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String execute() {
        return "Erreur: " + errorMessage;
    }
}
