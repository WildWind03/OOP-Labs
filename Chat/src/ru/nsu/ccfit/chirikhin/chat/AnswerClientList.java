package ru.nsu.ccfit.chirikhin.chat;

import org.apache.log4j.Logger;

import java.util.LinkedList;

public class AnswerClientList implements ServerMessage{
    private static final Logger logger = Logger.getLogger(AnswerClientList.class.getName());
    private final LinkedList<ClientDescriptor> listusers;

    public AnswerClientList(LinkedList<ClientDescriptor> listusers) {
        this.listusers = listusers;
    }

    public LinkedList<ClientDescriptor> getListusers() {
        return listusers;
    }

    @Override
    public void process(ClientMessageController clientMessageController) {
        clientMessageController.handleServerClientListMessage(this);
    }
}
