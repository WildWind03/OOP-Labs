package ru.nsu.ccfit.chirikhin.chat.server;

import org.apache.log4j.Logger;
import ru.nsu.ccfit.chirikhin.chat.service.*;
import ru.nsu.ccfit.chirikhin.cyclequeue.CycleQueue;

import java.util.Collection;
import java.util.LinkedList;
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

    public void handleLoginMessage(CommandLogin message, long sessionId) {
        if (null == message) {
            throw new NullPointerException("Null instead of message");
        }

        Client client = clients.get(sessionId);

        if (null == client) {
            throw new NullPointerException("There is no client with such sessionId: " + sessionId);
        }

        if (!client.isLoggedIn()) {
            try {
                setUsername(message.getName(), sessionId);
                client.setChatClientName(message.getType());
                client.login();

                LinkedList<Message> oldMessages = messagesForNewClients.get();
                for (Message message1 : oldMessages) {
                    sendMessageToTheClient((ServerMessage) message1, sessionId);
                }

                EventNewClient eventNewClient = new EventNewClient(message.getName());
                sendMessageToTheClient(new AnswerSuccessLogin(sessionId), sessionId);
                sendMessageToAllClients(eventNewClient);
                addMessageToServerStorage(eventNewClient);
            } catch (NicknameBusyException e) {
                sendMessageToTheClient(new AnswerError(e.getMessage()), sessionId);
            }
        } else {
            sendMessageToTheClient(new AnswerError("The client have already been registered!"), sessionId);
        }
    }

    public void handleTextMessage(CommandText message, long sessionId) {

        if (null == message) {
            throw new NullPointerException("Null instead of message");
        }

        Client client = clients.get(sessionId);

        if (null == client) {
            throw new NullPointerException("There is no client with such sessionId: " + sessionId);
        }

        if (client.isLoggedIn()) {
            AnswerSuccess answerSuccess = new AnswerSuccess();
            sendMessageToTheClient(answerSuccess, sessionId);

            EventText eventText = new EventText(client.getChatClientName(), client.getUsername() + ": " + message.getMessage());

            sendMessageToAllClients(eventText);
            addMessageToServerStorage(eventText);
        } else {
            logger.info("Client is not logged in");
        }
    }
    public void handleClientListMessage(CommandClientList commandClientList, long sessionId) {
        Client client = clients.get(sessionId);

        if (null == client) {
            throw new NullPointerException("There is no client with such sessionId: " + sessionId);
        }

        LinkedList<ClientDescriptor> clientsList = new LinkedList<>();
        Collection<Client> clientCollection = clients.values();

        clientCollection.stream()
                .filter(Client::isLoggedIn)
                .forEachOrdered(client1 -> clientsList.push(new ClientDescriptor(client1.getUsername(), client1.getChatClientName())));

        logger.info("Handling client list message");
        client.receiveMessage(new AnswerClientList(clientsList));

    }

    public void handleExitMessage(ClientMessage message, long sessionId) {
        Client client = clients.get(sessionId);

        if (null == client) {
            throw new NullPointerException("There is no client with such sessionId: " + sessionId);
        }

        sendMessageToTheClient(new AnswerSuccess(), sessionId);
        clients.remove(sessionId);

        sendMessageToAllClients(new EventLogout(client.getUsername()));
        addMessageToServerStorage(new EventLogout(client.getUsername()));
        client.finishAndStop();
    }

    public void handleConnectionFailedMessage(ClientMessage message, long sessionId) {
        Client client = clients.get(sessionId);

        if (null == client) {
            throw new NullPointerException("There is no client with such sessionId: " + sessionId);
        }

        client.delete();

        clients.remove(sessionId);

        if (client.isLoggedIn()) {
            sendMessageToAllClients(new EventLogout(client.getUsername()));
            addMessageToServerStorage(new EventLogout(client.getUsername()));
        }
    }

    private void sendMessageToAllClients(ServerMessage serverMessage) {
        if (null == serverMessage) {
            throw new NullPointerException("Can't send message. ServerMessage is null");
        }

        for (Map.Entry<Long, Client> client : clients.entrySet()) {
            logger.info("Client " + client.getValue().getUsername() + " is ready to get new server message");
            client.getValue().receiveMessage(serverMessage);
        }
    }

    private boolean isUsernameBusy(String username) {
        if (null == username) {
            throw new NullPointerException("Username ca not be null");
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
                if (!(message instanceof ClientMessage)) {
                    throw new ClassCastException("Can not cast a message to a client message");
                }
                ClientMessage clientMessage = (ClientMessage) message;
                clientMessage.process(this);
            }
        } catch (InterruptedException e) {
            logger.error("Interrupt");
        } catch (MessageProcessException e) {
            logger.error(e.toString());
        }
    }
}
