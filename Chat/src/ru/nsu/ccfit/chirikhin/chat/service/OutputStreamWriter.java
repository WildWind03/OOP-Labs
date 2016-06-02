package ru.nsu.ccfit.chirikhin.chat.service;

import org.apache.log4j.Logger;

import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.BlockingQueue;

public class OutputStreamWriter implements Runnable, Closeable {
    private static final Logger logger = Logger.getLogger(OutputStreamWriter.class.getName());

    private final MessageSender messageSender;
    private final BlockingQueue <Message> clientMessages;

    private boolean sendAndStopMode = false;

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
            while (!Thread.currentThread().isInterrupted() && !sendAndStopMode) {
                Message message = clientMessages.take();
                messageSender.send(message);
            }
        } catch (InterruptedException e) {
            logger.error("Interrupt exception");
        } catch (IOException e) {
            logger.error("Can't send message");
        }

        if (sendAndStopMode) {
            try {
                int size = clientMessages.size();
                for (int k = 0; k < size; ++k) {
                    Message message = clientMessages.poll();
                    if (null == message) {
                        throw new NullPointerException("There aren't enough messages to send");
                    }
                    messageSender.send(message);
                }


                close();
            } catch (IOException e) {
                logger.error("Can't send message");
            }
        }
    }

    public void finishAndStop() {
        sendAndStopMode = true;
    }

    @Override
    public void close() {
        try {
            messageSender.close();
        } catch (IOException e) {
            logger.error("Error while closing Message Sender");
        }
    }
}
