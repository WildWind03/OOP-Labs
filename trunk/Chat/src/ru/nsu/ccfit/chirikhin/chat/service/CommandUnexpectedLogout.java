package ru.nsu.ccfit.chirikhin.chat.service;

import org.apache.log4j.Logger;
import ru.nsu.ccfit.chirikhin.chat.server.ServerMessageController;

public class CommandUnexpectedLogout implements ClientMessage{
    private static final Logger logger = Logger.getLogger(CommandUnexpectedLogout.class.getName());

    private final String session;

    public CommandUnexpectedLogout(String session) {
        if (null == session) {
            throw new NullPointerException("session can not be null");
        }

        this.session = session;
    }

    @Override
    public void process(ServerMessageController messageController) throws MessageProcessException {
        if (null == messageController) {
            throw new NullPointerException("Null serverMessageController");
        }
        messageController.handleConnectionFailedMessage(this, session);
    }
}
