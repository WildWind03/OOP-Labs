package ru.nsu.ccfit.chirikhin.chat;

import org.apache.log4j.Logger;

import java.io.Closeable;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class ObjectMessageSender implements MessageSender, Closeable {
    private static final Logger logger = Logger.getLogger(ObjectMessageSender.class.getName());
    private final ObjectOutputStream objectOutputStream;

    public ObjectMessageSender(OutputStream outputStream) throws IOException {
        if (null == outputStream) {
            throw new NullPointerException("Null reference instead of OutputStream");
        }

        objectOutputStream = new ObjectOutputStream(outputStream);
    }

    @Override
    public void send(Message message) {
        if (null == message) {
            throw new NullPointerException("Null reference instead of message");
        }

        try {
            objectOutputStream.writeObject(message);
        } catch (IOException e) {
            logger.error("IO error");
        }
    }

    @Override
    public void close() throws IOException {
        objectOutputStream.close();
    }
}
