package com.esiea.pootd2;

import com.esiea.pootd2.controllers.ExplorerController;
import com.esiea.pootd2.interfaces.TextInterface;
import com.esiea.pootd2.models.FileInode;
import com.esiea.pootd2.models.FolderInode;
import com.esiea.pootd2.commands.ChangeDirectoryCommand;
import com.esiea.pootd2.commands.Command;
import com.esiea.pootd2.commands.parsers.*;
public class ExplorerApp {

    public static void main(String[] args) {

        ExplorerController controller = new ExplorerController();
        controller.initialiseBaseArchitecture();
        
        TextInterface textInterface = new TextInterface(controller);
        textInterface.run();
    }
}
