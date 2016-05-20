package ru.nsu.ccfit.chirikhin.chat;

import org.apache.log4j.Logger;
import ru.nsu.ccfit.chirikhin.chat.server.Client;
import ru.nsu.ccfit.chirikhin.chat.server.NicknameBusyException;
import ru.nsu.ccfit.chirikhin.cyclequeue.CycleQueue;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

public class ServerMessageController implements Runnable {
    private static final Logger logger = Logger.getLogger(ServerMessageController.class.getName());

    private final ConcurrentHashMap<Long, Client> clients;
    private final CycleQueue<Message> messagesForNewClients;
    private final BlockingQueue<Message> messagesFromClients;

    public ServerMessageController(CycleQueue<Message> messagesForNewClients, ConcurrentHashMap<Long, Client> clients,
                                   BlockingQueue<Message> messagesFromClients) {
        if (null == clients || null == messagesForNewClients || null == messagesFromClients) {
            throw new NullPointerException("Null reference in constructor");
        }

        this.clients = clients;
        this.messagesFromClients = messagesFromClients;
        this.messagesForNewClients = messagesForNewClients;
    }

    public void handleLoginMessage(LoginMessage message, long sessionId) {
        System.out.println("LOGIN");
        System.out.flush();
        logger.info("Login");

        if (null == message) {
            throw new NullPointerException("Null instead of message");
        }

        Client client = clients.get(sessionId);

        if (null == client) {
            throw new NullPointerException("There is no client with such sessionId: " + sessionId);
        }

        if (!client.isLoggedIn()) {
            try {
                setUsername(message.getUsername(), sessionId);
                client.setChatClientName(message.getChatClientName());
                client.login();

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

        if (client.isLoggedIn()) {
            logger.info("Message from logged in user");
            ServerTextMessage serverTextMessage = new ServerTextMessage(message.getAuthor(), message.getText());

            sendMessageToAllClients(serverTextMessage);
            addMessageToServerStorage(serverTextMessage);
        } else {
            logger.info("Client is not logged in");
        }
    }

    public void handleExitMessage(ClientMessage message, long sessionId) {

    }

    private void sendMessageToAllClients(ServerMessage serverMessage) {
        if (null == serverMessage) {
            throw new NullPointerException("Can't send message. ServerMessage is null");
        }

        for (Map.Entry<Long, Client> client : clients.entrySet()) {
            //System.out.println("hey");
            logger.info("Client " + client.getValue().getUsername() + " is ready to get new server message");
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
            client.setUsername(newUsername);
        } else {
            throw new NicknameBusyException("The nickname " + newUsername + " is busy!");
        }
    }

    private void sendMessageToTheClient(ServerMessage serverMessage, long sessionId) {
        if (null == serverMessage) {
            throw new NullPointerException("Null instead of clientMessage");
        }

        //System.out.println("ONE");

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
            while(!Thread.currentThread().isInterrupted()) {
                Message message = messagesFromClients.take();
                logger.info("New message has been taken");
                if (!(message instanceof ClientMessage)) {
                    throw new ClassCastException("Can not cast a message to a client message");
                }
                ClientMessage clientMessage = (ClientMessage) message;
                clientMessage.process(this);
                logger.info("New message has been processed");
            }
        } catch (InterruptedException e) {
            logger.error("Interrupt");
        } catch (MessageProcessException e) {
            logger.error(e.toString());
        }
    }
}
