package ru.nsu.ccfit.chirikhin.chat;

import org.apache.log4j.Logger;

import java.util.LinkedList;

public class ServerClientListMessage implements ServerMessage{
    private static final Logger logger = Logger.getLogger(ServerClientListMessage.class.getName());
    private final LinkedList<String> userNames;

    public ServerClientListMessage(LinkedList<String> userNames) {
        this.userNames = userNames;
    }

    public LinkedList<String> getUserNames() {
        return userNames;
    }

    @Override
    public void process(ClientMessageController clientMessageController) {
        clientMessageController.handleServerClientListMessage(this);
    }
}
