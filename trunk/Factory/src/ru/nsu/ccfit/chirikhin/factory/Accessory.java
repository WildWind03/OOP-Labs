package ru.nsu.ccfit.chirikhin.factory;

public class Accessory extends CarDetail {
    final static String description = "Accessory";

    public Accessory() {
        super();
    }

    @Override
    public String toString() {
        return description;
    }
}
