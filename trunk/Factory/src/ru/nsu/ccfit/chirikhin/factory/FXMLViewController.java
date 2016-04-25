package ru.nsu.ccfit.chirikhin.factory;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
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
        if (null == e) {
            throw new NullPointerException("Event can't be null!");
        }

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
        if (null == e) {
            throw new NullPointerException("Event can't be null!");
        }

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
        if (null == e) {
            throw new NullPointerException("Event can't be null!");
        }

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
        if (null == e) {
            throw new NullPointerException("Event can't be null!");
        }

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
        if (count < 0) {
            throw new IllegalArgumentException("Count of producers can't be negative!");
        }

        countOfAccessoryProducers.setText("AccessoryProducers - " + count);
    }

    public void setCountOfWorkers(int count) {
        if (count < 0) {
            throw new IllegalArgumentException("Count of workers can't be negative!");
        }

        countOfWorkers.setText("Car collectors - " + count);
    }

    public void setCountOfDealers(int count) {
        if (count < 0) {
            throw new IllegalArgumentException("Count of dealers can't be negative!");
        }

        countOfDealers.setText("Dealers - " + count);
    }

    public void setAccessoryStorageSize(int size) {
        if (size < 0) {
            throw new IllegalArgumentException("Storage size can't be negative!");
        }

        this.accessoryStorageSize = size;
    }

    public void setEngineStorageSize(int size) {
        if (size < 0) {
            throw new IllegalArgumentException("Storage size can't be negative!");
        }

        this.engineStorageSize = size;
    }

    public void setCarStorageSize(int size) {
        if (size < 0) {
            throw new IllegalArgumentException("Storage size can't be negative!");
        }

        this.carStorageSize = size;
    }

    public void setCarBodyStorageSize(int size) {
        if (size < 0) {
            throw new IllegalArgumentException("Storage size can't be negative!");
        }

        this.carBodyStorageSize = size;
    }

    public void setAccessoryProducingSpeed(int speed, int maxSpeed) {
        if (speed < 0 || maxSpeed < 0) {
            throw new IllegalArgumentException("Speed can't be negative!");
        }
        if (speed > maxSpeed) {
            throw new IllegalArgumentException("Speed must be less than maxSpeed!");
        }

        accessoryProducersEdit.setText(String.valueOf(speed));
        accessoryProducersSlider.setMax(maxSpeed);
        accessoryProducersSlider.setValue(speed);
        accessoryProducersEdit.setMaxValue(maxSpeed);
    }

    public void setDealerSpeed(int speed, int maxSpeed) {
        if (speed < 0 || maxSpeed < 0) {
            throw new IllegalArgumentException("Speed can't be negative!");
        }
        if (speed > maxSpeed) {
            throw new IllegalArgumentException("Speed must be less than maxSpeed!");
        }

        dealersProducingSpeedEdit.setText(String.valueOf(speed));
        dealersProducingSpeedSlider.setMax(maxSpeed);
        dealersProducingSpeedSlider.setValue(speed);
        dealersProducingSpeedEdit.setMaxValue(maxSpeed);
    }

    public void setEngineProducingSpeed(int speed, int maxSpeed) {
        if (speed < 0 || maxSpeed < 0) {
            throw new IllegalArgumentException("Speed can't be negative!");
        }
        if (speed > maxSpeed) {
            throw new IllegalArgumentException("Speed must be less than maxSpeed!");
        }

        enginesProducerEdit.setText(String.valueOf(speed));
        enginesProducerSlider.setMax(maxSpeed);
        enginesProducerSlider.setValue(speed);
        enginesProducerEdit.setMaxValue(maxSpeed);
    }

    public void setCarBodyProducingSpeed(int speed, int maxSpeed) {
        if (speed < 0 || maxSpeed < 0) {
            throw new IllegalArgumentException("Speed can't be negative!");
        }
        if (speed > maxSpeed) {
            throw new IllegalArgumentException("Speed must be less than maxSpeed!");
        }

        carBodyProducerEdit.setText(String.valueOf(speed));
        carBodyProducerSlider.setMax(maxSpeed);
        carBodyProducerSlider.setValue(speed);
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
