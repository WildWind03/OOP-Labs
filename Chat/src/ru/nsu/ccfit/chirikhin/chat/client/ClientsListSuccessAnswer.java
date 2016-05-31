package ru.nsu.ccfit.chirikhin.chat.client;

import org.apache.log4j.Logger;
import ru.nsu.ccfit.chirikhin.chat.service.ClientDescriptor;

import java.util.LinkedList;

public class ClientsListSuccessAnswer implements ServerEvent {
    private static final Logger logger = Logger.getLogger(ClientsListSuccessAnswer.class.getName());
    private final LinkedList<ClientDescriptor> clients;

    public LinkedList<ClientDescriptor> getClients() {
        return clients;
    }

    public ClientsListSuccessAnswer(LinkedList<ClientDescriptor> clients) {
        if (null == clients) {
            throw new NullPointerException("Clients can not be null");
        }

        this.clients = clients;
    }

    @Override
    public void process(ClientViewController clientViewController) {
        if (null == clientViewController) {
            throw new NullPointerException("ClientViewController can not be null");
        }
        clientViewController.onClientListSuccessAnswer(this);
    }
}
