package com.esiea.pootd2;

import com.esiea.pootd2.controllers.ExplorerController;
import com.esiea.pootd2.interfaces.TextInterface;

public class ExplorerApp {
    public static void main(String[] args) {
        // Initialiser le contrôleur
        ExplorerController controller = new ExplorerController();

        // Initialiser l'interface textuelle
        TextInterface textInterface = new TextInterface(controller);

        // Lancer l'interface utilisateur
        textInterface.run();
    }
}
