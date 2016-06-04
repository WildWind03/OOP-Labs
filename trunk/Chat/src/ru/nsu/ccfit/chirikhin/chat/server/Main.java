package ru.nsu.ccfit.chirikhin.chat.server;

import org.apache.log4j.Logger;
import ru.nsu.ccfit.chirikhin.chat.service.ConfigParser;
import ru.nsu.ccfit.chirikhin.chat.service.ConsoleParser;
import ru.nsu.ccfit.chirikhin.chat.service.LoggerController;
import ru.nsu.ccfit.chirikhin.chat.service.ProtocolName;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.LinkedList;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());

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
            serverConfig.addNewSocketListenerDescription(new SocketListenerDescriptor(8000, ProtocolName.SERIALIZE));
            serverConfig.addNewSocketListenerDescription(new SocketListenerDescriptor(9000, ProtocolName.XML));
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
            logger.error(e.getMessage());
            System.out.println(e.toString());
        } catch (Throwable t) {
            logger.error("Unknown exception");
        }
    }
}
