package ru.nsu.ccfit.chirikhin.factory;

/**
 * Created by cas on 21.03.16.
 */
public class EngineProducer implements Producer {
    static int countOfMadeEngines = 0;

    public EngineProducer() {
    }

    @Override
    public CarDetail produce() {
        CarDetail engine = new Engine(++countOfMadeEngines);
        return engine;
    }
}
