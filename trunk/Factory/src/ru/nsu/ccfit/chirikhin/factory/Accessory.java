package ru.nsu.ccfit.chirikhin.factory;

public class Accessory {
    private final static String description = "Accessory";
    private final long id;

    public Accessory(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    @Override
    public String toString() {
        return description;
    }
}
