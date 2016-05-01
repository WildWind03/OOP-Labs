package ru.nsu.ccfit.chirikhin.factory;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

public class JavaFXMLView extends Application {
    private final static Logger logger = Logger.getLogger(JavaFXMLView.class.getName());
    private FactoryController factoryController;

    @Override
    public void stop() throws Exception {
        factoryController.stop();
        super.stop();
    }

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view.fxml"));
            Parent root = loader.load();

            factoryController = new FactoryController("./config.txt", loader.getController());
            factoryController.startFactory();

            Scene scene = new Scene(root);
            stage.setTitle("Factory");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.sizeToScene();
            stage.show();
        } catch (Exception e) {
            System.err.println(e.toString());
            System.exit(-1);
        }
    }


    public static void main(String[] args) {
        launch();
    }


}
