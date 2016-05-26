package ru.nsu.ccfit.chirikhin.chat.client;

import org.apache.log4j.Logger;

public class NewClientEvent implements ServerEvent {
    private static final Logger logger = Logger.getLogger(NewClientEvent.class.getName());
    private final String nickname;

    public String getNickname() {
        return nickname;
    }

    public NewClientEvent(String nickname) {

        this.nickname = nickname;
    }

    @Override
    public void process(ClientViewController clientViewController) {
        clientViewController.onNewClientEvent(this);
    }
}
