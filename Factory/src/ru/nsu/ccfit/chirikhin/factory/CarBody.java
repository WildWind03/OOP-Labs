package ru.nsu.ccfit.chirikhin.factory;

public class CarBody {
    private final static String description = "Car Body";
    private final long id;

    public CarBody(long id) {
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
