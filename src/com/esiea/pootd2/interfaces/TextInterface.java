package com.esiea.pootd2.interfaces;

import com.esiea.pootd2.controllers.IExplorerController;
import java.util.Scanner;

public class TextInterface implements IUserInterface {
    private final IExplorerController controller;

    // Constructeur pour injecter le contrôleur
    public TextInterface(IExplorerController controller) {
        this.controller = controller;
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        String input;

        System.out.println("Bienvenue dans l'explorateur de fichiers virtuel. Tapez 'exit' pour quitter.");

        // Boucle infinie jusqu'à ce que l'utilisateur tape 'exit'
        while (true) {
            System.out.print("> ");
            input = scanner.nextLine().trim();

            // Vérifier si l'utilisateur veut quitter
            if ("exit".equalsIgnoreCase(input)) {
                System.out.println("Fermeture de l'explorateur.");
                break;
            }

            // Passer la commande à l'explorer controller
            String result = controller.executeCommand(input);

            // Afficher le résultat de la commande
            if (result != null && !result.isEmpty()) {
                System.out.println(result);
            }
        }

        scanner.close();
    }
}
