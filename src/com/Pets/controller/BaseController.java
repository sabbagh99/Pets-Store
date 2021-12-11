package com.Pets.controller;

import com.Pets.view.ViewFactory;

public  abstract  class BaseController {

//    private PetsManager petsManager;
    private ViewFactory viewFactory;
    private String fxmlName;

    public BaseController( ViewFactory viewFactory, String fxmlName) {
        this.viewFactory = viewFactory;
        this.fxmlName = fxmlName;
    }


//    public PetsManager getPetsManager() {
//        return petsManager;
//    }
//
//    public void setPetsManager(PetsManager petsManager) {
//        this.petsManager = petsManager;
//    }

    public ViewFactory getViewFactory() {
        return viewFactory;
    }

    public void setViewFactory(ViewFactory viewFactory) {
        this.viewFactory = viewFactory;
    }

    public String getFxmlName() {
        return fxmlName;
    }

    public void setFxmlName(String fxmlName) {
        this.fxmlName = fxmlName;
    }
}
