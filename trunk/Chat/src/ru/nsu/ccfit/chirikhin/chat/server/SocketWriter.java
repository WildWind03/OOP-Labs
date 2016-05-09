package ru.nsu.ccfit.chirikhin.chat.server;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class SocketWriter implements Runnable {
    private static final Logger logger = Logger.getLogger(SocketWriter.class.getName());
    private final UserMessageStore messages;
    private final Socket socket;
    private final OutputStream outputStream;

    public SocketWriter(UserMessageStore messages, Socket socket) throws IOException {
        this.socket = socket;
        this.messages = messages;
        outputStream = socket.getOutputStream();
    }

    @Override
    public void run() {
        try {
            while (true) {
                Message message = messages.takeMessage();
                outputStream.write(message.toBytes());
            }
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }
}
