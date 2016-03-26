package ru.nsu.ccfit.chirikhin.factory;

/**
 * Created by cas on 26.03.16.
 */
public class CarBody extends CarDetail {
    final static String description = "Car Body";

    public CarBody() {
        super();
    }

    @Override
    public String toString() {
        return description;
    }
}
