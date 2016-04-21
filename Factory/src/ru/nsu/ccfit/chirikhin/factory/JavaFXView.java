package ru.nsu.ccfit.chirikhin.factory;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

import java.util.Observable;
import java.util.Observer;

public class JavaFXView extends Application implements Observer {
    private Logger logger = Logger.getLogger(JavaFXView.class.getName());

    public static void initView() {
        Application.launch();
    }

    public JavaFXView() {

    }

    @Override
    public void update(Observable observable, Object o) {
        //switch(observable.getClass().getName()) {
         //   case "Factory" :
         //       break;
       // }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Factory");

        FlowPane flowPane = new FlowPane();
        Scene scene = new Scene(flowPane, 300, 200);
        primaryStage.setScene(scene);

        Label countOfAccessoryProducers = new Label("Count Of Accessory Producers");
        Label accessoryStorage = new Label("Accessory Storage Fullness");

        flowPane.getChildren().add(countOfAccessoryProducers);
        flowPane.getChildren().add(accessoryStorage);
        primaryStage.show();
    }
}
