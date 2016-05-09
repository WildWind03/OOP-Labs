package ru.nsu.ccfit.chirikhin.chat.server;

import com.sun.org.apache.xml.internal.security.algorithms.MessageDigestAlgorithm;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

public class SocketWriter implements Runnable {
    private static final Logger logger = Logger.getLogger(SocketWriter.class.getName());
    private final BlockingQueue<Message> messages;
    private final Socket socket;
    private final OutputStream outputStream;
    private final MessageTransformer messageTransformer;

    public SocketWriter(BlockingQueue<Message> messages, Socket socket, MessageTransformer messageTransformer) throws IOException {
        this.socket = socket;
        this.messages = messages;
        outputStream = socket.getOutputStream();
        this.messageTransformer = messageTransformer;

    }

    @Override
    public void run() {
        try {
            while (true) {
                Message message = messages.take();
                outputStream.write(message.toBytes(messageTransformer));
            }
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }
}
