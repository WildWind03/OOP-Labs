package ru.nsu.ccfit.chirikhin.chat.client;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.apache.log4j.Logger;
import ru.nsu.ccfit.chirikhin.chat.*;

import java.util.Observable;
import java.util.Observer;

public class ClientViewController extends Observable implements Observer {
    private final static String CLIENT_NAME = "Windogram";

    private final Object lock = new Object();

    private static final Logger logger = Logger.getLogger(ClientViewController.class.getName());
    @FXML
    TextField inputField;
    @FXML
    TextArea chatText;

    private boolean isConnectionSet;
    private boolean isLoggedIn;

    public boolean tryLogin(ClientProperties clientProperties) {
        setChanged();
        notifyObservers(new InfoFromView(Info.LOGIN, clientProperties));

        return isConnectionSet();
    }

    public boolean isConnectionSet() {
        return isConnectionSet;
    }

    public void onLoggedInSuccessfully() {
        isConnectionSet = true;
    }

    public void onLoggedInFailed() {
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
            sendMessage(inputField.getText());
            inputField.clear();
        }
    }

    public void sendMessage(String str) {
        setChanged();
        notifyObservers(new InfoFromView(Info.TYPED_MESSAGE, str));
    }

    @Override
    public void update(Observable o, Object arg) {
        ServerMessage message = (ServerMessage) arg;

        if (message instanceof ServerTextMessage) {
            chatText.appendText(((ServerTextMessage) message).getAuthor() + ": " + ((ServerTextMessage) message).getText() + "\n");
        }

        if (message instanceof ServerErrorAnswer) {
            isLoggedIn = false;
            setChanged();
            notifyObservers(new InfoFromView(Info.DISCONNECT, null));
            synchronized (lock) {
                lock.notify();
            }
        }

        if (message instanceof NewClientServerMessage) {
            chatText.appendText(((NewClientServerMessage) message).getUsername() + " joined\n");
        }

        if (message instanceof ServerSuccessLoginAnswer) {
            logger.info("Server Success Message");
            isLoggedIn = true;
            synchronized (lock) {
                lock.notify();
            }
        }

        if (message instanceof ClientLogoutServerMessage) {
            chatText.appendText(((ClientLogoutServerMessage) message).getUserName() + " left the chat room");
        }
    }

    public void waitLogin(int timeout) {
        synchronized (lock) {
            try {
                lock.wait(timeout);
            } catch (InterruptedException e) {
                logger.error("Interrupt");
            }
        }
    }

    public void onStop() {
        setChanged();
        notifyObservers(new InfoFromView(Info.LOGOUT, null));
    }
}
