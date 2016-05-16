package ru.nsu.ccfit.chirikhin.chat;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

public class SocketWriter implements Runnable {
    private static final Logger logger = Logger.getLogger(SocketWriter.class.getName());

    private final MessageSender messageSender;
    private final BlockingQueue<ClientMessage> clientMessages;

    public SocketWriter(Socket socket, ProtocolName protocolName, BlockingQueue<ClientMessage> clientMessages) throws IOException {
        if (null == socket || null == protocolName || null == clientMessages) {
            throw new NullPointerException("Null in constructor");
        }

        this.messageSender = MessageSenderFactory.createMessageSender(protocolName, socket.getOutputStream());
        this.clientMessages = clientMessages;
    }

    @Override
    public void run() {
        try {
            while (true) {
                ClientMessage clientMessage = clientMessages.take();
                logger.info ("Ne message has been taken");
                messageSender.send(clientMessage);
            }
        } catch (InterruptedException e) {
            logger.error("Interrupt exception");
        }
    }
}
