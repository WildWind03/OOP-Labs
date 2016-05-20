package ru.nsu.ccfit.chirikhin.chat.client;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.apache.log4j.Logger;
import ru.nsu.ccfit.chirikhin.chat.NewClientServerMessage;
import ru.nsu.ccfit.chirikhin.chat.ServerErrorMessage;
import ru.nsu.ccfit.chirikhin.chat.ServerMessage;
import ru.nsu.ccfit.chirikhin.chat.ServerTextMessage;

import java.util.Observable;
import java.util.Observer;

public class ClientViewController extends Observable implements Observer {
    private static final Logger logger = Logger.getLogger(ClientViewController.class.getName());
    @FXML
    TextField inputField;
    @FXML
    TextArea chatText;
    private boolean isLoggedIn;

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
        ServerMessage message = (ServerMessage) arg;

        if (message instanceof ServerTextMessage) {
            chatText.appendText(((ServerTextMessage) message).getAuthor() + ": " + ((ServerTextMessage) message).getText() + "\n");
        }

        if (message instanceof ServerErrorMessage) {
            chatText.appendText(((ServerErrorMessage) message).getErrorReason());
        }

        if (message instanceof NewClientServerMessage) {
            chatText.appendText(((NewClientServerMessage) message).getUsername() + " joined\n");
        }
    }
}
