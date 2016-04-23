package ru.nsu.ccfit.chirikhin.factory;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

import java.io.IOException;

import static javafx.application.Application.launch;

public class Main{

    public static void main(String[] args) {
        try {
            FactoryController factoryController = new FactoryController("./config.txt");
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
