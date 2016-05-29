package ru.nsu.ccfit.chirikhin.chat;

import org.apache.log4j.Logger;

public class EventLogout implements ServerMessage {
    private static final Logger logger = Logger.getLogger(EventLogout.class.getName());
    private final String messageType = "clientlogout";
    private final String name;

    public EventLogout(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public void process(ClientMessageController clientMessageController) {
        clientMessageController.handleUserLogoutMessage(this);
    }
}
