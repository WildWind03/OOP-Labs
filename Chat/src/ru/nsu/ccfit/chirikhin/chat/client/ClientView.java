package ru.nsu.ccfit.chirikhin.chat.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ru.nsu.ccfit.chirikhin.chat.ConfigParser;
import ru.nsu.ccfit.chirikhin.chat.ConsoleParser;
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
            List<Logger> loggers = Collections.<Logger>list(LogManager.getCurrentLoggers());
            loggers.add(LogManager.getRootLogger());
            for (Logger logger : loggers) {
                logger.setLevel(Level.OFF);
            }
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view.fxml"));
        Parent root = loader.load();

        clientViewController = loader.getController();

        new Controller(clientViewController);

        ClientProperties clientProperties;

        while(true) {
            do {
                LoginView loginView = new LoginView();
                Optional<ClientProperties> result = loginView.show();

                if (!result.isPresent()) {
                    return;
                }

                clientProperties = result.get();

                if (clientViewController.connectWithServer(clientProperties)) {
                    break;
                }

            } while (true);

            boolean isLoggedIn = false;

            while(true) {
                EnterUsernameView enterUsernameView = new EnterUsernameView();
                String nickname = enterUsernameView.show();

                if (clientViewController.login(nickname)) {
                    logger.info("Login success");
                    isLoggedIn = true;
                    break;
                }

                if(!clientViewController.isServerAnswered()) {
                    logger.info("Login no answer");
                    break;
                }
            }

            if (isLoggedIn) {
                break;
            }
        }

        Scene scene = new Scene(root);
        stage.setTitle("Windogram");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.sizeToScene();
        stage.show();

        logger.info("Logged in successfully!");
    }

    @Override
    public void stop() throws Exception {
        clientViewController.onStop();
        super.stop();
    }
}
