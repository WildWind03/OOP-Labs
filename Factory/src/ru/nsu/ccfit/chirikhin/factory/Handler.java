package ru.nsu.ccfit.chirikhin.factory;

import ru.nsu.ccfit.chirikhin.factoryview.FXMLViewController;

import java.util.Observer;

public abstract class Handler implements Observer {
    private final FXMLViewController fxmlViewController;

    public Handler(FXMLViewController fxmlViewController) {
        this.fxmlViewController = fxmlViewController;
    }
}
