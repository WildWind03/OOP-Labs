package ru.nsu.ccfit.chirikhin.chat.server;

import java.net.Socket;

public class SocketListener implements Runnable {

    private final Socket socket;

    public SocketListener(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {

        }
    }
}
