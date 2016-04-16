package ru.nsu.ccfit.chirikhin.factory;

import java.util.concurrent.atomic.AtomicInteger;

public class Car {

    final private Engine engine;
    final private CarBody carBody;
    final private Accessory accessory;
    final private long id;

    public Car(Engine engine, Accessory accessory, CarBody carBody, IDRegisterer idRegisterer) {
        if (null == engine || null == accessory || null == carBody) {
            throw new IllegalArgumentException("Can't create myself because of null reference!");
        }

        id = idRegisterer.getId();

        this.engine = engine;
        this.accessory = accessory;
        this.carBody = carBody;
    }

    public long getId() {
        return id;
    }
}
