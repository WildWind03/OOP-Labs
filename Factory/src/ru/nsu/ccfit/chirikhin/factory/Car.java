package ru.nsu.ccfit.chirikhin.factory;

/**
 * Created by cas on 09.04.16.
 */
public class Car {
    static int maxCarId = 0;

    final Engine engine;
    final CarBody carBody;
    final Accessory accessory;
    final int id;

    public Car(Engine engine, Accessory accessory, CarBody carBody) {
        this.engine = engine;
        this.accessory = accessory;
        this.carBody = carBody;
        id = maxCarId++;
    }
}
