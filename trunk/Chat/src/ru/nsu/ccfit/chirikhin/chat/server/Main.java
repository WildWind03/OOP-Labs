package ru.nsu.ccfit.chirikhin.chat.server;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ru.nsu.ccfit.chirikhin.chat.ProtocolName;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;

public class Main {
    public static void main(String[] args) throws ServerConfigException, IOException {
        try {

            ServerConfig serverConfig = new ServerConfig();
            serverConfig.addNewSocketListenerDescription(new SocketListenerDescriptor(3000, ProtocolName.SERIALIZE));
            Server server = new Server(serverConfig);
            server.start();

            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                if (br.readLine().equals("stop")) {
                    System.out.println("Exit");
                    System.out.flush();
                    server.stop();
                    break;
                }
            }
        } catch(Exception e) {
            System.out.println(e.toString());
        }

    }
}
