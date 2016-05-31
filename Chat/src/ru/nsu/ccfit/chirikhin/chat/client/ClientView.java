package ru.nsu.ccfit.chirikhin.chat.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ru.nsu.ccfit.chirikhin.chat.*;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ClientView extends Application {
    private static final Logger logger = Logger.getLogger(ClientView.class.getName());
    private static final int ERROR_EXIT = -1;
    private final static String CLIENT_TYPE = "Windogram";

    private ClientViewController clientViewController;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        try {
            Parameters parameters = getParameters();
            List<String> args = parameters.getRaw();
            ConsoleParser consoleParser = new ConsoleParser(args);
            ConfigParser configParser = null;

            try {
                configParser = new ConfigParser(consoleParser.getPathToFile());
            } catch (IOException | InvalidConfigException e) {
                logger.error("Error while parsing config");
                System.exit(ERROR_EXIT);
            }

            if (!configParser.isLog()) {
                LoggerController.switchOffLogger();
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view.fxml"));
            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException e) {
                logger.error("Error while loading fxml form");
                System.exit(ERROR_EXIT);
            }

            clientViewController = loader.getController();
            new Controller(clientViewController);

            ClientProperties clientProperties;

            do {
                LoginView loginView = new LoginView();
                Optional<ClientProperties> result = loginView.show();

                if (!result.isPresent()) {
                    return;
                }

                clientProperties = result.get();

                ConnectionState connectionState = clientViewController.connectWithServer(clientProperties);

                Alert alert;
                switch (connectionState) {
                    case CONNECT_FAILED:
                        alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle(CLIENT_TYPE);
                        alert.setHeaderText("The connection can not be setup");
                        alert.setContentText("The server doesn't answer");
                        alert.showAndWait();
                        continue;

                    case LOGGED_FAILED:
                        alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle(CLIENT_TYPE);
                        alert.setHeaderText("Can't log in");
                        alert.setContentText("Try to change nickname");
                        alert.showAndWait();
                        continue;

                    case LOGGED_IN:
                        break;
                    case NO_ANSWER:
                        alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle(CLIENT_TYPE);
                        alert.setHeaderText("The connection can not be setup");
                        alert.setContentText("Can not connect to the server");
                        alert.showAndWait();
                        continue;
                }

                break;

            } while (true);

            logger.info("The connection is set. Logged in successfully");

            Scene scene = new Scene(root);
            stage.setTitle(CLIENT_TYPE);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.sizeToScene();
            stage.show();
        } catch (Exception e) {
            logger.error("Runtime error " + e.getMessage());
            System.exit(ERROR_EXIT);
        } catch (Throwable throwable) {
            logger.error(("Unknown runtime error"));
            System.exit(ERROR_EXIT);
        }
    }

    @Override
    public void stop() throws Exception {
        clientViewController.onStop();
        super.stop();
    }
}
