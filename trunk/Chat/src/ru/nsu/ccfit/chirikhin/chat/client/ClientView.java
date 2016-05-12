package ru.nsu.ccfit.chirikhin.chat.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

public class ClientView extends Application {
    private static final Logger logger = Logger.getLogger(ClientView.class.getName());

    private Controller controller;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view.fxml"));
        Parent root = loader.load();

        controller = new Controller(loader.getController());

        Scene scene = new Scene(root);
        stage.setTitle("Chat Client");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.sizeToScene();
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
