package ru.nsu.ccfit.chirikhin.factory;

import org.apache.log4j.Logger;

public class Main {

    public static void main(String[] args) {
        FactoryController factoryController = new FactoryController("./config");
        factoryController.startFactory();
    }
}
