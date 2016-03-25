package ru.nsu.ccfit.chirikhin.factory;

/**
 * Created by cas on 24.03.16.
 */
public class InvalidConfigException extends FactoryException {
    public InvalidConfigException(String str) {
        super(str);
    }
}
