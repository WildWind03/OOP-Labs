package ru.nsu.ccfit.chirikhin.chat;

import org.apache.log4j.Logger;

public class CommandClientList implements ClientMessage {
    private static final Logger logger = Logger.getLogger(CommandClientList.class.getName());
    private final long session;

    public long getSession() {
        return session;
    }

    public CommandClientList(long session) {

        this.session = session;
    }

    @Override
    public void process(ServerMessageController serverMessageController) {
        serverMessageController.handleClientListMessage(this, session);
    }
}
