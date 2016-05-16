package ru.nsu.ccfit.chirikhin.chat;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.BlockingQueue;

public class SocketWriter implements Runnable {
    private static final Logger logger = Logger.getLogger(SocketWriter.class.getName());

    private final MessageSender messageSender;
    private final BlockingQueue <? extends Message> clientMessages;

    public SocketWriter(OutputStream outputStream, ProtocolName protocolName, BlockingQueue<? extends Message> clientMessages) throws IOException {
        if (null == outputStream || null == protocolName || null == clientMessages) {
            throw new NullPointerException("Null in constructor");
        }

        this.messageSender = MessageSenderFactory.createMessageSender(protocolName, outputStream);
        this.clientMessages = clientMessages;
    }

    @Override
    public void run() {
        try {
            while (true) {
                logger.info("Trying to take message");
                Message message = clientMessages.take();
                logger.info("New message has been taken");
                messageSender.send(message);
            }
        } catch (InterruptedException e) {
            logger.error("Interrupt exception");
        }
    }
}
