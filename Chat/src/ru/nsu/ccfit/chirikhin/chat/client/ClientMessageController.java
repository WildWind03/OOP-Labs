package ru.nsu.ccfit.chirikhin.chat.client;

import org.apache.log4j.Logger;
import ru.nsu.ccfit.chirikhin.chat.Message;
import ru.nsu.ccfit.chirikhin.chat.MessageController;

import java.util.Observable;


public class ClientMessageController extends Observable implements MessageController {
    private static final Logger logger = Logger.getLogger(ClientMessageController.class.getName());

    @Override
    public void acceptMessage(Message message) {
        logger.info("New message received!");
        setChanged();
        notifyObservers(message);
    }
}
