package ru.nsu.ccfit.chirikhin.factory;

import java.util.concurrent.atomic.AtomicInteger;

public class Car {

    private final Engine engine;
    private final CarBody carBody;
    private final Accessory accessory;
    private final long id;

    public Car(Engine engine, Accessory accessory, CarBody carBody, long id) {
        if (null == engine || null == accessory || null == carBody) {
            throw new IllegalArgumentException("Can't create myself because of null reference!");
        }

        if (id < 0) {
            throw new IllegalArgumentException("ID can't be negative!");
        }

        this.id = id;
        this.engine = engine;
        this.accessory = accessory;
        this.carBody = carBody;
    }

    public long getId() {
        return id;
    }

    public long getBodyId() {
        return carBody.getId();
    }

    public long getEngineId() {
        return engine.getId();
    }

    public long getAccessoryId() {
        return accessory.getId();
    }
}
