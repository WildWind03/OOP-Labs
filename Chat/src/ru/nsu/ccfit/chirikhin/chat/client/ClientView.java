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

    private static final String IP_PATTERN =
            "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";

    private final static String PORT_PATTERN = "\\d+";

    private final Pattern ipPattern = Pattern.compile(IP_PATTERN);
    private final Pattern portPattern = Pattern.compile(PORT_PATTERN);

    private final boolean isValidIp(String ip) {
        Matcher matcher = ipPattern.matcher(ip);
        return matcher.matches();
    }

    private final boolean isValidPort(String port) {
        return portPattern.matcher(port).matches();
    }

    @Override
    public void start(Stage stage) throws Exception {

        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Chat Client");
        dialog.setHeaderText("Chat Client: Authorization");

        dialog.setGraphic(new ImageView(this.getClass().getResource("/chat1.png").toString()));

        ButtonType loginButtonType = new ButtonType("Login", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField username = new TextField();
        username.setPromptText("Username");

        TextField ipTextField = new TextField();
        ipTextField.setPromptText("IP");

        TextField portTextField = new TextField();
        portTextField.setPromptText("Port");

        grid.add(new Label("Username:"), 0, 0);
        grid.add(username, 1, 0);
        grid.add(new Label("IP:"), 0, 1);
        grid.add(ipTextField, 1, 1);
        grid.add(new Label("Port:"), 0, 2);
        grid.add(portTextField, 1, 2);

        Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
        loginButton.setDisable(true);

        username.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(!(isValidPort(portTextField.getText())) || newValue.trim().isEmpty() || !(isValidIp(ipTextField.getText())));
        });

        ipTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(!(isValidPort(portTextField.getText())) || !isValidIp(newValue) || username.getText().trim().isEmpty());
        });

        portTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable((!isValidPort(newValue)) || username.getText().trim().isEmpty() || !(isValidIp(ipTextField.getText())));
        });


        dialog.getDialogPane().setContent(grid);

        Platform.runLater(() -> username.requestFocus());

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return new Pair<>(username.getText(), ipTextField.getText());
            }
            return null;
        });

        Optional<Pair<String, String>> result = dialog.showAndWait();

        result.ifPresent(usernamePassword -> {
            System.out.println("Username=" + usernamePassword.getKey() + ", Password=" + usernamePassword.getValue());
        });


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
