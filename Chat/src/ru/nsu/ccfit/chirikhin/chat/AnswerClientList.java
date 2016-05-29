package ru.nsu.ccfit.chirikhin.chat;

import org.apache.log4j.Logger;

import java.util.LinkedList;

public class AnswerClientList implements ServerMessage{
    private static final Logger logger = Logger.getLogger(AnswerClientList.class.getName());
    private final LinkedList<ClientDescriptor> user;

    public AnswerClientList(LinkedList<ClientDescriptor> user) {
        this.user = user;
    }

    public LinkedList<ClientDescriptor> getUser() {
        return user;
    }

    @Override
    public void process(ClientMessageController clientMessageController) {
        clientMessageController.handleServerClientListMessage(this);
    }
}
