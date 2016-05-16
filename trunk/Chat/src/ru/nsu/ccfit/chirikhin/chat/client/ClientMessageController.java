package ru.nsu.ccfit.chirikhin.chat.client;

import org.apache.log4j.Logger;
import ru.nsu.ccfit.chirikhin.chat.*;

import java.util.Observable;


public class ClientMessageController extends Observable implements MessageController {
    private static final Logger logger = Logger.getLogger(ClientMessageController.class.getName());

    private final User user;

    public ClientMessageController(User user) {
        this.user = user;
    }

    @Override
    public void acceptMessage(Message message) {
        if (!(message instanceof ServerMessage)) {
            throw new ClassCastException("Invalid message");
        }

        ServerMessage serverMessage = (ServerMessage) message;

        serverMessage.process(this);

        logger.info("New message received!");
    }

    public void handleTextMessage(ServerTextMessage serverMessage) {
        setChanged();
        notifyObservers(serverMessage);
    }

    public void handleNewClientServerMessage(NewClientServerMessage newClientServerMessage) {
        setChanged();
        notifyObservers(newClientServerMessage);
    }

    public void handleErrorServerMessage(ServerErrorMessage serverErrorMessage) {
        setChanged();
        notifyObservers(serverErrorMessage);
    }

    public void handleSuccessServerMessage(ServerSuccessMessage serverSuccessMessage) {
        user.setSessionId(serverSuccessMessage.getSessionId());
    }
}
