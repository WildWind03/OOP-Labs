package ru.nsu.ccfit.chirikhin.factory;

public class Accessory {
    private final static String description = "Accessory";
    private final long id;

    public Accessory(IDRegisterer idRegisterer) {
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
