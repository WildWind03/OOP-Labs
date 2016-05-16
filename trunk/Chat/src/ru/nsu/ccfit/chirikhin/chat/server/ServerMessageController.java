package ru.nsu.ccfit.chirikhin.chat.server;

import org.apache.log4j.Logger;
import ru.nsu.ccfit.chirikhin.chat.NewClientServerMessage;
import ru.nsu.ccfit.chirikhin.chat.LoginMessage;
import ru.nsu.ccfit.chirikhin.chat.MessageController;
import ru.nsu.ccfit.chirikhin.chat.ServerMessage;
import ru.nsu.ccfit.chirikhin.chat.ServerSuccessMessage;
import ru.nsu.ccfit.chirikhin.chat.ServerErrorMessage;
import ru.nsu.ccfit.chirikhin.chat.ClientTextMessage;
import ru.nsu.ccfit.chirikhin.chat.ServerTextMessage;
import ru.nsu.ccfit.chirikhin.cyclequeue.CycleQueue;
import ru.nsu.ccfit.chirikhin.chat.ClientMessage;
import ru.nsu.ccfit.chirikhin.chat.Message;

import java.util.concurrent.BlockingQueue;

public class ServerMessageController implements MessageController {
    private static final Logger logger = Logger.getLogger(ServerMessageController.class.getName());

    private final BlockingQueue<Client> clients;
    private final CycleQueue<ServerMessage> messages;
    private final Client client;

    public ServerMessageController(CycleQueue<ServerMessage> messages, BlockingQueue<Client> clients, Client client) {
        if (null == clients || null == messages || null == client) {
            throw new NullPointerException("Null reference in constructor");
        }

        this.clients = clients;
        this.messages = messages;
        this.client = client;
    }

    public void handleLoginMessage(LoginMessage message) {
        if (null == message) {
            throw new NullPointerException("Null instead of message");
        }

        if (!client.isRegistered()) {
            try {
                setUsername(message.getUsername());
                client.setChatClientName(message.getChatClientName());
                client.register();

                sendMessageToTheClient(new ServerSuccessMessage(getClientId()));
                sendMessageToAllClients(new NewClientServerMessage(message.getUsername()));
            } catch (NicknameBusyException e) {
                sendMessageToTheClient(new ServerErrorMessage(e.getMessage()));
            }
        } else {
            sendMessageToTheClient(new ServerErrorMessage("The client have already been registered!"));
        }
    }

    public void handleTextMessage(ClientTextMessage message) {
        if (null == message) {
            throw new NullPointerException("Null instead of message");
        }

        if (client.isRegistered()) {
            ServerTextMessage serverTextMessage = new ServerTextMessage(message.getAuthor(), message.getText());

            sendMessageToAllClients(serverTextMessage);
            addMessageToServerStorage(serverTextMessage);
        }
    }

    public void handleExitMessage(ClientMessage message) {

    }

    public void sendMessageToAllClients(ServerMessage serverMessage) {
        if (null == serverMessage) {
            throw new NullPointerException("Can't send message. ServerMessage is null");
        }

        for (Client client : clients) {
            client.receiveMessage(serverMessage);
        }
    }

    private boolean isUsernameBusy(String username) {
        if (null == username) {
            throw new NullPointerException("Usename ca not be null");
        }

        for (Client client : clients) {
            if (client.getUsername().equals(username)) {
                return true;
            }
        }

        return false;
    }


    private void setUsername(String newUsername) throws NicknameBusyException {
        if (null == newUsername) {
            throw new NullPointerException("Username can not be null");
        }

        if ((!isUsernameBusy(newUsername))) {
            client.setUsermame(newUsername);
        } else {
            throw new NicknameBusyException("The nickname " + newUsername + " is busy!");
        }
    }

    private void sendMessageToTheClient(ServerMessage serverMessage) {
        if (null == serverMessage) {
            throw new NullPointerException("Null instead of clientMessage");
        }

        client.receiveMessage(serverMessage);
    }


    public void addMessageToServerStorage(ServerMessage serverMessage) {
        if (null == serverMessage) {
            throw new NullPointerException("Null instead of server Message");
        }
        try {
            messages.put(serverMessage);
        } catch (InterruptedException e) {
            logger.error("Interrupt");
        }
    }

    public long getClientId() {
        return client.getId();
    }


    @Override
    public void acceptMessage(Message message) {
        if (null == message) {
            throw new NullPointerException("Null instead of client Message");
        }

        if (!(message instanceof ClientMessage)) {
            throw new ClassCastException("Message is not a client message");
        }

        logger.info("Message has been taken by controller");
        ClientMessage clientMessage = (ClientMessage) message;
        clientMessage.process(this);
    }
}
