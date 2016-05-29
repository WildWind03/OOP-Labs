package ru.nsu.ccfit.chirikhin.chat;

import org.apache.log4j.Logger;
import ru.nsu.ccfit.chirikhin.chat.client.Client;
import ru.nsu.ccfit.chirikhin.chat.client.ClientLeftEvent;
import ru.nsu.ccfit.chirikhin.chat.client.ClientListFailedAnswer;
import ru.nsu.ccfit.chirikhin.chat.client.ClientMessageControllerFunctionalityException;
import ru.nsu.ccfit.chirikhin.chat.client.ClientsListSuccessAnswer;
import ru.nsu.ccfit.chirikhin.chat.client.LoginFailedAnswer;
import ru.nsu.ccfit.chirikhin.chat.client.LoginSuccessAnswer;
import ru.nsu.ccfit.chirikhin.chat.client.MessageDeliveredAnswer;
import ru.nsu.ccfit.chirikhin.chat.client.MessageNotDeliveredAnswer;
import ru.nsu.ccfit.chirikhin.chat.client.NewClientEvent;
import ru.nsu.ccfit.chirikhin.chat.client.NewMessageEvent;
import ru.nsu.ccfit.chirikhin.chat.client.ServerEvent;

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

    public void handleTextMessage(EventText serverMessage) {
        notifyView(new NewMessageEvent(serverMessage.getMessage(), serverMessage.getType()));
    }

    public void handleNewClientServerMessage(EventNewClient eventNewClient) {
        notifyView(new NewClientEvent(eventNewClient.getName()));
    }

    public void handleErrorServerMessage(AnswerError answerError) throws ClientMessageControllerFunctionalityException {
        ClientMessageEnum prevMessage = historyOfCommands.poll();

        if (null == prevMessage) {
            throw new ClientMessageControllerFunctionalityException("Error! There is not a history of messages!");
        }

        switch(prevMessage) {
            case LOGIN:
                notifyView(new LoginFailedAnswer(answerError.getReason()));
                break;
            case LOGOUT:
                //notifyView(new LogoutFailedAnswer(serverErrorAnswer.getReason()));
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
        historyOfCommands.poll();
        notifyView(new ClientsListSuccessAnswer(answerClientList.getListusers()));
    }

    public void handleSuccessLoginServerMessage(AnswerSuccessLogin serverSuccessMessage) {
        logger.info("Handling success login server message");
        historyOfCommands.poll();
        client.setSessionId(serverSuccessMessage.getSession());
        client.setLoginState(true);
        notifyView(new LoginSuccessAnswer(Long.toString(serverSuccessMessage.getSession())));
    }

    public void handleSuccessServerAnswer(AnswerSuccess answerSuccess) throws ClientMessageControllerFunctionalityException {
        ClientMessageEnum prevMessage = historyOfCommands.poll();

        if (null == prevMessage) {
            throw new ClientMessageControllerFunctionalityException("Error! There is not a history of messages!");
        }

        switch(prevMessage) {
            case LOGOUT:
                //notifyView(new LogoutSuccessAnswer());
                client.disconnect();
                break;
            case TEXT:
                notifyView(new MessageDeliveredAnswer());
                break;
        }
    }

    public void handleUserLogoutMessage(EventLogout message) {
        notifyView(new ClientLeftEvent(message.getName()));
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
