package ru.nsu.ccfit.chirikhin.factory;

import com.sun.javafx.application.PlatformImpl;
import com.sun.javaws.progress.Progress;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

import java.util.Observable;
import java.util.Observer;

public class JavaFXView extends Application {
    private Logger logger = Logger.getLogger(JavaFXView.class.getName());

    Label countOfAccessoryProducersLabel;
    Label engineStorageFullnessLabel;
    Label accessoryStorageFullnessLabel;
    Label carBodyStorageFullnessLabel;
    ProgressBar accessoryStorageBar;
    ProgressBar carBodyStorageBar;
    ProgressBar engineStorageBar;
    GridPane gridPane;
    Stage stage;

    Scene scene;

    public static void main(String[] args) {
        initView(args);
    }

    public static void initView(String[] args) {
        Application.launch(args);
    }

    public JavaFXView() {
        logger.debug("Construct");
    }

    public void onEngineStorageChanged(Storage.StorageContext newStorageContext) {
        logger.debug("On Engine Storage Changed NOT");

        new Thread(new Runnable() {
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        logger.debug("Engine storage changed!");
                        engineStorageBar.setProgress((double) newStorageContext.getSize() / (double) newStorageContext.getMaxSize());
                        System.out.println("Hello");
                        engineStorageFullnessLabel.setText("Engine Storage Fullness: " + newStorageContext.getSize() + "/" + newStorageContext.getMaxSize());
            }
        });
            }

        }).start();


    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        gridPane = new GridPane();
        gridPane.setAlignment(Pos.TOP_LEFT);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25, 25, 25, 25));

        countOfAccessoryProducersLabel = new Label("Count Of Accessory Producers: ");

        engineStorageBar = new ProgressBar(0);
        engineStorageFullnessLabel = new Label("Engine Storage Fullness: ");

        accessoryStorageBar = new ProgressBar(0);
        accessoryStorageFullnessLabel = new Label("Accessory Storage Fullness: ");

        carBodyStorageBar = new ProgressBar(0);
        carBodyStorageFullnessLabel = new Label("Car Body Storage Fullness: ");

        gridPane.add(countOfAccessoryProducersLabel, 0, 0);
        gridPane.add(engineStorageFullnessLabel, 0, 1);
        gridPane.add(engineStorageBar, 1, 1);
        gridPane.add(accessoryStorageFullnessLabel, 0, 2);
        gridPane.add(accessoryStorageBar, 1, 2);
        gridPane.add(carBodyStorageBar, 1, 3);
        gridPane.add(carBodyStorageFullnessLabel, 0, 3);

        scene = new Scene(gridPane, 400, 250);
        logger.debug("JavaFXView start");
        stage = primaryStage;
        primaryStage.setTitle("Factory");

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
