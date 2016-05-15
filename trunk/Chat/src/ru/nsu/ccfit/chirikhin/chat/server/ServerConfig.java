package ru.nsu.ccfit.chirikhin.chat.server;

import org.apache.log4j.Logger;

import java.util.LinkedList;
import java.util.stream.Stream;

public class ServerConfig {
    private static final Logger logger = Logger.getLogger(ServerConfig.class.getName());

    private final LinkedList<SocketListenerDescriptor> socketListenerDescriptors = new LinkedList<>();

    public void addNewSocketListenerDescription(SocketListenerDescriptor socketListenerDescriptor) throws ServerConfigException {
        if (null == socketListenerDescriptor) {
            throw new NullPointerException("Null instead of socket listener descriptor");
        }

        if (!socketListenerDescriptors.add(socketListenerDescriptor)) {
            logger.error("Can't add new listener");
            throw new ServerConfigException("Can't add new listener");
        }
    }

    public int getCountOfPorts() {
        return socketListenerDescriptors.size();
    }

    public Stream<SocketListenerDescriptor> stream() {
        return socketListenerDescriptors.stream();
    }
}
