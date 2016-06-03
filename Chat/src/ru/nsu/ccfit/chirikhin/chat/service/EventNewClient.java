package ru.nsu.ccfit.chirikhin.chat.service;

import org.apache.log4j.Logger;
import ru.nsu.ccfit.chirikhin.chat.client.model.ClientMessageController;

public class EventNewClient implements ServerMessage {
    private static final Logger logger = Logger.getLogger(EventNewClient.class.getName());
    private final String messageType = "userlogin";

    private final String name;

    public EventNewClient(String name) {
        if (null == name) {
            throw new NullPointerException("name is null");
        }
        this.name = name;
    }

    @Override
    public void process(ClientMessageController clientMessageController) {
        if (null == clientMessageController) {
            throw new NullPointerException("Null clientMessageController");
        }

        clientMessageController.handleNewClientServerMessage(this);
    }

    public String getName() {
        return name;
    }
}
