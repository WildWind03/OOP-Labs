package ru.nsu.ccfit.chirikhin.chat.service;

import org.apache.log4j.Logger;

import java.io.Serializable;

public class ClientDescriptor implements Serializable{
    private static final Logger logger = Logger.getLogger(ClientDescriptor.class.getName());

    private final String name;
    private final String type;

    public ClientDescriptor(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }
}
