package ru.nsu.ccfit.chirikhin.chat.service;

import org.apache.log4j.Logger;
import ru.nsu.ccfit.chirikhin.chat.server.ServerMessageController;

public class CommandClientList implements ClientMessage {
    private static final Logger logger = Logger.getLogger(CommandClientList.class.getName());
    private final String messageType = "list";
    private final String session;

    public String getSession() {
        return session;
    }

    public CommandClientList(String session) {

        this.session = session;
    }

    @Override
    public void process(ServerMessageController serverMessageController) {
        serverMessageController.handleClientListMessage(this, session);
    }
}
