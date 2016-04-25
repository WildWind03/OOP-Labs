package ru.nsu.ccfit.chirikhin.factory;

public class Engine {
    private static final String description = "Engine";
    private final long id;

    public Engine(long id) {
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
