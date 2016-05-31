package ru.nsu.ccfit.chirikhin.chat.service;

import org.apache.log4j.Logger;
import ru.nsu.ccfit.chirikhin.chat.server.ServerMessageController;

public class CommandUnexpectedLogout implements ClientMessage{
    private static final Logger logger = Logger.getLogger(CommandUnexpectedLogout.class.getName());

    private final long session;

    public CommandUnexpectedLogout(long session) {
        this.session = session;
    }

    @Override
    public void process(ServerMessageController messageController) throws MessageProcessException {
        messageController.handleConnectionFailedMessage(this, session);
    }
}
