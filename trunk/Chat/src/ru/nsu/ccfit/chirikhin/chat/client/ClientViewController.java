package ru.nsu.ccfit.chirikhin.chat.client;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.apache.log4j.Logger;

import java.util.Observable;

public class ClientViewController extends Observable {
    private static final Logger logger = Logger.getLogger(ClientViewController.class.getName());

    private boolean isLoggedIn;

    @FXML
    TextField inputField;

    public boolean tryLogin(ClientProperties clientProperties) {
        setChanged();
        notifyObservers(new InfoFromView(Info.LOGIN, clientProperties));

        return isLoggedIn();
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public void onLoggedInSuccessfully() {
        isLoggedIn = true;
    }

    public void onLoggedInFailed() {
        isLoggedIn = false;
    }

    @FXML
    public void onKeyPressed(Event event) {
        KeyEvent keyEvent = (KeyEvent) event;
        KeyCode keyCode = keyEvent.getCode();

        if (keyCode == KeyCode.ENTER) {
            sendMessage(inputField.getText());
            inputField.clear();
        }
    }

    public void sendMessage(String str) {
        setChanged();
        notifyObservers(new InfoFromView(Info.TYPED_MESSAGE, str));
    }
}
