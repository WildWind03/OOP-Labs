package ru.nsu.ccfit.chirikhin.factory;

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
