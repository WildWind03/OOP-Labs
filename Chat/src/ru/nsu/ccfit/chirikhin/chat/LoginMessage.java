package ru.nsu.ccfit.chirikhin.chat;

import org.apache.log4j.Logger;
import ru.nsu.ccfit.chirikhin.chat.server.ServerMessageController;

public class LoginMessage extends Message{
    private static final Logger logger = Logger.getLogger(LoginMessage.class.getName());

    @Override
    public void process(ServerMessageController serverMessageController) {

    }
}
