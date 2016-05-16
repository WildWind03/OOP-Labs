package ru.nsu.ccfit.chirikhin.chat.client;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.apache.log4j.Logger;
import ru.nsu.ccfit.chirikhin.chat.ServerErrorMessage;
import ru.nsu.ccfit.chirikhin.chat.ClientTextMessage;
import ru.nsu.ccfit.chirikhin.chat.ClientMessage;

import java.util.Observable;
import java.util.Observer;

public class ClientViewController extends Observable implements Observer {
    private static final Logger logger = Logger.getLogger(ClientViewController.class.getName());

    private boolean isLoggedIn;

    @FXML
    TextField inputField;

    @FXML
    TextArea chatText;

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
        ClientMessage clientMessage = (ClientMessage) arg;

        if (clientMessage instanceof ClientTextMessage) {
            chatText.appendText(((ClientTextMessage) clientMessage).getAuthor() + ": " + ((ClientTextMessage) clientMessage).getText() + "\n");
        }

        if (clientMessage instanceof ServerErrorMessage) {
            chatText.appendText(((ServerErrorMessage) clientMessage).getErrorReason());
        }
    }
}
