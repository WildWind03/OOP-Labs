package ru.nsu.ccfit.chirikhin.chat.client.model;

import org.apache.log4j.Logger;
import ru.nsu.ccfit.chirikhin.chat.client.view.ClientViewController;

public class ClientLeftEvent implements ServerEvent{
    private static final Logger logger = Logger.getLogger(ClientLeftEvent.class.getName());
    private final String name;

    public String getName() {
        return name;
    }

    public ClientLeftEvent(String name) {
        if (null == name) {
            throw new NullPointerException("Name is null");
        }

        this.name = name;
    }

    @Override
    public void process(ClientViewController clientViewController) {
        if (null == clientViewController) {
            throw new NullPointerException("Client View Controller is null");
        }

        clientViewController.onClientLeftEvent(this);
    }
}
