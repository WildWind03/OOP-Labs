package ru.nsu.ccfit.chirikhin.chat;

import org.apache.log4j.Logger;

public class UserLogoutMessage implements ServerMessage {
    private static final Logger logger = Logger.getLogger(UserLogoutMessage.class.getName());
    private final String userName;

    public UserLogoutMessage(String userName) {
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
