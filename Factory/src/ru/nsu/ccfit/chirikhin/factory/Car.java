package ru.nsu.ccfit.chirikhin.factory;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by cas on 09.04.16.
 */
public class Car {
    static AtomicInteger maxCarId = new AtomicInteger(0);

    final private Engine engine;
    final private CarBody carBody;
    final private Accessory accessory;
    final private int id;

    public Car(Engine engine, Accessory accessory, CarBody carBody) {
        if (null == engine || null == accessory || null == carBody) {
            throw new IllegalArgumentException("Can't create myself because of null reference!");
        }

        this.engine = engine;
        this.accessory = accessory;
        this.carBody = carBody;
        id = maxCarId.getAndIncrement();
    }

    public int getId() {
        return id;
    }
}
