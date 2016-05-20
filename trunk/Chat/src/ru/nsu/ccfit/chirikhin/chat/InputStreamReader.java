package ru.nsu.ccfit.chirikhin.chat;

import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketTimeoutException;
import java.util.concurrent.BlockingQueue;

public class InputStreamReader implements Runnable, Closeable{

    private final static Logger logger = Logger.getLogger(InputStreamReader.class.getName());

    private final MessageSerializer messageSerializer;
    private final MessageHandler messageHandler;
    private final OnResultHandler onResultHandler;

    public InputStreamReader(InputStream inputStream, ProtocolName protocolName, MessageHandler messageHandler, OnResultHandler onResultHandler) throws IOException, ParserConfigurationException {
        if (null == inputStream || null == protocolName || null == messageHandler || null == onResultHandler) {
            throw new NullPointerException("Null in constructor");
        }

        this.messageSerializer = MessageSerializerFactory.createSerializer(protocolName, inputStream);
        this.messageHandler = messageHandler;
        this.onResultHandler = onResultHandler;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                logger.info("Read messsage");
                Message message = null;

                try {
                    message = messageSerializer.serialize();
                } catch (SocketTimeoutException e) {
                    continue;
                }

                logger.info("New message has been read");
                messageHandler.handle(message);
            }
        } catch (IOException | ClassNotFoundException | SAXException e) {
            logger.error("Error while reading message!");
            onResultHandler.handle();
        }
    }

    @Override
    public void close() throws IOException {
        messageSerializer.close();
    }
}
