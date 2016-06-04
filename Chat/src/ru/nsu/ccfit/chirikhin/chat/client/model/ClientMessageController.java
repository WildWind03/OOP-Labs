package ru.nsu.ccfit.chirikhin.chat.client.model;

import org.apache.log4j.Logger;
import ru.nsu.ccfit.chirikhin.chat.service.*;

import java.util.Observable;
import java.util.concurrent.BlockingQueue;


public class ClientMessageController extends Observable implements Runnable {
    private static final Logger logger = Logger.getLogger(ClientMessageController.class.getName());

    private final Client client;
    private final BlockingQueue<Message> messages;
    private final BlockingQueue<ClientMessageEnum> historyOfCommands;

    public ClientMessageController(BlockingQueue<ClientMessageEnum> historyOfCommands, BlockingQueue<Message> messages, Client client) {
        if (null == historyOfCommands || null == messages || null == client) {
            throw new NullPointerException("Null references in constructor");
        }

        this.historyOfCommands = historyOfCommands;
        this.client = client;
        this.messages = messages;
    }

    public void handleTextMessage(EventText serverMessage) {
        if (null == serverMessage) {
            throw new NullPointerException("server message is null");
        }

        notifyView(new NewMessageEvent(serverMessage.getMessage(), serverMessage.getName()));
    }

    public void handleNewClientServerMessage(EventNewClient eventNewClient) {
        if (null == eventNewClient) {
            throw new NullPointerException("eventNewClient is null");
        }

        client.sendMessage(new CommandClientList(client.getSessionId()));
        notifyView(new NewClientEvent(eventNewClient.getName()));
    }

    public void handleErrorServerMessage(AnswerError answerError) throws ClientMessageControllerFunctionalityException {
        if (null == answerError) {
            throw new NullPointerException("answerError is null");
        }


        ClientMessageEnum prevMessage = historyOfCommands.poll();

        if (null == prevMessage) {
            throw new ClientMessageControllerFunctionalityException("Error! There is not a history of messages!");
        }

        switch(prevMessage) {
            case LOGIN:
                client.setLoginState(LoginState.ERROR);
                client.disconnect();
                notifyView(new LoginFailedAnswer(answerError.getReason()));
                break;
            case LOGOUT:
                client.disconnect();
                break;
            case CLIENT_LIST:
                notifyView(new ClientListFailedAnswer(answerError.getReason()));
                break;
            case TEXT:
                notifyView(new MessageNotDeliveredAnswer(answerError.getReason()));
                break;
        }
    }

    public void handleServerClientListMessage(AnswerClientList answerClientList) {
        if (null == answerClientList) {
            throw new NullPointerException("answerClientList is null");
        }

        historyOfCommands.poll();
        notifyView(new ClientsListSuccessAnswer(answerClientList.getListusers()));
    }

    public void handleSuccessLoginServerMessage(AnswerSuccessLogin serverSuccessMessage) {
        if (null == serverSuccessMessage) {
            throw new NullPointerException("serverSuccessMessage is null");
        }

        logger.info("Handling success login server message");
        historyOfCommands.poll();
        client.setSessionId(serverSuccessMessage.getSession());
        client.setLoginState(LoginState.SUCCESS);
        notifyView(new LoginSuccessAnswer(serverSuccessMessage.getSession()));
    }

    public void handleSuccessServerAnswer(AnswerSuccess answerSuccess) throws ClientMessageControllerFunctionalityException {
        if (null == answerSuccess) {
            throw new NullPointerException("AnswerSuccess is null");
        }
        ClientMessageEnum prevMessage = historyOfCommands.poll();

        if (null == prevMessage) {
            throw new ClientMessageControllerFunctionalityException("Error! There is not a history of messages!");
        }

        switch(prevMessage) {
            case LOGOUT:
                client.disconnect();
                break;
            case TEXT:
                notifyView(new MessageDeliveredAnswer());
                break;
        }
    }

    public void handleUserLogoutMessage(EventLogout message) {
        if (null == message) {
            throw new NullPointerException("eventLogout is null");
        }

        client.sendMessage(new CommandClientList(client.getSessionId()));
        notifyView(new ClientLeftEvent(message.getName()));
    }

    public void notifyView(ServerEvent serverEvent) {
        if (null == serverEvent) {
            throw new NullPointerException("serverEvent is null");
        }

        setChanged();
        notifyObservers(serverEvent);
    }

    @Override
    public void run() {
        try {
            while(!Thread.currentThread().isInterrupted()) {
                Message message = messages.take();

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

    public void handleEventConnectionFailed(EventConnectionFailed eventConnectionFailed) {
        if (null == eventConnectionFailed) {
            throw new NullPointerException("eventConnectionFailed is null");
        }

        client.disconnect();

        if (LoginState.SUCCESS == client.getLoginState()) {
            notifyView(new ConnectionFailedEvent());
        }

        Thread.currentThread().interrupt();
    }
}
