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

        System.out.println("Bienvenue dans l'explorateur de fichiers virtuel. Tapez 'exit' pour quitter.");

        while (true) {
            System.out.print("> ");
            input = scanner.nextLine().trim();

            if ("exit".equalsIgnoreCase(input)) {
                System.out.println("Fermeture de l'explorateur.");
                break;
            }

            String result = controller.executeCommand(input);

            if (result != null && !result.isEmpty()) {
                System.out.println(result);
            }
        }

        scanner.close();
    }
}
