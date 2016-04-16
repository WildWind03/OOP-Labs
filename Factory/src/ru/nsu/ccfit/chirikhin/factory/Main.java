package ru.nsu.ccfit.chirikhin.factory;

import org.apache.log4j.Logger;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        FactoryController factoryController = new FactoryController("./config.txt");
        try {
            factoryController.startFactory();
        } catch (Exception e) {
            System.err.println(e.toString());
            System.exit(-1);
        }
    }
}
