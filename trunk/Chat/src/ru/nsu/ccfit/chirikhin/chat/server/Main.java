package ru.nsu.ccfit.chirikhin.chat.server;

import ru.nsu.ccfit.chirikhin.chat.ConfigParser;
import ru.nsu.ccfit.chirikhin.chat.ConsoleParser;
import ru.nsu.ccfit.chirikhin.chat.LoggerController;
import ru.nsu.ccfit.chirikhin.chat.ProtocolName;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.LinkedList;

public class Main {
    public static void main(String[] args) throws ServerConfigException, IOException {
        try {
            LinkedList<String> parameters = new LinkedList<>();
            Collections.addAll(parameters, args);

            ConsoleParser consoleParser = new ConsoleParser(parameters);
            ConfigParser configParser = new ConfigParser(consoleParser.getPathToFile());

            if (!configParser.isLog()) {
                LoggerController.switchOffLogger();
            }

            ServerConfig serverConfig = new ServerConfig();
            serverConfig.addNewSocketListenerDescription(new SocketListenerDescriptor(3000, ProtocolName.SERIALIZE));
            serverConfig.addNewSocketListenerDescription(new SocketListenerDescriptor(3001, ProtocolName.XML));
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
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}
