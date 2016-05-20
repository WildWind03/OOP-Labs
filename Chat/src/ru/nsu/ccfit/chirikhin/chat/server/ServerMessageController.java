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

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

public class ServerMessageController implements Runnable {
    private static final Logger logger = Logger.getLogger(ServerMessageController.class.getName());

    private final ConcurrentHashMap<Long, Client> clients;
    private final CycleQueue<ServerMessage> messagesForNewClients;
    private final BlockingQueue<ClientMessage> messagesFromClients;

    public ServerMessageController(CycleQueue<ServerMessage> messagesForNewClients, ConcurrentHashMap<Long, Client> clients,
                                   BlockingQueue<ClientMessage> messagesFromClients) {
        if (null == clients || null == messagesForNewClients || null == messagesFromClients) {
            throw new NullPointerException("Null reference in constructor");
        }

        this.clients = clients;
        this.messagesFromClients = messagesFromClients;
        this.messagesForNewClients = messagesForNewClients;
    }

    public void handleLoginMessage(LoginMessage message, long sessionId) {
        if (null == message) {
            throw new NullPointerException("Null instead of message");
        }

        Client client = clients.get(sessionId);

        if (null == client) {
            throw new NullPointerException("There is no client with such sessionId: " + sessionId);
        }

        if (!client.isRegistered()) {
            try {
                setUsername(message.getUsername(), sessionId);
                client.setChatClientName(message.getChatClientName());
                client.register();

                NewClientServerMessage newClientServerMessage = new NewClientServerMessage(message.getUsername());

                sendMessageToTheClient(new ServerSuccessMessage(sessionId), sessionId);
                sendMessageToAllClients(newClientServerMessage);
                addMessageToServerStorage(newClientServerMessage);
            } catch (NicknameBusyException e) {
                sendMessageToTheClient(new ServerErrorMessage(e.getMessage()), sessionId);
            }
        } else {
            sendMessageToTheClient(new ServerErrorMessage("The client have already been registered!"), sessionId);
        }
    }

    public void handleTextMessage(ClientTextMessage message, long sessionId) {
        if (null == message) {
            throw new NullPointerException("Null instead of message");
        }

        Client client = clients.get(sessionId);

        if (null == client) {
            throw new NullPointerException("There is no client with such sessionId: " + sessionId);
        }

        if (client.isRegistered()) {
            ServerTextMessage serverTextMessage = new ServerTextMessage(message.getAuthor(), message.getText());

            sendMessageToAllClients(serverTextMessage);
            addMessageToServerStorage(serverTextMessage);
        }
    }

    public void handleExitMessage(ClientMessage message, long sessionId) {

    }

    private void sendMessageToAllClients(ServerMessage serverMessage) {
        if (null == serverMessage) {
            throw new NullPointerException("Can't send message. ServerMessage is null");
        }

        for (Map.Entry<Long, Client> client : clients.entrySet()) {
            client.getValue().receiveMessage(serverMessage);
        }
    }

    private boolean isUsernameBusy(String username) {
        if (null == username) {
            throw new NullPointerException("Usename ca not be null");
        }

        for (Map.Entry<Long, Client> client : clients.entrySet()) {
            if (client.getValue().getUsername().equals(username)) {
                return true;
            }
        }

        return false;
    }


    private void setUsername(String newUsername, long sessionId) throws NicknameBusyException {
        if (null == newUsername) {
            throw new NullPointerException("Username can not be null");
        }

        Client client = clients.get(sessionId);

        if (null == client) {
            throw new NullPointerException("There is no client with such session id " + sessionId);
        }

        if ((!isUsernameBusy(newUsername))) {
            client.setUsermame(newUsername);
        } else {
            throw new NicknameBusyException("The nickname " + newUsername + " is busy!");
        }
    }

    private void sendMessageToTheClient(ServerMessage serverMessage, long sessionId) {
        if (null == serverMessage) {
            throw new NullPointerException("Null instead of clientMessage");
        }

        Client client = clients.get(sessionId);
        client.receiveMessage(serverMessage);
    }


    private void addMessageToServerStorage(ServerMessage serverMessage) {
        if (null == serverMessage) {
            throw new NullPointerException("Null instead of server Message");
        }
        try {
            messagesForNewClients.put(serverMessage);
        } catch (InterruptedException e) {
            logger.error("Interrupt");
        }
    }

    @Override
    public void run() {
        try {
            while(true) {
                ClientMessage clientMessage = messagesFromClients.take();
                clientMessage.process(this);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
