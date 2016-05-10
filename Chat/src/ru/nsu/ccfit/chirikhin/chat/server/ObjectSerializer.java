package ru.nsu.ccfit.chirikhin.chat.server;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

public class ObjectSerializer implements MessageSerializer {
    private static final Logger logger = Logger.getLogger(ObjectSerializer.class.getName());
    private final ObjectInputStream objectInputStream;

    public ObjectSerializer(InputStream inputStream) throws IOException {
        if (null == inputStream) {
            throw new NullPointerException("Input stream is null");
        }

        objectInputStream = new ObjectInputStream(inputStream);

    }

    @Override
    public Message read() throws IOException, ClassNotFoundException {
        Object object = objectInputStream.readObject();

        if (! (object instanceof Message)) {
            throw new ClassCastException("Can not read! Object is not a message!");
        }

        Message message= (Message) objectInputStream.readObject();
        return message;
    }
}
