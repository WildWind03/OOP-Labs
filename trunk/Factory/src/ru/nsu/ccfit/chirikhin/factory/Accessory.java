package ru.nsu.ccfit.chirikhin.factory;


import org.apache.log4j.Logger;

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
