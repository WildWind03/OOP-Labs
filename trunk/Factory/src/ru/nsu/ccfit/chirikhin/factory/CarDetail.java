package ru.nsu.ccfit.chirikhin.factory;

abstract public class CarDetail {
    static long idCounter = 0;

    private long id;

    public CarDetail() {
        this.id = idCounter++;
    }

    public long getId() {
        return id;
    }

    @Override
    abstract public String toString();
}
