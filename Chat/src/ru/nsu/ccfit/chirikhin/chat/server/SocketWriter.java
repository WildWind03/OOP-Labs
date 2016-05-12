package ru.nsu.ccfit.chirikhin.chat.server;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

public class SocketWriter implements Runnable {
    private static final Logger logger = Logger.getLogger(SocketWriter.class.getName());

    private final MessageSender messageSender;
    private final BlockingQueue<Message> messages;

    public SocketWriter(Socket socket, ProtocolName protocolName, BlockingQueue<Message> messages) throws IOException {
        if (null == socket || null == protocolName || null == messages) {
            throw new NullPointerException("Null in constructor");
        }

        this.messageSender = MessageSenderFactory.createMessageSender(protocolName, socket.getOutputStream());
        this.messages = messages;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Message message = messages.take();
                messageSender.send(message);
            }
        } catch (InterruptedException e) {
            logger.error("Interrupt exception");
        }
    }
}
