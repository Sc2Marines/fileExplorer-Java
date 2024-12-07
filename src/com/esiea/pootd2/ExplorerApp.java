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
        System.out.println(controller.executeCommand("touch test.c"));
        System.out.println(controller.executeCommand("ls"));
        System.out.println(controller.executeCommand("cd /POO/TD2"));
        System.out.println(controller.executeCommand("mkdir tmp"));
        System.out.println(controller.executeCommand("mkdir /POO/TD2/tmp/tmp2"));
        System.out.println(controller.executeCommand("touch /POO/TD2/tmp/test2.c"));
        System.out.println(controller.executeCommand("touch /POO/TD2/tmp/tmp2/test3.c"));
        System.out.println(controller.executeCommand("ls /"));
        System.out.println(controller.executeCommand("ls ../"));
        System.out.println(controller.executeCommand("mkdir /POO/TD2/tmp/tmp2/../tmp3"));
        System.out.println(controller.executeCommand("touch /POO/TD2/tmp/tmp2/../tmp3/test4.c"));
        System.out.println(controller.executeCommand("touch /POO/TD2/tmp/tmp2/../tmp3/test4.c"));
        System.out.println(controller.executeCommand("cd ./tmp/tmp2/../"));
        System.out.println(controller.executeCommand("cd /"));
        System.out.println(controller.executeCommand("tree"));
        

        TextInterface textInterface = new TextInterface(controller);
    }
}
