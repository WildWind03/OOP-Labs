package ru.nsu.ccfit.chirikhin.chat;

import org.apache.log4j.Logger;

public class ClientLogoutServerMessage implements ServerMessage {
    private static final Logger logger = Logger.getLogger(ClientLogoutServerMessage.class.getName());
    private final String userName;

    public ClientLogoutServerMessage(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    @Override
    public void process(ClientMessageController clientMessageController) {
        clientMessageController.handleUserLogoutMessage(this);
    }
}
