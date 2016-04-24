package ru.nsu.ccfit.chirikhin.factory;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

public class JavaFXMLView extends Application {
    private Logger logger = Logger.getLogger(JavaFXMLView.class.getName());

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("view.fxml"));
        Parent root = loader.load();

        FactoryController factoryController = new FactoryController("./config.txt", loader.getController());
        factoryController.startFactory();

        Scene scene = new Scene(root);
        stage.setTitle("Factory");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.sizeToScene();
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }


}
