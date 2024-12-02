package com.esiea.pootd2;

import com.esiea.pootd2.controllers.ExplorerController;
import com.esiea.pootd2.interfaces.TextInterface;
import com.esiea.pootd2.models.FileInode;
import com.esiea.pootd2.models.FolderInode;
import com.esiea.pootd2.commands.Command;
import com.esiea.pootd2.commands.parsers.*;
public class ExplorerApp {

    public static void main(String[] args) {
        FolderInode root = new FolderInode("/");
        FolderInode folder1 = new FolderInode("POO");
        FolderInode folder2 = new FolderInode("TD2");
        root.addSubInodes(folder1);
        folder1.addSubInodes(folder2);
        FileInode file1 = new FileInode("TD1.pdf");
        FileInode file2 = new FileInode("Main.java");
        FileInode file3 = new FileInode("Main.class");
        folder1.addSubInodes(file1);
        folder2.addSubInodes(file2);
        folder2.addSubInodes(file3);

        //root.displaySubInodes("");

        UnixCommandParser parser = new UnixCommandParser();
        Command cmd;

        cmd = parser.parse("touch test.c", folder2);
        System.out.println(cmd.execute());
        cmd = parser.parse("ls", folder2);
        System.out.println(cmd.execute());

        cmd = parser.parse("mkdir tmp", folder2);
        System.out.println(cmd.execute());
        cmd = parser.parse("mkdir /POO/TD2/tmp/tmp2", folder2);
        System.out.println(cmd.execute());
        cmd = parser.parse("touch /POO/TD2/tmp/test2.c", folder2);
        System.out.println(cmd.execute());
        cmd = parser.parse("touch /POO/TD2/tmp/tmp2/test3.c", folder2);
        System.out.println(cmd.execute());
        cmd = parser.parse("ls /", folder2);
        System.out.println(cmd.execute());
        cmd = parser.parse("ls ../", folder2);
        System.out.println(cmd.execute());
        cmd = parser.parse("mkdir /POO/TD2/tmp/tmp2/../tmp3", folder2);
        System.out.println(cmd.execute());
        cmd = parser.parse("touch /POO/TD2/tmp/tmp2/../tmp3/test4.c", folder2);
        System.out.println(cmd.execute());


        root.displaySubInodes("");



        ExplorerController controller = new ExplorerController();
        TextInterface textInterface = new TextInterface(controller);
        //textInterface.run();
    }
}
