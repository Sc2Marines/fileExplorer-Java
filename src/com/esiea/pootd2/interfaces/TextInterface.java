package com.esiea.pootd2.interfaces;

import com.esiea.pootd2.controllers.IExplorerController;
import java.util.Scanner;

public class TextInterface implements IUserInterface {
    private final IExplorerController controller;

    public TextInterface(IExplorerController controller) {
        this.controller = controller;
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        String input;

        System.out.println("Welcome to the virtual file explorer. Type 'cmds' to see all commands, type 'exit' to quit.");

        while (true) {
            System.out.print("> ");
            input = scanner.nextLine().trim();

            if ("exit".equalsIgnoreCase(input)) {
                System.out.println("Closing file explorer.");
                break;
            }

            String result = controller.executeCommand(input);

            if (result != null && !result.isEmpty()) {
                if (result.equals("\033[H\033[2J")) {
                    System.out.print(result);
                } else {
                    System.out.println(result);
                }
            }
        }

        scanner.close();
    }
}
