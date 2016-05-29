package ru.nsu.ccfit.chirikhin.chat;

import org.apache.log4j.Logger;

import java.io.Serializable;

public class ClientDescriptor implements Serializable{
    private static final Logger logger = Logger.getLogger(ClientDescriptor.class.getName());

    private final String nickname;
    private final String clientType;

    public ClientDescriptor(String nickname, String clientType) {
        this.nickname = nickname;
        this.clientType = clientType;
    }

    public String getNickname() {
        return nickname;
    }

    public String getClientType() {
        return clientType;
    }
}
