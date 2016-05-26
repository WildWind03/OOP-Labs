package ru.nsu.ccfit.chirikhin.chat;

import jdk.nashorn.internal.ir.Block;
import org.apache.log4j.Logger;
import ru.nsu.ccfit.chirikhin.chat.client.*;

import java.util.Observable;
import java.util.concurrent.BlockingQueue;


public class ClientMessageController extends Observable implements Runnable {
    private static final Logger logger = Logger.getLogger(ClientMessageController.class.getName());

    private final Client client;
    private final BlockingQueue<Message> messages;
    private final BlockingQueue<ClientMessageEnum> historyOfCommands;

    public ClientMessageController(BlockingQueue<ClientMessageEnum> historyOfCommands, BlockingQueue<Message> messages, Client client) {
        this.historyOfCommands = historyOfCommands;
        this.client = client;
        this.messages = messages;
    }

    public void handleTextMessage(ServerTextMessage serverMessage) {
        notifyView(new NewMessageEvent(serverMessage.getText(), serverMessage.getClientType()));
    }

    public void handleNewClientServerMessage(NewClientServerMessage newClientServerMessage) {
        notifyView(new NewClientEvent(newClientServerMessage.getUsername()));
    }

    public void handleErrorServerMessage(ServerErrorAnswer serverErrorAnswer) throws ClientMessageControllerFunctionalityException {
        ClientMessageEnum prevMessage = historyOfCommands.poll();

        if (null == prevMessage) {
            throw new ClientMessageControllerFunctionalityException("Error! There is not a history of messages!");
        }

        switch(prevMessage) {
            case LOGIN:
                notifyView(new LoginFailedAnswer(serverErrorAnswer.getErrorReason()));
                break;
            case LOGOUT:
                notifyView(new LogoutFailedAnswer(serverErrorAnswer.getErrorReason()));
                break;
            case CLIENT_LIST:
                notifyView(new ClientListFailedAnswer(serverErrorAnswer.getErrorReason()));
                break;
            case TEXT:
                notifyView(new MessageNotDeliveredAnswer(serverErrorAnswer.getErrorReason()));
                break;
        }
    }

    public void handleServerClientListMessage(ServerClientListMessage serverClientListMessage) {
        notifyView(new ClientsListSuccessAnswer(serverClientListMessage.getUserNames()));
    }

    public void handleSuccessLoginServerMessage(ServerSuccessLoginAnswer serverSuccessMessage) {
        client.setSessionId(serverSuccessMessage.getSessionId());
        notifyView(new LoginSuccessAnswer(Long.toString(serverSuccessMessage.getSessionId())));
    }

    public void handleSuccessServerAnswer(ServerSuccessAnswer serverSuccessAnswer) {
        notifyView(serverSuccessAnswer);
    }

    public void handleUserLogoutMessage(ClientLogoutServerMessage message) {
        notifyView(message);
    }

    public void notifyView(ServerEvent serverEvent) {
        setChanged();
        notifyObservers(serverEvent);
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
