package ru.nsu.ccfit.chirikhin.chat.client;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Pair;
import org.apache.log4j.Logger;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClientView extends Application {
    private static final Logger logger = Logger.getLogger(ClientView.class.getName());

    private Controller controller;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view.fxml"));
        Parent root = loader.load();

        ClientViewController clientViewController = loader.getController();

        controller = new Controller(clientViewController);

        ClientProperties clientProperties;

        do {
            LoginView loginView = new LoginView();
            Optional<ClientProperties> result = loginView.show();

            if (!result.isPresent()){
                logger.error("Exit");
                return;
            }

            clientProperties = result.get();

            logger.info("Trying to log");

        } while(!clientViewController.tryLogin(clientProperties));

        logger.info("Logged in successfully!");

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
