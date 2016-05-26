package ru.nsu.ccfit.chirikhin.chat;

import org.apache.log4j.Logger;
import ru.nsu.ccfit.chirikhin.chat.server.Client;

import java.util.LinkedList;

public class ServerClientListMessage implements ServerMessage{
    private static final Logger logger = Logger.getLogger(ServerClientListMessage.class.getName());
    private final LinkedList<ClientDescriptor> userNames;

    public ServerClientListMessage(LinkedList<ClientDescriptor> userNames) {
        this.userNames = userNames;
    }

    public LinkedList<ClientDescriptor> getUserNames() {
        return userNames;
    }

    @Override
    public void process(ClientMessageController clientMessageController) {
        clientMessageController.handleServerClientListMessage(this);
    }
}
