package ru.nsu.ccfit.chirikhin.chat.service;

import org.apache.log4j.Logger;
import ru.nsu.ccfit.chirikhin.chat.client.ClientMessageController;

public class EventNewClient implements ServerMessage {
    private static final Logger logger = Logger.getLogger(EventNewClient.class.getName());
    private final String messageType = "userlogin";

    private final String name;

    public EventNewClient(String name) {
        this.name = name;
    }

    @Override
    public void process(ClientMessageController clientMessageController) {
        clientMessageController.handleNewClientServerMessage(this);
    }

    public String getName() {
        return name;
    }
}
