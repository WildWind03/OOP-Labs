package ru.nsu.ccfit.chirikhin.chat.client;

import org.apache.log4j.Logger;
import ru.nsu.ccfit.chirikhin.chat.ClientDescriptor;

import java.util.LinkedList;

public class ClientsListSuccessAnswer implements ServerEvent {
    private static final Logger logger = Logger.getLogger(ClientsListSuccessAnswer.class.getName());
    private final LinkedList<ClientDescriptor> clients;

    public LinkedList<ClientDescriptor> getClients() {
        return clients;
    }

    public ClientsListSuccessAnswer(LinkedList<ClientDescriptor> clients) {

        this.clients = clients;
    }

    @Override
    public void process(ClientViewController clientViewController) {
        clientViewController.onClientListSuccessAnswer(this);
    }
}
