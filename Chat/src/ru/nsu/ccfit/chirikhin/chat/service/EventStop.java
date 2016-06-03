package ru.nsu.ccfit.chirikhin.chat.service;

import org.apache.log4j.Logger;
import ru.nsu.ccfit.chirikhin.chat.client.model.ClientMessageController;
import ru.nsu.ccfit.chirikhin.chat.client.model.ServerEvent;
import ru.nsu.ccfit.chirikhin.chat.client.view.ClientViewController;
import ru.nsu.ccfit.chirikhin.chat.server.Client;
import ru.nsu.ccfit.chirikhin.chat.server.ServerMessageController;

public class EventStop implements ServerMessage {
    private static final Logger logger = Logger.getLogger(EventStop.class.getName());
    private final String session;
    private final ServerMessageController serverMessageController;

    public EventStop(String session, ServerMessageController serverMessageController) {
        if (null == session) {
            throw new NullPointerException("Session can not be null");
        }

        if (null == serverMessageController) {
            throw new NullPointerException("serverMessageController can not be null");
        }
        this.serverMessageController = serverMessageController;
        this.session = session;
    }

    @Override
    public void process(ClientMessageController clientMessageController) {
        serverMessageController.onEventStop(this);
    }

    public String getSession() {
        return session;
    }
}
