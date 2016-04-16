package ru.nsu.ccfit.chirikhin.factory;


import org.apache.log4j.Logger;

public class Accessory {
    final static String description = "Accessory";
    final long id;

    public Accessory(IDRegisterer idRegisterer) {
        id = idRegisterer.getId();
    }

    @Override
    public String toString() {
        return description;
    }
}
