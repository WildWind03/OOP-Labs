package ru.nsu.ccfit.chirikhin.chat.client;

import org.apache.log4j.Logger;
import ru.nsu.ccfit.chirikhin.chat.ClientMessageEnum;
import ru.nsu.ccfit.chirikhin.chat.ServerMessage;

public class ServerMessageDescriptor {
    private static final Logger logger = Logger.getLogger(ServerMessageDescriptor.class.getName());
    private final ServerMessage serverMessage;
    private final ClientMessageEnum previousMessage;

    public ServerMessageDescriptor(ServerMessage serverMessage, ClientMessageEnum previousMessage) {
        this.serverMessage = serverMessage;
        this.previousMessage = previousMessage;
    }

    public ServerMessage getServerMessage() {
        return serverMessage;
    }

    public ClientMessageEnum getPreviousMessage() {
        return previousMessage;
    }
}
