package ru.nsu.ccfit.chirikhin.chat.service;

import org.apache.log4j.Logger;
import ru.nsu.ccfit.chirikhin.chat.client.model.ClientMessageController;

public class EventLogout implements ServerMessage {
    private static final Logger logger = Logger.getLogger(EventLogout.class.getName());
    private final String messageType = "userlogout";
    private final String name;

    public EventLogout(String name) {
        if (null == name) {
            throw new NullPointerException("name is null");
        }
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public void process(ClientMessageController clientMessageController) {
        if (null == clientMessageController) {
            throw new NullPointerException("Null clientMessageController");
        }

        clientMessageController.handleUserLogoutMessage(this);
    }
}
