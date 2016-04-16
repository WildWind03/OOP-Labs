package ru.nsu.ccfit.chirikhin.factory;

public class Engine {
    private static String description = "Engine";
    private final long id;

    public Engine(IDRegisterer idRegisterer) {
        id = idRegisterer.getId();
    }

    @Override
    public String toString() {
        return description;
    }
}
