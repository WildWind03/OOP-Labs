package ru.nsu.ccfit.chirikhin.chat;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.BlockingQueue;

public class OutputStreamWriter implements Runnable {
    private static final Logger logger = Logger.getLogger(OutputStreamWriter.class.getName());

    private final MessageSender messageSender;
    private final BlockingQueue <Message> clientMessages;
    private final MessageHandler messageHandler;

    public OutputStreamWriter(OutputStream outputStream, ProtocolName protocolName, BlockingQueue<Message> clientMessages, MessageHandler messageHandler) throws IOException {
        if (null == outputStream || null == protocolName || null == clientMessages || null == messageHandler) {
            throw new NullPointerException("Null in constructor");
        }

        this.messageSender = MessageSenderFactory.createMessageSender(protocolName, outputStream);
        this.clientMessages = clientMessages;
        this.messageHandler = messageHandler;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                logger.info("Trying to take message");
                Message message = clientMessages.take();
                logger.info("New message has been taken");
                messageSender.send(message);
                messageHandler.handle(message);
            }
        } catch (InterruptedException e) {
            logger.error("Interrupt exception");
        }
    }
}
