package ru.nsu.ccfit.chirikhin.chat;

import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.BlockingQueue;

public class InputStreamReader implements Runnable, Closeable{

    private final static Logger logger = Logger.getLogger(InputStreamReader.class.getName());

    private final MessageSerializer messageSerializer;
    private final MessageHandler messageHandler;

    public InputStreamReader(InputStream inputStream, ProtocolName protocolName, MessageHandler messageHandler) throws IOException, ParserConfigurationException {
        if (null == inputStream || null == protocolName || null == messageHandler) {
            throw new NullPointerException("Null in constructor");
        }

        this.messageSerializer = MessageSerializerFactory.createSerializer(protocolName, inputStream);
        this.messageHandler = messageHandler;
    }

    @Override
    public void run() {
        try {
            while (true) {
                logger.info("Read messsage");
                Message message = messageSerializer.read();
                logger.info("New message has been read");
                messageHandler.handle(message);
            }
        } catch (IOException | ClassNotFoundException | SAXException e) {
            logger.error("Error while reading message!");
        }
    }

    @Override
    public void close() throws IOException {
        messageSerializer.stop();
    }
}
