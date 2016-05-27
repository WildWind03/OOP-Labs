package ru.nsu.ccfit.chirikhin.chat;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.util.concurrent.BlockingQueue;

public class OutputStreamWriter implements Runnable {
    private static final Logger logger = Logger.getLogger(OutputStreamWriter.class.getName());

    private final MessageSender messageSender;
    private final BlockingQueue <Message> clientMessages;

    private int messageToSendBeforeExit = 0;
    private boolean isExit = false;

    public OutputStreamWriter(OutputStream outputStream, ProtocolName protocolName, BlockingQueue<Message> clientMessages) throws IOException {
        if (null == outputStream || null == protocolName || null == clientMessages) {
            throw new NullPointerException("Null in constructor");
        }

        this.messageSender = MessageSenderFactory.createMessageSender(protocolName, outputStream);
        this.clientMessages = clientMessages;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted() && !isExit) {
                Message message = clientMessages.take();
                messageSender.send(message);
            }
        } catch (InterruptedException e) {
            logger.error("Interrupt exception");
        }

        if (isExit) {
            try {
                int size = clientMessages.size();
                for (int k = 0; k < size; ++k) {
                    Message message = clientMessages.take();
                    messageSender.send(message);
                }
            } catch (InterruptedException e) {
                logger.error("Interrupt exception");
            }
        }

        close();
    }

    public void finishAndStop() {
        isExit = true;
    }

    public void close() {
        try {
            messageSender.close();
        } catch (IOException e) {
            logger.error("Error while closing Message Sender");
        }
    }
}
