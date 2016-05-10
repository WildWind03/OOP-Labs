package ru.nsu.ccfit.chirikhin.chat.server;

import org.xml.sax.SAXException;

import java.io.IOException;

public interface MessageSerializer  {
    Message read() throws IOException, ClassNotFoundException, SAXException;
}
