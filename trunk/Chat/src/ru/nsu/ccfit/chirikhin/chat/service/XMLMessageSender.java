package ru.nsu.ccfit.chirikhin.chat.service;

import org.apache.log4j.Logger;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

public class XMLMessageSender implements MessageSender {
    private static final Logger logger = Logger.getLogger(XMLMessageSender.class.getName());
    private final XMLMessageParser xmlMessageParser = new XMLMessageParser();
    private final DataOutputStream dataOutputStream;

    public XMLMessageSender(OutputStream outputStream) {
        if (null == outputStream) {
            throw new NullPointerException("Null in constructor");
        }

        this.dataOutputStream = new DataOutputStream(outputStream);
    }

    @Override
    public void send(Message message) throws IOException {
        if (null == message) {
            throw new NullPointerException("Null instead of message");
        }

        String xml = xmlMessageParser.createXMLFromMessage(message);
        byte[] xmlBytes = xml.getBytes(Charset.forName("UTF-8"));
        int size = xmlBytes.length;
        dataOutputStream.writeInt(size);
        dataOutputStream.write(xmlBytes);
    }

    @Override
    public void close() throws IOException {
        dataOutputStream.close();
    }
}
