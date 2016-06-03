package ru.nsu.ccfit.chirikhin.chat.service;

import org.apache.log4j.Logger;
import ru.nsu.ccfit.chirikhin.chat.client.model.ClientMessageController;

import java.util.LinkedList;

public class AnswerClientList implements ServerMessage{
    private static final Logger logger = Logger.getLogger(AnswerClientList.class.getName());
    private final LinkedList<ClientDescriptor> listusers;

    public AnswerClientList(LinkedList<ClientDescriptor> listusers) {
        if (null == listusers) {
            throw new NullPointerException("Null arg");
        }
        this.listusers = listusers;
    }

    public LinkedList<ClientDescriptor> getListusers() {
        return listusers;
    }

    @Override
    public void process(ClientMessageController clientMessageController) {
        if (null == clientMessageController) {
            throw new NullPointerException("Null clientMessageController");
        }
        clientMessageController.handleServerClientListMessage(this);
    }
}
