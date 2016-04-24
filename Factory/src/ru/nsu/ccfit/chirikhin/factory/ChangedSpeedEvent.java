package ru.nsu.ccfit.chirikhin.factory;

import org.apache.log4j.Logger;

public class ChangedSpeedEvent {
    private SpeedType speedType;
    private int newSpeed;

    public SpeedType getSpeedType() {
        return speedType;
    }

    public int getNewSpeed() {
        return newSpeed;
    }

    public ChangedSpeedEvent(SpeedType speedType, int newSpeed) {

        this.speedType = speedType;
        this.newSpeed = newSpeed;
    }

    private Logger logger = Logger.getLogger(ChangedSpeedEvent.class.getName());
}
