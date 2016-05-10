package ru.nsu.ccfit.chirikhin.chat.server;

import org.apache.log4j.Logger;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;

public class MessageSerializerFactory {
    private static final Logger logger = Logger.getLogger(MessageSerializerFactory.class.getName());

    private MessageSerializerFactory() {

    }

    public static MessageSerializer createSerializer(ProtocolName protocolName, InputStream inputStream)
            throws ParserConfigurationException, IOException {

        MessageSerializer messageSerializer = null;

        switch(protocolName) {
            case XML:
                messageSerializer = new XMLSerializer(inputStream);
            case SERIALIZE:
                messageSerializer = new ObjectSerializer(inputStream);
        }

        return messageSerializer;
    }
}
