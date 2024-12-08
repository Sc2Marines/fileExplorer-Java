package com.esiea.pootd2;

import com.esiea.pootd2.controllers.ExplorerController;
import com.esiea.pootd2.interfaces.*;
public class ExplorerApp {

    public static void main(String[] args) {

        ExplorerController controller = new ExplorerController();
        controller.initialiseBaseArchitecture();
    
        if (args.length == 0) {
            System.out.println("Please specify interface [cli or web]");
        } else if (args[0].equalsIgnoreCase("cli")) {
            TextInterface textInterface = new TextInterface(controller);
            textInterface.run();
        } else if (args[0].equalsIgnoreCase("web")) {
            HttpInterface httpInterface = new HttpInterface(controller);
            httpInterface.run();
        } else {
            System.out.println("Invalid interface specified. Please use 'cli' or 'web'.");
        }
    }
}
