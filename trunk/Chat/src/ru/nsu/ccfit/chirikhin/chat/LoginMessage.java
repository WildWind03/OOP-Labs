package ru.nsu.ccfit.chirikhin.chat;

import org.apache.log4j.Logger;
import ru.nsu.ccfit.chirikhin.chat.server.ServerMessageController;

public class LoginMessage extends Message{
    private static final Logger logger = Logger.getLogger(LoginMessage.class.getName());

    private final String username;
    private final String chatClientName;

    public LoginMessage(String username, String chatClientName) {
        this.username = username;
        this.chatClientName = chatClientName;
    }

    @Override
    public void process(ServerMessageController serverMessageController) {

    }

    public String getChatClientName() {
        return chatClientName;
    }

    public String getUsername() {
        return username;

    }
}
