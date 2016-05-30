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
import ru.nsu.ccfit.chirikhin.chat.ConfigParser;
import ru.nsu.ccfit.chirikhin.chat.ConsoleParser;
import ru.nsu.ccfit.chirikhin.chat.LoggerController;
import ru.nsu.ccfit.chirikhin.chat.TimeoutException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ClientView extends Application {
    private static final Logger logger = Logger.getLogger(ClientView.class.getName());

    private ClientViewController clientViewController;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parameters parameters = getParameters();
        List<String> args = parameters.getRaw();
        ConsoleParser consoleParser = new ConsoleParser(args);
        ConfigParser configParser = new ConfigParser(consoleParser.getPathToFile());

        if (!configParser.isLog()) {
            LoggerController.switchOffLogger();
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view.fxml"));
        Parent root = loader.load();

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

                if (clientViewController.connectWithServer(clientProperties)) {
                    break;
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Windogram");
                    alert.setHeaderText("The connection can not be setup");
                    alert.setContentText("Try again or choose another server/protocol/nickname");
                    alert.showAndWait();
                }

            } while (true);

            logger.info("Connection is set!");

        Scene scene = new Scene(root);
        stage.setTitle("Windogram");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.sizeToScene();
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        clientViewController.onStop();
        super.stop();
    }
}
