package ru.nsu.ccfit.chirikhin.chat.server;

import org.apache.log4j.Logger;

public class ClientMessage implements Message{
    private static final Logger logger = Logger.getLogger(ClientMessage.class.getName());

    @Override
    public void process(MessageController messageController) {

    }

    @Override
    public byte[] toBytes(MessageTransformer messageTransformer) {
        return new byte[0];
    }
}
