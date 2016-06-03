package ru.nsu.ccfit.chirikhin.chat.client.view;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.apache.log4j.Logger;
import ru.nsu.ccfit.chirikhin.chat.client.controller.Info;
import ru.nsu.ccfit.chirikhin.chat.client.controller.InfoFromView;
import ru.nsu.ccfit.chirikhin.chat.client.model.*;
import ru.nsu.ccfit.chirikhin.chat.service.ClientDescriptor;

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
    ListView<String> userList;

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

        if (KeyCode.ENTER == keyCode) {
            sendTextMessage(inputField.getText());
            inputField.clear();
        }
    }

    public void onStop() {
        setChanged();
        notifyObservers(new InfoFromView(Info.STOP, null));
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
        if (null == clientListFailedAnswer) {
            throw new NullPointerException("clientListFailedAnswer is null");
        }

        chatText.appendText("Error! Can not get list of clients. Reason: " + clientListFailedAnswer.getReason() + "\n");
    }

    public void onClientLeftEvent(ClientLeftEvent clientLeftEvent) {
        if (null == clientLeftEvent) {
            throw new NullPointerException("clientLeftEvent is null");
        }

        chatText.appendText(clientLeftEvent.getName() + " left the chat room\n");
    }

    public void onClientListSuccessAnswer(ClientsListSuccessAnswer clientsListSuccessAnswer) {
        if (null == clientsListSuccessAnswer) {
            throw new NullPointerException("ClientListSuccessAnswer is null");
        }

        LinkedList<ClientDescriptor> clients = clientsListSuccessAnswer.getClients();

        LinkedList<String> clientNames = new LinkedList<>();
        for (ClientDescriptor clientDescriptor : clients) {
            String result = clientDescriptor.getName() + " (" + clientDescriptor.getType() + ")";
            clientNames.push(result);
        }

        Platform.runLater(() -> {
            ObservableList<String> observableList = FXCollections.observableList(clientNames);
            userList.setItems(observableList);

        });

    }

    public void onLogoutFailedAnswer(LogoutFailedAnswer logoutFailedAnswer) {
        if (null == logoutFailedAnswer) {
            throw new NullPointerException("logoutFailedAnswer");
        }
    }

    public void onLogoutSuccessAnswer(LogoutSuccessAnswer logoutSuccessAnswer) {
        if (null == logoutSuccessAnswer) {
            throw new NullPointerException("logoutSuccessAnswer is null");
        }
    }

    public void onMessageDeliveredAnswer(MessageDeliveredAnswer messageDeliveredAnswer) {
        if (null == messageDeliveredAnswer) {
            throw new NullPointerException("messageDeliveredAnswer is null");
        }
    }

    public void onMessageNotDeliveredAnswer(MessageNotDeliveredAnswer messageNotDeliveredAnswer) {
        if (null == messageNotDeliveredAnswer) {
            throw new NullPointerException("messageNotDeliveredAnswer is null");
        }

        chatText.appendText("The message hasn't been delivered to user. Reason: " + messageNotDeliveredAnswer.getReason() + "\n");
    }

    public void onNewClientEvent(NewClientEvent newClientEvent) {
        if (null == newClientEvent) {
            throw new NullPointerException("newClientEvent is null");
        }

        chatText.appendText(newClientEvent.getNickname() + " joined\n");
    }

    public void onNewMessageEvent(NewMessageEvent newMessageEvent) {
        if (null == newMessageEvent) {
            throw new NullPointerException("newMessageEvent is null");
        }

        chatText.appendText(newMessageEvent.getAuthor() + ": " + newMessageEvent.getMessage() + "\n");
    }

    public void onConnectionFailedEvent(ConnectionFailedEvent connectionFailedEvent) {
        if (null == connectionFailedEvent) {
            throw new NullPointerException("connectionFailedEvent is null");
        }

        logger.info("The connection failed!");

        Platform.runLater(() -> {
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
        if (null == loginFailedAnswer) {
            throw new NullPointerException("LoginFailedAnswer is null");
        }
    }

    public void onLoginSuccessAnswer(LoginSuccessAnswer loginSuccessAnswer) {
        if (null == loginSuccessAnswer) {
            throw new NullPointerException("loginSuccessAnswer is null");
        }
    }
}
