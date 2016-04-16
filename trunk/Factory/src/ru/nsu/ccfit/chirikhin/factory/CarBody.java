package ru.nsu.ccfit.chirikhin.factory;

public class CarBody {
    private final static String description = "Car Body";
    private final long id;

    public CarBody(IDRegisterer idRegisterer) {
        if (null == idRegisterer) {
            throw new NullPointerException("Null reference instead of IDRegister");
        }

        id = idRegisterer.getId();
    }

    public long getId() {
        return id;
    }

    @Override
    public String toString() {
        return description;
    }
}
