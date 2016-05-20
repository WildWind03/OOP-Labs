package ru.nsu.ccfit.chirikhin.chat;

import org.apache.log4j.Logger;

public class LoginMessage implements ClientMessage {
    private static final Logger logger = Logger.getLogger(LoginMessage.class.getName());

    private final String username;
    private final String chatClientName;

    public LoginMessage(String username, String chatClientName) {
        this.username = username;
        this.chatClientName = chatClientName;
    }

    @Override
    public void process(ServerMessageController serverMessageController) throws MessageProcessException {
        if (null == serverMessageController) {
            throw new NullPointerException("Null reference instead of server message controller");
        }

        throw new MessageProcessException("Can't process login message. Use for that Signed Login Message");
    }

    public String getChatClientName() {
        return chatClientName;
    }

    public String getUsername() {
        return username;

    }
}
