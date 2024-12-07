package com.esiea.pootd2.models;

import java.io.File;
import java.lang.reflect.Field;
import java.util.Random;

public class FileInode extends Inode {
    
    public FileInode(String name)
    {
        super(name);
        Random rd = new Random();
        int size = rd.nextInt(100000) + 1;
        super.setSize(size);
    }
}
