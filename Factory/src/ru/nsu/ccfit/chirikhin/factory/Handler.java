package ru.nsu.ccfit.chirikhin.factory;

import java.util.Observer;

public abstract class Handler implements Observer {
    FXMLViewController fxmlViewController;

    public Handler(FXMLViewController fxmlViewController) {
        this.fxmlViewController = fxmlViewController;
    }
}
