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

import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;

public class ClientViewController extends Observable implements Observer {
    private static final Logger logger = Logger.getLogger(ClientViewController.class.getName());
    private static final int TIMEOUT_TO_WAIT_LOGIN = 1000;
    private final Object lock = new Object();

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

        return isConnectionSet;
    }

    public boolean login(String username) {
        setChanged();
        notifyObservers(new InfoFromView(Info.LOGIN, username));

        synchronized (lock) {
            try {
                lock.wait(TIMEOUT_TO_WAIT_LOGIN);
            } catch (InterruptedException e) {
                logger.info("Interrupt");
            }
        }

        return isLoggedIn;
    }

    private void sendTextMessage(String str) {
        setChanged();
        notifyObservers(new InfoFromView(Info.TYPED_MESSAGE, str));
    }

    public boolean isServerAnswered() {
        return isServerAnswered;
    }

    @FXML
    public void onKeyPressed(Event event) {
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
        ServerEvent serverEvent = (ServerEvent) arg;
        serverEvent.process(this);
    }

    public void onConnectionSetSuccessfully() {
        isConnectionSet = true;
    }

    public void onConnectionSetFailed() {
        isConnectionSet = false;
    }

    public void onTypedMessageNotDelivered() {
        chatText.appendText("The message hasn't been delivered! There is no connection with server!\n");
    }

    public void onLoginMessageNotDelivered() {
        isServerAnswered = false;
        isLoggedIn = false;
    }

    public void onExitMessageNotDelivered() {
        chatText.appendText("There is no answer from server. The message about logging out can not be delivered\n");
    }

    public void onListOfUsersMessagesNotDelivered() {
        chatText.appendText("There is no answer from server. Can't show list of users\n");
    }

    public void onClientListFailedAnswer(ClientListFailedAnswer clientListFailedAnswer) {
        chatText.appendText("Error! Can not get list of clients. Reason: " + clientListFailedAnswer.getReason() + "\n");
    }

    public void onClientLeftEvent(ClientLeftEvent clientLeftEvent) {
        chatText.appendText(clientLeftEvent.getName() + " left the chat room");
    }

    public void onClientListSuccessAnswer(ClientsListSuccessAnswer clientsListSuccessAnswer) {
        LinkedList<ClientDescriptor> clients = clientsListSuccessAnswer.getClients();
        chatText.appendText("\nThe list of clients:\n");

        for (ClientDescriptor current : clients) {
            chatText.appendText("Nickname: " + current.getName() + "\nClient Type: " + current.getType() + "\n\n");
        }
    }

    public void onLoginFailedAnswer(LoginFailedAnswer loginFailedAnswer) {
        isLoggedIn = false;
        isServerAnswered = true;

        synchronized (lock) {
            lock.notifyAll();
        }
    }

    public void onLoginSuccessAnswer(LoginSuccessAnswer loginSuccessAnswer) {
        logger.info("Handling success login answer");
        isLoggedIn = true;
        isServerAnswered = true;

        synchronized (lock) {
            lock.notifyAll();
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
}
