package ru.nsu.ccfit.chirikhin.factory;

import javafx.application.Platform;
import org.apache.log4j.Logger;

import java.util.Observable;
import java.util.Observer;

public class OnEngineStorageChangedHandler implements Observer {
    private Logger logger = Logger.getLogger(OnEngineStorageChangedHandler.class.getName());
    JavaFXView view;

    public OnEngineStorageChangedHandler(JavaFXView view) {
        this.view = view;
    }

    @Override
    public void update(Observable o, Object arg) {
        view.onEngineStorageChanged((Storage.StorageContext) arg);
    }
}
