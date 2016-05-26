package ru.nsu.ccfit.chirikhin.chat.client;

import org.apache.log4j.Logger;

public class ClientLeftEvent implements ServerEvent{
    private static final Logger logger = Logger.getLogger(ClientLeftEvent.class.getName());
    private final String name;

    public String getName() {
        return name;
    }

    public ClientLeftEvent(String name) {
        this.name = name;
    }

    @Override
    public void process(ClientViewController clientViewController) {
        clientViewController.onClientLeftEvent(this);
    }
}
