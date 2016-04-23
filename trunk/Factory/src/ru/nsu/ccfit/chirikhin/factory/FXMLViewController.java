package ru.nsu.ccfit.chirikhin.factory;

import javafx.application.Platform;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import org.apache.log4j.Logger;

public class FXMLViewController {
    private Logger logger = Logger.getLogger(FXMLViewController.class.getName());

    @FXML
    ProgressBar countOfEnginesBar;
    @FXML
    Label countOfEngines;
    @FXML
    Label countOfAccessories;
    @FXML
    Label countOfCarBodies;
    @FXML
    ProgressBar countOfCarBodiesBar;
    @FXML
    ProgressBar countOfAccessoriesBar;

    @FXML
    Label countOfCars;

    @FXML
    ProgressBar countOfCarsBar;

    public FXMLViewController() {
        logger.debug("Constuctor");
    }

    public void onEngineStorageChanged(Storage.StorageContext storageContext) {
        logger.debug("Engine Storage Changed");
        countOfEnginesBar.setProgress(((double) storageContext.getSize()) / ((double) storageContext.getMaxSize()));
        countOfEngines.setText("Engines - " + storageContext.getSize() + "/" + storageContext.getMaxSize());
    }

    public void onAccessoryStorageChanged(Storage.StorageContext storageContext) {
        countOfAccessoriesBar.setProgress(((double) storageContext.getSize()) / ((double) storageContext.getMaxSize()));
        countOfAccessories.setText("Accessories - " + storageContext.getSize() + "/" + storageContext.getMaxSize());
    }

    public void onCarBodyStorageChanged(Storage.StorageContext storageContext) {
        countOfCarBodiesBar.setProgress(((double) storageContext.getSize()) / ((double) storageContext.getMaxSize()));
        countOfCarBodies.setText("Car Bodies - " + storageContext.getSize() + "/" + storageContext.getMaxSize());
    }

    public void onCarStorageChanged(Storage.StorageContext storageContext) {
        countOfCarsBar.setProgress(((double) storageContext.getSize()) / ((double) storageContext.getMaxSize()));
        countOfCars.setText("Cars - " + storageContext.getSize() + "/" + storageContext.getMaxSize());
    }
}
