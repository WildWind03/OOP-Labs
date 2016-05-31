package ru.nsu.ccfit.chirikhin.chat.client;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.apache.log4j.Logger;
import ru.nsu.ccfit.chirikhin.chat.*;

import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;

public class ClientViewController extends Observable implements Observer {
    private static final Logger logger = Logger.getLogger(ClientViewController.class.getName());
    private ConnectionState connectionState = ConnectionState.NO_ANSWER;

    @FXML
    TextField inputField;
    @FXML
    TextArea chatText;
    @FXML
    Button listOfUsers;

    public ConnectionState connectWithServer(ClientProperties clientProperties) {
        if (null == clientProperties) {
            throw new NullPointerException("Null instead of client properties");
        }

        connectionState = ConnectionState.NO_ANSWER;

        setChanged();
        notifyObservers(new InfoFromView(Info.CONNECT, clientProperties));

        return connectionState;
    }

    private void sendTextMessage(String str) {
        if (null == str) {
            throw new NullPointerException("Text message can not be null");
        }

        setChanged();
        notifyObservers(new InfoFromView(Info.TYPED_MESSAGE, str));
    }

    @FXML
    public void onKeyPressed(Event event) {
        if (null == event) {
            logger.error("Null reference instead of event");
        }

        KeyEvent keyEvent = (KeyEvent) event;

        KeyCode keyCode = keyEvent.getCode();

        if (keyCode == KeyCode.ENTER) {
            sendTextMessage(inputField.getText());
            inputField.clear();
        }
    }

    public void onStop() {
        setChanged();
        notifyObservers(new InfoFromView(Info.STOP, null));
    }

    @FXML
    public void onMousePressed(Event event) {
        setChanged();
        notifyObservers(new InfoFromView(Info.LIST_OF_USERS, null));
    }


    @Override
    public void update(Observable o, Object arg) {
        if (null == arg) {
            throw new NullPointerException("observable can not be null");
        }

        ServerEvent serverEvent = (ServerEvent) arg;
        serverEvent.process(this);
    }

    public void onConnectionSetFailed() {
        connectionState = ConnectionState.CONNECT_FAILED;
    }


    public void onClientListFailedAnswer(ClientListFailedAnswer clientListFailedAnswer) {
        chatText.appendText("Error! Can not get list of clients. Reason: " + clientListFailedAnswer.getReason() + "\n");
    }

    public void onClientLeftEvent(ClientLeftEvent clientLeftEvent) {
        chatText.appendText(clientLeftEvent.getName() + " left the chat room\n");
    }

    public void onClientListSuccessAnswer(ClientsListSuccessAnswer clientsListSuccessAnswer) {
        LinkedList<ClientDescriptor> clients = clientsListSuccessAnswer.getClients();
        chatText.appendText("\nThe list of clients:\n");

        for (ClientDescriptor current : clients) {
            chatText.appendText("Nickname: " + current.getName() + "\nClient Type: " + current.getType() + "\n\n");
        }
    }

    public void onLogoutFailedAnswer(LogoutFailedAnswer logoutFailedAnswer) {
    }

    public void onLogoutSuccessAnswer(LogoutSuccessAnswer logoutSuccessAnswer) {
    }

    public void onMessageDeliveredAnswer(MessageDeliveredAnswer messageDeliveredAnswer) {
    }

    public void onMessageNotDeliveredAnswer(MessageNotDeliveredAnswer messageNotDeliveredAnswer) {
        chatText.appendText("The message hasn't been delivered to user. Reason: " + messageNotDeliveredAnswer.getReason() + "\n");
    }

    public void onNewClientEvent(NewClientEvent newClientEvent) {
        chatText.appendText(newClientEvent.getNickname() + " joined\n");
    }

    public void onNewMessageEvent(NewMessageEvent newMessageEvent) {
        chatText.appendText(newMessageEvent.getMessage() + "\n");
    }

    public void onConnectionFailedEvent(ConnectionFailedEvent connectionFailedEvent) {
        logger.info("The connection failed!");

        Platform.runLater(() -> {
            listOfUsers.setDisable(true);
            inputField.setDisable(true);
            chatText.setDisable(true);

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Windogram");
            alert.setHeaderText("The connection is lost");
            alert.setContentText("We are sorry...");
            alert.showAndWait();
            Platform.exit();
        });
    }

    public void onLoginFailed() {
        connectionState = ConnectionState.LOGGED_FAILED;
    }

    public void onLoginSuccessfully() {
        connectionState = ConnectionState.LOGGED_IN;
    }

    public void onNoAnswer() {
        connectionState = ConnectionState.NO_ANSWER;
    }

    public void onLoginFailedAnswer(LoginFailedAnswer loginFailedAnswer) {
    }

    public void onLoginSuccessAnswer(LoginSuccessAnswer loginSuccessAnswer) {
    }
}
