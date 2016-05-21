package ru.nsu.ccfit.chirikhin.chat.client;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.apache.log4j.Logger;
import ru.nsu.ccfit.chirikhin.chat.*;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;

public class ClientViewController extends Observable implements Observer {
    private final static String CLIENT_NAME = "Windogram";

    private static final Logger logger = Logger.getLogger(ClientViewController.class.getName());

    @FXML
    TextField inputField;
    @FXML
    TextArea chatText;
    @FXML
    Button listOfUsers;

    private boolean isConnectionSet = false;
    private boolean isLoggedIn = false;
    private boolean isServerAnswered = false;

    public boolean connectWithServer(ClientProperties clientProperties) {
        setChanged();
        notifyObservers(new InfoFromView(Info.CONNECT, clientProperties));

        return isConnectionSet();
    }

    public boolean login(String username) {
        setChanged();
        notifyObservers(new InfoFromView(Info.LOGIN, username));

        return isLoggedIn();
    }

    public boolean isServerAnswered() {
        return isServerAnswered;
    }

    public boolean isConnectionSet() {
        return isConnectionSet;
    }

    public void onConnectionSetSuccessfully() {
        isConnectionSet = true;
    }

    public void onConnectionSetFailed() {
        isConnectionSet = false;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    @FXML
    public void onKeyPressed(Event event) {
        KeyEvent keyEvent = (KeyEvent) event;
        KeyCode keyCode = keyEvent.getCode();

        if (keyCode == KeyCode.ENTER) {
            logger.info("Send Message: " + inputField.getText());
            sendTextMessage(inputField.getText());
            inputField.clear();
        }
    }

    public void sendTextMessage(String str) {
        setChanged();
        notifyObservers(new InfoFromView(Info.TYPED_MESSAGE, str));
    }

    @Override
    public void update(Observable o, Object arg) {
        ServerMessageDescriptor serverMessageDescriptor = (ServerMessageDescriptor) arg;

        ServerMessage message = serverMessageDescriptor.getServerMessage();
        ClientMessageEnum previousMessage = serverMessageDescriptor.getPreviousMessage();

        if (message instanceof ServerTextMessage) {
            chatText.appendText(((ServerTextMessage) message).getAuthor() + ": " + ((ServerTextMessage) message).getText() + "\n");
        }

        if (message instanceof ServerErrorAnswer) {
            if (previousMessage.equals(ClientMessageEnum.LOGIN)) {
                isLoggedIn = false;
                isServerAnswered = true;
            }

            chatText.appendText("The message hasn't been delivered! Reason: " + ((ServerErrorAnswer) message).getErrorReason() + "\n");
        }

        if (message instanceof NewClientServerMessage) {
            chatText.appendText(((NewClientServerMessage) message).getUsername() + " joined\n");
        }

        if (message instanceof ServerSuccessLoginAnswer) {
            if (previousMessage.equals(ClientMessageEnum.LOGIN)) {
                isLoggedIn = true;
                isServerAnswered = true;
            }
        }

        if (message instanceof ClientLogoutServerMessage) {
            chatText.appendText(((ClientLogoutServerMessage) message).getUserName() + " left the chat room\n");
        }

        if (message instanceof  ServerSuccessAnswer) {

        }

        if (message instanceof ServerClientListMessage) {
            LinkedList<String> clients = ((ServerClientListMessage) message).getUserNames();
            chatText.appendText("The list of clients:\n");

            for (String current : clients) {
                chatText.appendText(current + "\n");
            }
        }
    }

    public void onTypedMessageNotDelivered() {
        chatText.appendText("The message hasn't been delivered! There is no connection with server!\n");
    }

    public void onLoginMessageNotDelivered() {
        isServerAnswered = false;
        isLoggedIn = false;
    }

    public void onExitMessageNotDelivered() {

    }

    public void onListOfUsersMessagesNotDelivered() {
        chatText.appendText("There is no answer from server\n");
    }

    public void onStop() {
        setChanged();
        notifyObservers(new InfoFromView(Info.LOGOUT, null));
    }

    @FXML
    public void onMousePressed(Event event) {
        setChanged();
        notifyObservers(new InfoFromView(Info.LIST_OF_USERS, null));
    }
}
