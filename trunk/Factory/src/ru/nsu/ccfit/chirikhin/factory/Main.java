package ru.nsu.ccfit.chirikhin.factory;

import javafx.application.Application;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

import java.io.IOException;

public class Main{

    public static void main(String[] args) {
        //launch();
        FactoryController factoryController = new FactoryController("./config.txt");
        try {
            factoryController.startFactory();
        } catch (Exception e) {
            System.err.println(e.toString());
            System.exit(-1);
        }
    }

   /* @Override
    public void start(Stage primaryStage) throws Exception {
        System.out.println("OK");
    }
    */
}
