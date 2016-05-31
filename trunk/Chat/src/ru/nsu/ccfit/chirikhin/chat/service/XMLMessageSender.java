package ru.nsu.ccfit.chirikhin.chat.service;

import org.apache.log4j.Logger;

import java.io.*;
import java.nio.charset.Charset;

public class XMLMessageSender implements MessageSender {
    private static final Logger logger = Logger.getLogger(XMLMessageSender.class.getName());
    private final XMLMessageParser xmlMessageParser = new XMLMessageParser();
    private final OutputStream outputStream;

    public XMLMessageSender(OutputStream outputStream) {
        if (null == outputStream) {
            throw new NullPointerException("Null in constructor");
        }

        this.outputStream = outputStream;
    }

    @Override
    public void send(Message message) throws IOException {
        if (null == message) {
            throw new NullPointerException("Null instead of message");
        }

        String xml = xmlMessageParser.createXMLFromMessage(message);
        byte[] xmlBytes = xml.getBytes(Charset.forName("UTF-8"));
        int size = xmlBytes.length;
        outputStream.write(size);
        outputStream.write(xmlBytes);
    }

    @Override
    public void close() throws IOException {
        outputStream.close();
    }
}
