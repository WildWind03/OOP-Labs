package ru.nsu.ccfit.chirikhin.chat.server;

import org.apache.log4j.Logger;
import ru.nsu.ccfit.chirikhin.chat.service.*;

import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.BlockingQueue;

class ServerOutputStreamWriter implements Runnable, Closeable {
    private static final Logger logger = Logger.getLogger(ru.nsu.ccfit.chirikhin.chat.server.ServerOutputStreamWriter.class.getName());

    private final MessageSender messageSender;
    private final BlockingQueue<Message> clientMessages;

    public ServerOutputStreamWriter(OutputStream outputStream, ProtocolName protocolName, BlockingQueue<Message> clientMessages) throws IOException {
        if (null == outputStream || null == protocolName || null == clientMessages) {
            throw new NullPointerException("Null in constructor");
        }

        this.messageSender = MessageSenderFactory.createMessageSender(protocolName, outputStream);
        this.clientMessages = clientMessages;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Message message = clientMessages.take();
                if (message instanceof EventStop)
                {
                    ((EventStop) message).process(null);
                    break;
                }
                messageSender.send(message);
            }
        } catch (InterruptedException e) {
            logger.error("Interrupt exception");
        } catch (IOException e) {
            logger.error("Can't send message");
        } catch (Exception e) {
            logger.error(e.getMessage());
        } catch (Throwable t) {
            logger.error("Unknown exception");
        }

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
