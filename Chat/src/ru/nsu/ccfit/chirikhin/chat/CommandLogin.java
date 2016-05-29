package ru.nsu.ccfit.chirikhin.chat;

import org.apache.log4j.Logger;

public class CommandLogin implements ClientMessage {
    private static final Logger logger = Logger.getLogger(CommandLogin.class.getName());
    private final String messageType = "login";

    private final String name;
    private final String type;

    public CommandLogin(String name, String type) {
        this.name = name;
        this.type = type;
    }

    @Override
    public void process(ServerMessageController serverMessageController) throws MessageProcessException {
        if (null == serverMessageController) {
            throw new NullPointerException("Null reference instead of server message controller");
        }

        throw new MessageProcessException("Can't process login message. Use for that Signed Login Message");
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;

    }
}
