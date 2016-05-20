package ru.nsu.ccfit.chirikhin.chat;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

public class ObjectMessageSerializer implements MessageSerializer {
    private static final Logger logger = Logger.getLogger(ObjectMessageSerializer.class.getName());
    private final ObjectInputStream objectInputStream;

    public ObjectMessageSerializer(InputStream inputStream) throws IOException {
        if (null == inputStream) {
            throw new NullPointerException("Input stream is null");
        }

        objectInputStream = new ObjectInputStream(inputStream);
    }

    @Override
    public Message read() throws IOException, ClassNotFoundException {
        Object object = objectInputStream.readObject();

        logger.info("Read message");

        if (!(object instanceof Message)) {
            throw new ClassCastException("Can not read! Object is not a message!");
        }

        return (Message) object;
    }

    @Override
    public void stop() {
        try {
            objectInputStream.close();
        } catch (IOException e) {
            logger.error("Closing connection");
        }
    }
}
