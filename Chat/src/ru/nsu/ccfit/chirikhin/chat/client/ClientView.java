package ru.nsu.ccfit.chirikhin.chat.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ClientView extends Application {
    private static final Logger logger = Logger.getLogger(ClientView.class.getName());

    private Controller controller;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        List<Logger> loggers = Collections.<Logger>list(LogManager.getCurrentLoggers());
        loggers.add(LogManager.getRootLogger());
        for (Logger logger : loggers) {
            logger.setLevel(Level.OFF);
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view.fxml"));
        Parent root = loader.load();

        ClientViewController clientViewController = loader.getController();

        controller = new Controller(clientViewController);

        ClientProperties clientProperties;

        do {
            LoginView loginView = new LoginView();
            Optional<ClientProperties> result = loginView.show();

            if (!result.isPresent()) {
                logger.error("Exit");
                return;
            }

            clientProperties = result.get();

            logger.info("Trying to log");

        } while (!clientViewController.tryLogin(clientProperties));

        logger.info("Logged in successfully!");

        Scene scene = new Scene(root);
        stage.setTitle("Chat Client");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.sizeToScene();
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        controller.disconnectUser();
        super.stop();
    }
}
