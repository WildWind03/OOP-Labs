package ru.nsu.ccfit.chirikhin.chat.client;

import org.apache.log4j.Logger;
import ru.nsu.ccfit.chirikhin.chat.ProtocolName;

public class ProtocolParser {
    private static final Logger logger = Logger.getLogger(ProtocolParser.class.getName());

    private ProtocolParser() {

    }

    public static ProtocolName getProtocol(String protocolString) {
        if (null == protocolString) {
            throw new NullPointerException("Null reference instead of name of the protocol!");
        }

        ProtocolName protocolName;

        switch (protocolString) {
            case "XML":
                protocolName = ProtocolName.XML;
                break;
            case "SERIALIZE":
                protocolName = ProtocolName.SERIALIZE;
                break;
            default:
                throw new IllegalArgumentException(protocolString + " doesn't fit to any protocol");
        }

        return protocolName;
    }
}
