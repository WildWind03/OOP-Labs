package ru.nsu.ccfit.chirikhin.chat.server;

import org.apache.log4j.Logger;
import org.xml.sax.SAXException;
import ru.nsu.ccfit.chirikhin.chat.service.*;

import javax.xml.parsers.ParserConfigurationException;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketTimeoutException;

public class ServerInputStreamReader implements Runnable, Closeable {

    private final static Logger logger = Logger.getLogger(ru.nsu.ccfit.chirikhin.chat.server.ServerInputStreamReader.class.getName());

    private final MessageSerializer messageSerializer;
    private final NoArgHandler exceptionHandler;
    private final MessageHandler messageHandler;

    public ServerInputStreamReader(InputStream inputStream, ProtocolName protocolName, NoArgHandler exceptionHandler, MessageHandler messageHandler) throws IOException, ParserConfigurationException {
        if (null == inputStream || null == protocolName || null == exceptionHandler) {
            throw new NullPointerException("Null in constructor");
        }

        this.exceptionHandler = exceptionHandler;
        this.messageHandler = messageHandler;
        this.messageSerializer = MessageSerializerFactory.createSerializer(protocolName, inputStream);
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Message message;

                try {
                    message = messageSerializer.serialize();
                    messageHandler.handle(message);
                } catch (SocketTimeoutException e) {
                    continue;
                } catch (InvalidXMLException e) {
                    logger.error("Invalid XML");
                    break;
                }

            }
        } catch (IOException | ClassNotFoundException | SAXException e) {
            logger.error("Error while reading message!");
            exceptionHandler.handle();
        } catch (Exception e) {
            logger.error(e.getMessage());
            exceptionHandler.handle();
        } catch (Throwable t) {
            logger.error("Unknown error");
            exceptionHandler.handle();
        }
    }

    @Override
    public void close() throws IOException {
        messageSerializer.close();
    }
}
