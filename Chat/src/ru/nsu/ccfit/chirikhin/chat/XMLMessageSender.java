package ru.nsu.ccfit.chirikhin.chat;

import org.apache.log4j.Logger;

import java.io.OutputStream;

public class XMLMessageSender implements MessageSender {
    private static final Logger logger = Logger.getLogger(XMLMessageSender.class.getName());

    public XMLMessageSender(OutputStream outputStream) {
        if (null == outputStream) {
            throw new NullPointerException("Null in constructor");
        }
    }

    @Override
    public void send(Message message) {
        if (null == message) {
            throw new NullPointerException("Null instead of message");
        }
    }
}
