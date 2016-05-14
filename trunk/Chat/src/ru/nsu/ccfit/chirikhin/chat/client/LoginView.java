package ru.nsu.ccfit.chirikhin.chat.client;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import org.apache.log4j.Logger;

import java.util.Optional;

public class LoginView {
    private static final Logger logger = Logger.getLogger(LoginView.class.getName());

    public Optional<ClientProperties> show() {
        Dialog<ClientProperties> dialog = new Dialog<>();
        dialog.setTitle("Chat Client");
        dialog.setHeaderText("Chat Client: Authorization");

        dialog.setGraphic(new ImageView(this.getClass().getResource("/chat.png").toString()));

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

            ///////////////FOR TEST
        portTextField.setText("3000");
        ipTextField.setText("127.0.0.1");
        username.setText("Wind");
        loginButton.setDisable(false);
        /////////////////////////

        username.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(!(IPChecker.isPort(portTextField.getText())) || newValue.trim().isEmpty() || !(IPChecker.isIp(ipTextField.getText())));
        });

        ipTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(!(IPChecker.isPort(portTextField.getText())) || !IPChecker.isIp(newValue) || username.getText().trim().isEmpty());
        });

        portTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable((!IPChecker.isPort(newValue)) || username.getText().trim().isEmpty() || !(IPChecker.isIp(ipTextField.getText())));
        });


        dialog.getDialogPane().setContent(grid);

        Platform.runLater(username::requestFocus);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                int port = Integer.parseInt(portTextField.getText());
                String ipString = ipTextField.getText();
                String usernameString = username.getText();
                return new ClientProperties(port, ipString, usernameString);
            }

            return null;
        });

        Optional<ClientProperties> result = dialog.showAndWait();

        return result;
    }
}
