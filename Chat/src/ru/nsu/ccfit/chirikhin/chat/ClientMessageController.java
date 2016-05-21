package ru.nsu.ccfit.chirikhin.chat;

import org.apache.log4j.Logger;
import ru.nsu.ccfit.chirikhin.chat.client.Client;
import ru.nsu.ccfit.chirikhin.chat.client.ServerMessageDescriptor;

import java.util.Observable;
import java.util.concurrent.BlockingQueue;


public class ClientMessageController extends Observable implements Runnable {
    private static final Logger logger = Logger.getLogger(ClientMessageController.class.getName());

    private final Client client;
    private final BlockingQueue<Message> messages;
    private ClientMessageEnum previousMessage = null;

    public void setPreviousMessage(ClientMessageEnum clientMessageEnum) {
        this.previousMessage = clientMessageEnum;
    }

    public ClientMessageController(BlockingQueue<Message> messages, Client client) {
        this.client = client;
        this.messages = messages;
    }

    public void handleTextMessage(ServerTextMessage serverMessage) {
        notifyView(serverMessage);

        if (serverMessage.getAuthor().equals(client.getNickname())) {
            client.wakeUp();
            previousMessage = null;
        }
    }

    public void handleNewClientServerMessage(NewClientServerMessage newClientServerMessage) {
        notifyView(newClientServerMessage);

        if (newClientServerMessage.getUsername().equals(client.getNickname())) {
            previousMessage = null;
        }
    }

    public void handleErrorServerMessage(ServerErrorAnswer serverErrorAnswer) {
        notifyView(serverErrorAnswer);
        client.wakeUp();
        previousMessage = null;
    }

    public void handleServerClientListMessage(ServerClientListMessage serverClientListMessage) {
        notifyView(serverClientListMessage);
        client.wakeUp();
        previousMessage = null;
    }

    public void handleSuccessLoginServerMessage(ServerSuccessLoginAnswer serverSuccessMessage) {
        client.setSessionId(serverSuccessMessage.getSessionId());
        notifyView(serverSuccessMessage);
        client.wakeUp();
        previousMessage = null;
    }

    public void handleSuccessServerAnswer(ServerSuccessAnswer serverSuccessAnswer) {
        notifyView(serverSuccessAnswer);
        client.wakeUp();
        previousMessage = null;
    }

    public void handleUserLogoutMessage(ClientLogoutServerMessage message) {
        notifyView(message);
    }

    public void notifyView(ServerMessage serverMessage) {
        setChanged();
        notifyObservers(new ServerMessageDescriptor(serverMessage, previousMessage));
    }

    @Override
    public void run() {
        try {
            while(!Thread.currentThread().isInterrupted()) {
                Message message = messages.take();

                logger.info("New message has been received!");

                if (!(message instanceof ServerMessage)) {
                    throw new ClassCastException("Invalid message");
                }

                ServerMessage serverMessage = (ServerMessage) message;

                serverMessage.process(this);

            }
        } catch (InterruptedException e) {
            logger.error("Interrupt");
        }
    }
}
