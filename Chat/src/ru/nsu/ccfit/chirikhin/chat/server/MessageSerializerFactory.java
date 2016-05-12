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

        if (null == protocolName || null == inputStream) {
            throw new NullPointerException("Null reference in constructor");
        }

        MessageSerializer messageSerializer = null;

        switch(protocolName) {
            case XML:
                messageSerializer = new XMLSerializer(inputStream);
                break;
            case SERIALIZE:
                messageSerializer = new ObjectMessageSerializer(inputStream);
                break;
        }

        return messageSerializer;
    }
}
