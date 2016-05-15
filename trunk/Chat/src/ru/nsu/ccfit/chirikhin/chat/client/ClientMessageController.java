package ru.nsu.ccfit.chirikhin.chat.client;

import org.apache.log4j.Logger;
import ru.nsu.ccfit.chirikhin.chat.Message;

import java.util.Observable;
import java.util.concurrent.BlockingQueue;

public class ClientMessageController extends Observable implements Runnable{
    private static final Logger logger = Logger.getLogger(ClientMessageController.class.getName());

    private final BlockingQueue<Message> messages;

    public ClientMessageController(BlockingQueue<Message> messages) {
        if (null == messages) {
            throw new NullPointerException("Null reference instead of messages");
        }

        this.messages = messages;
    }

    @Override
    public void run() {
        try{
            while(true) {
                Message message = messages.take();
                logger.info("New message received!");
                setChanged();
                notifyObservers(message);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
