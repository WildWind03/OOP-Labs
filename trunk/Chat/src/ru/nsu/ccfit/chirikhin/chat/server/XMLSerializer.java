package ru.nsu.ccfit.chirikhin.chat.server;

import org.apache.log4j.Logger;

import java.io.InputStream;

public class XMLSerializer implements MessageSerializer {
    private static final Logger logger = Logger.getLogger(XMLSerializer.class.getName());

    @Override
    public Message read(InputStream inputStream) {
        return null;
    }
}
