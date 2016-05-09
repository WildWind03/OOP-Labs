package ru.nsu.ccfit.chirikhin.chat.server;

import java.io.IOException;
import java.net.SocketException;

public class Main {
    public static void main(String[] args) throws ServerConfigException, IOException {
        ServerConfig serverConfig = new ServerConfig();
        serverConfig.addNewSocketListenerDescription(new SocketListenerDescriptor(3000, ProtocolName.XML));
        Server server = new Server(serverConfig);
        server.start();
    }
}
