package ru.nsu.ccfit.chirikhin.chat.client.view;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import org.apache.log4j.Logger;
import ru.nsu.ccfit.chirikhin.chat.client.model.ClientProperties;
import ru.nsu.ccfit.chirikhin.chat.client.model.IPChecker;
import ru.nsu.ccfit.chirikhin.chat.client.model.ProtocolParser;
import ru.nsu.ccfit.chirikhin.chat.service.ProtocolName;

import java.util.Optional;

public class LoginView {
    private static final Logger logger = Logger.getLogger(LoginView.class.getName());
    private final static String CHAT_CLIENT_NAME = "Windogram";
    private final static String HEADER_TEXT = "Windogram: Authorization";

    public Optional<ClientProperties> show() {
        Dialog<ClientProperties> dialog = new Dialog<>();
        dialog.setTitle(CHAT_CLIENT_NAME);
        dialog.setHeaderText(HEADER_TEXT);

        dialog.setGraphic(new ImageView(this.getClass().getResource("/chat.png").toString()));

        ButtonType loginButtonType = new ButtonType("Login", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField ipTextField = new TextField();
        ipTextField.setPromptText("IP");

        TextField portTextField = new TextField();
        portTextField.setPromptText("Port");

        TextField nicknameTextField = new TextField();
        nicknameTextField.setPromptText("Nickname");

        ChoiceBox<String> protocolType = new ChoiceBox<>();
        protocolType.setItems(FXCollections.observableArrayList("SERIALIZE", "XML"));
        protocolType.getSelectionModel().selectLast();

        grid.add(new Label("Nickname:"), 0, 0);
        grid.add(nicknameTextField, 1, 0);
        grid.add(new Label("IP:"), 0, 1);
        grid.add(ipTextField, 1, 1);
        grid.add(new Label("Port:"), 0, 2);
        grid.add(portTextField, 1, 2);
        grid.add(new Label("Protocol:"), 0, 3);
        grid.add(protocolType, 1, 3);

        Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
        loginButton.setDisable(true);

        ///////////////FOR TEST
        portTextField.setText("9000");
        ipTextField.setText("127.0.0.1");
        nicknameTextField.setText("Wind");
        loginButton.setDisable(false);
        /////////////////////////

        ipTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(!(IPChecker.isPort(portTextField.getText())) || !IPChecker.isIp(newValue) || nicknameTextField.getText().isEmpty());
        });

        portTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable((!IPChecker.isPort(newValue)) || !(IPChecker.isIp(ipTextField.getText())) || nicknameTextField.getText().isEmpty());
        });

        nicknameTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable((!(IPChecker.isPort(portTextField.getText()))) || !(IPChecker.isIp(ipTextField.getText())) || newValue.isEmpty());
        });


        dialog.getDialogPane().setContent(grid);

        Platform.runLater(ipTextField::requestFocus);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                int port = Integer.parseInt(portTextField.getText());
                String ipString = ipTextField.getText();
                ProtocolName protocolName = ProtocolParser.getProtocol(protocolType.getValue());
                String nickname = nicknameTextField.getText();
                return new ClientProperties(port, ipString, protocolName, nickname);
            }

            return null;
        });

        return dialog.showAndWait();
    }
}
