package ru.nsu.ccfit.chirikhin.chat;

import org.apache.log4j.Logger;
import ru.nsu.ccfit.chirikhin.chat.client.MessageTypeConverter;

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
    public void send(Message message) throws IOException {
        if (null == message) {
            throw new NullPointerException("Null reference instead of message");
        }

        objectOutputStream.writeObject(message);
    }

    @Override
    public void close() throws IOException {
        objectOutputStream.close();
    }
}
