package ru.nsu.ccfit.chirikhin.chat.client.model;

import org.apache.log4j.Logger;
import org.xml.sax.SAXException;
import ru.nsu.ccfit.chirikhin.chat.service.*;

import javax.xml.parsers.ParserConfigurationException;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketTimeoutException;
import java.util.concurrent.BlockingQueue;

public class ClientInputStreamReader implements Runnable, Closeable {

    private final static Logger logger = Logger.getLogger(ru.nsu.ccfit.chirikhin.chat.client.model.ClientInputStreamReader.class.getName());

    private final MessageSerializer messageSerializer;
    private final BlockingQueue<Message> messages;

    public ClientInputStreamReader(InputStream inputStream, ProtocolName protocolName, BlockingQueue<Message> messages) throws IOException, ParserConfigurationException {
        if (null == inputStream || null == protocolName || null == messages) {
            throw new NullPointerException("Null in constructor");
        }

        this.messages = messages;
        this.messageSerializer = MessageSerializerFactory.createSerializer(protocolName, inputStream);
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Message message;

                try {
                    message = messageSerializer.serialize();
                    messages.put(message);
                } catch (SocketTimeoutException e) {
                    continue;
                } catch (InvalidXMLException e) {
                    logger.error("Invalid XML");
                    break;
                }
            }
        } catch (IOException | ClassNotFoundException | SAXException e) {
            logger.error("Error while reading message!");
            sendConnectionFailed();
        } catch (Exception e) {
            logger.error(e.getMessage());
            sendConnectionFailed();
        } catch (Throwable t) {
            logger.error("Unknown error");
            sendConnectionFailed();
        }
    }

    private void sendConnectionFailed() {
        try {
            messages.put(new EventConnectionFailed());
        } catch (InterruptedException e) {
            logger.error("Interrupt");
        }
    }

    @Override
    public void close() throws IOException {
        messageSerializer.close();
    }
}