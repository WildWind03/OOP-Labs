package ru.nsu.ccfit.chirikhin.factory;

import org.apache.log4j.Logger;

public class ChangedSpeedEvent {
    private final SpeedType speedType;
    private final int newSpeed;
    private final static Logger logger = Logger.getLogger(ChangedSpeedEvent.class.getName());

    public SpeedType getSpeedType() {
        return speedType;
    }

    public int getNewSpeed() {
        return newSpeed;
    }

    public ChangedSpeedEvent(SpeedType speedType, int newSpeed) {

        if (null == speedType) {
            throw new NullPointerException("Null reference");
        }

        if (newSpeed < 0) {
            throw new IllegalArgumentException("Speed can no be negative");
        }

        this.speedType = speedType;
        this.newSpeed = newSpeed;
    }
}
