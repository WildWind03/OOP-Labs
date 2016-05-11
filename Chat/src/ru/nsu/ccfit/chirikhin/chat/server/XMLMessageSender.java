package ru.nsu.ccfit.chirikhin.chat.server;

import org.apache.log4j.Logger;

import java.io.OutputStream;

public class XMLMessageSender implements MessageSender {
    private static final Logger logger = Logger.getLogger(XMLMessageSender.class.getName());

    public XMLMessageSender(OutputStream outputStream) {
    }

    @Override
    public void send(Message message) {

    }
}
