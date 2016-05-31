package ru.nsu.ccfit.chirikhin.chat;

import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketTimeoutException;

public class InputStreamReader implements Runnable, Closeable {

    private final static Logger logger = Logger.getLogger(InputStreamReader.class.getName());

    private final MessageSerializer messageSerializer;
    private final MessageHandler messageHandler;
    private final NoArgHandler exceptionHandler;

    public InputStreamReader(InputStream inputStream, ProtocolName protocolName, MessageHandler messageHandler, NoArgHandler exceptionHandler) throws IOException, ParserConfigurationException {
        if (null == inputStream || null == protocolName || null == messageHandler || null == exceptionHandler) {
            throw new NullPointerException("Null in constructor");
        }

        this.messageSerializer = MessageSerializerFactory.createSerializer(protocolName, inputStream);
        this.messageHandler = messageHandler;
        this.exceptionHandler = exceptionHandler;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Message message;

                try {
                    message = messageSerializer.serialize();
                } catch (SocketTimeoutException e) {
                    continue;
                } catch (InvalidXMLException e) {
                    logger.error("Invalid XML");
                    continue;
                }

                messageHandler.handle(message);
            }
        } catch (IOException | ClassNotFoundException | SAXException e) {
            logger.error("Error while reading message!");
            exceptionHandler.handle();
        }
    }

    @Override
    public void close() throws IOException {
        messageSerializer.close();
    }
}
