package ru.nsu.ccfit.chirikhin.chat;

import org.apache.log4j.Logger;
import ru.nsu.ccfit.chirikhin.chat.client.ClientMessageController;

public class NewClientServerMessage implements ServerMessage {
    private static final Logger logger = Logger.getLogger(NewClientServerMessage.class.getName());

    private final String username;

    public NewClientServerMessage(String username) {
        this.username = username;
    }

    @Override
    public void process(ClientMessageController clientMessageController) {
        clientMessageController.handleNewClientServerMessage(this);
    }

    public String getUsername() {
        return username;
    }
}
