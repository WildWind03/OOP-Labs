package ru.nsu.ccfit.chirikhin.factory;

import javafx.beans.Observable;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.apache.log4j.Logger;
import ru.nsu.ccfit.chirikhin.numerictextfield.NumericTextField;

public class FXMLViewController extends java.util.Observable {
    private Logger logger = Logger.getLogger(FXMLViewController.class.getName());

    private  int engineStorageSize = 0;
    private  int carBodyStorageSize = 0;
    private  int carStorageSize = 0;
    private  int accessoryStorageSize = 0;

    private int currentEngineCountInStorage = 0;
    private int currentCarBodiesCountInStorage = 0;
    private int currentCarsCountInStorage = 0;
    private int currentAccessoryCountInStorage = 0;

    private int totalEnginesProducedCounter = 0;
    private int totalAccessoriesProducedCounter = 0;
    private int totalCarsProducedCounter = 0;
    private int totalCarBodiesProducedCounter = 0;

    private int maxAccessoryProducingSpeed;
    private int maxEngineProducingSpeed;
    private int maxCarBodyProducingSpeed;
    private int maxDealerSpeed;

    private int currentTasksToMakeCars = 0;

    @FXML ProgressBar countOfEnginesBar;
    @FXML Label countOfEngines;
    @FXML Label countOfAccessories;
    @FXML Label countOfCarBodies;
    @FXML ProgressBar countOfCarBodiesBar;
    @FXML ProgressBar countOfAccessoriesBar;
    @FXML Label countOfCars;
    @FXML ProgressBar countOfCarsBar;
    @FXML Label countOfWorkers;
    @FXML Label countOfAccessoryProducers;
    @FXML Label countOfDealers;
    @FXML NumericTextField accessoryProducersEdit;
    @FXML Slider accessoryProducersSlider;
    @FXML Label totalAccessoryProduced;
    @FXML Label totalEnginesProduced;
    @FXML Label totalCarBodiesProduced;
    @FXML Label totalCarsProduced;
    @FXML NumericTextField dealersProducingSpeedEdit;
    @FXML Slider dealersProducingSpeedSlider;
    @FXML NumericTextField enginesProducerEdit;
    @FXML Slider enginesProducerSlider;
    @FXML NumericTextField carBodyProducerEdit;
    @FXML Slider carBodyProducerSlider;
    @FXML Label countOfSoldCars;
    @FXML Label tasksToMakeCars;

    public FXMLViewController() {
    }

    public void onEngineStorageChanged(StorageEvent e) {
        switch(e) {
            case PUT :
                currentEngineCountInStorage++;
                totalEnginesProducedCounter++;
                break;
            case GET :
                currentEngineCountInStorage--;
                break;
        }

        countOfEnginesBar.setProgress(((double) currentEngineCountInStorage) / ((double) engineStorageSize));
        countOfEngines.setText("Engines - " + currentEngineCountInStorage + "/" + engineStorageSize);
        totalEnginesProduced.setText("Total engines produced - " + totalEnginesProducedCounter);
    }

    public void onCarBodyStorageChanged(StorageEvent e) {
        switch(e) {
            case PUT :
                currentCarBodiesCountInStorage++;
                totalCarBodiesProducedCounter++;
                break;
            case GET :
                currentCarBodiesCountInStorage--;
                break;
        }

        countOfCarBodiesBar.setProgress(((double) currentCarBodiesCountInStorage) / ((double) carBodyStorageSize));
        countOfCarBodies.setText("Car Bodies - " + currentCarBodiesCountInStorage + "/" + carBodyStorageSize);
        totalCarBodiesProduced.setText("Total car bodies produced - " + totalCarBodiesProducedCounter);
    }

    public void onAccessoryStorageChanged(StorageEvent e) {
        switch(e) {
            case PUT :
                currentAccessoryCountInStorage++;
                totalAccessoriesProducedCounter++;
                break;
            case GET :
                currentAccessoryCountInStorage--;
                break;
        }

        countOfAccessoriesBar.setProgress(((double) currentAccessoryCountInStorage) / ((double) accessoryStorageSize));
        countOfAccessories.setText("Accessories - " + currentAccessoryCountInStorage + "/" + accessoryStorageSize);
        totalAccessoryProduced.setText("Total Accessories produced - " + totalAccessoriesProducedCounter);
    }

    public void onCarStorageChanged(StorageEvent e) {
        switch(e) {
            case PUT :
                currentCarsCountInStorage++;
                totalCarsProducedCounter++;
                break;
            case GET :
                currentCarsCountInStorage--;
                break;
        }

        countOfCarsBar.setProgress(((double) currentCarsCountInStorage) / ((double) carStorageSize));
        countOfCars.setText("Cars - " + currentCarsCountInStorage + "/" + carStorageSize);
        totalCarsProduced.setText("Total Cars produced - " + totalCarsProducedCounter);
        countOfSoldCars.setText("Count of sold cars - " + (totalCarsProducedCounter - currentCarsCountInStorage));
    }

    public void setCountOfAccessoryProducers(int count) {
        countOfAccessoryProducers.setText("AccessoryProducers - " + count);
    }

    public void setCountOfWorkers(int count) {
        countOfWorkers.setText("Car collectors - " + count);
    }

    public void setCountOfDealers(int count) {
        countOfDealers.setText("Dealers - " + count);
    }

    public void setAccessoryStorageSize(int size) {
        this.accessoryStorageSize = size;
    }

    public void setEngineStorageSize(int size) {
        this.engineStorageSize = size;
    }

    public void setCarStorageSize(int size) {
        this.carStorageSize = size;
    }

    public void setCarBodyStorageSize(int size) {
        this.carBodyStorageSize = size;
    }

    public void setAccessoryProducingSpeed(int speed, int maxSpeed) {
        accessoryProducersEdit.setText(String.valueOf(speed));
        accessoryProducersSlider.setMax(maxSpeed);
        accessoryProducersSlider.setValue(speed);
        this.maxAccessoryProducingSpeed = maxSpeed;
        accessoryProducersEdit.setMaxValue(maxSpeed);
    }

    public void setDealerSpeed(int speed, int maxSpeed) {
        dealersProducingSpeedEdit.setText(String.valueOf(speed));
        dealersProducingSpeedSlider.setMax(maxSpeed);
        dealersProducingSpeedSlider.setValue(speed);
        this.maxDealerSpeed = maxSpeed;
        dealersProducingSpeedEdit.setMaxValue(maxSpeed);
    }

    public void setEngineProducingSpeed(int speed, int maxSpeed) {
        enginesProducerEdit.setText(String.valueOf(speed));
        enginesProducerSlider.setMax(maxSpeed);
        enginesProducerSlider.setValue(speed);
        this.maxEngineProducingSpeed = maxSpeed;
        enginesProducerEdit.setMaxValue(maxSpeed);
    }

    public void setCarBodyProducingSpeed(int speed, int maxSpeed) {
        carBodyProducerEdit.setText(String.valueOf(speed));
        carBodyProducerSlider.setMax(maxSpeed);
        carBodyProducerSlider.setValue(speed);
        this.maxCarBodyProducingSpeed = maxSpeed;
        carBodyProducerEdit.setMaxValue(maxSpeed);
    }


    @FXML public void onEngineProducerSliderDragged(Event event) {
        enginesProducerEdit.setText(String.valueOf((int)enginesProducerSlider.getValue()));
        setChanged();
        notifyObservers(new ChangedSpeedEvent(SpeedType.ENGINE_PRODUCING, (int)enginesProducerSlider.getValue()));
    }

    @FXML public void onAccessoryProducerSliderDragged(Event event) {
        accessoryProducersEdit.setText(String.valueOf((int)accessoryProducersSlider.getValue()));
        setChanged();
        notifyObservers(new ChangedSpeedEvent(SpeedType.ACCESSORY_PRODUCING, (int)accessoryProducersSlider.getValue()));
    }

    @FXML public void onDealerSliderDragged(Event event) {
        dealersProducingSpeedEdit.setText(String.valueOf((int)dealersProducingSpeedSlider.getValue()));
        setChanged();
        notifyObservers(new ChangedSpeedEvent(SpeedType.SELLING_CARS, (int)dealersProducingSpeedSlider.getValue()));
    }

    @FXML public void onCarBodyProducerSliderDragged(Event event) {
        carBodyProducerEdit.setText(String.valueOf((int)carBodyProducerSlider.getValue()));
        setChanged();
        notifyObservers(new ChangedSpeedEvent(SpeedType.CAR_BODY_PRODUCING, (int)carBodyProducerSlider.getValue()));
    }

    @FXML public void onCarBodyProducingSpeedChanged(Event event) {
        carBodyProducerSlider.setValue(Integer.parseInt(carBodyProducerEdit.getText()));
        setChanged();
        notifyObservers(new ChangedSpeedEvent(SpeedType.CAR_BODY_PRODUCING, (int)carBodyProducerSlider.getValue()));
    }

    @FXML public void onEngineProducingSpeedChanged(Event event) {
        enginesProducerSlider.setValue(Integer.parseInt(enginesProducerEdit.getText()));
        setChanged();
        notifyObservers(new ChangedSpeedEvent(SpeedType.ENGINE_PRODUCING, (int)enginesProducerSlider.getValue()));
    }

    @FXML public void onDealerSpeedChanged(Event event) {
        dealersProducingSpeedSlider.setValue(Integer.parseInt(dealersProducingSpeedEdit.getText()));
        setChanged();
        notifyObservers(new ChangedSpeedEvent(SpeedType.SELLING_CARS, (int)dealersProducingSpeedSlider.getValue()));
    }

    @FXML public void onAccessoryProducerSpeedChanged(Event event) {
        accessoryProducersSlider.setValue(Integer.parseInt(accessoryProducersEdit.getText()));
        setChanged();
        notifyObservers(new ChangedSpeedEvent(SpeedType.ACCESSORY_PRODUCING, (int)accessoryProducersSlider.getValue()));
    }

    @FXML public void onNewTaskToMakeCarAppeared() {
        currentTasksToMakeCars++;
        tasksToMakeCars.setText("Tasks to make cars - " + currentTasksToMakeCars);
    }

    @FXML public void onTaskCompleted() {
        currentTasksToMakeCars--;
        tasksToMakeCars.setText("Tasks to make cars - " + currentTasksToMakeCars);
    }
}
