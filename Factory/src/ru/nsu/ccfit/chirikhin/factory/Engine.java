package ru.nsu.ccfit.chirikhin.factory;

public class Engine extends CarDetail {
    private static String description = "Engine";

    public Engine() {super();}

    @Override
    public String toString() {
        return description;
    }
}
