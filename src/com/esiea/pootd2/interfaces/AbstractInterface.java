package com.esiea.pootd2.interfaces;
import com.esiea.pootd2.controllers.IExplorerController;

public class AbstractInterface {

    protected final IExplorerController controller;

    public AbstractInterface(IExplorerController controller)
    {
        this.controller = controller;
    }
}
