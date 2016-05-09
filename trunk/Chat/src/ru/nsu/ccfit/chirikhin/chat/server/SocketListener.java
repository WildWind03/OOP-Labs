package ru.nsu.ccfit.chirikhin.chat.server;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class SocketListener implements Runnable {

    private final Socket socket;
    private final MessageSerializer messageSerializer;
    private final InputStream inputStream;
    private final MessageController messageController;

    public SocketListener(Socket socket, MessageSerializer messageSerializer, MessageController messageController) throws IOException {
        this.socket = socket;
        this.messageSerializer = messageSerializer;
        this.inputStream = socket.getInputStream();
        this.messageController = messageController;
    }

    @Override
    public void run() {
        while(true) {
            Message message = messageSerializer.read(inputStream);
            messageController.takeMessage(message);
        }
    }
}
