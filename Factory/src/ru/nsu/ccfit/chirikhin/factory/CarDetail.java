package ru.nsu.ccfit.chirikhin.factory;

import java.util.concurrent.atomic.AtomicLong;

abstract public class CarDetail {
    static AtomicLong idCounter = new AtomicLong(0);

    private long id;

    public CarDetail() {
        this.id = idCounter.getAndIncrement();
    }

    public long getId() {
        return id;
    }

    @Override
    abstract public String toString();
}
