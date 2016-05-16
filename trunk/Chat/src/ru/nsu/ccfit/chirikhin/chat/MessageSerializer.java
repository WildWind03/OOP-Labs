package ru.nsu.ccfit.chirikhin.chat;

import org.xml.sax.SAXException;

import java.io.IOException;

public interface MessageSerializer  {
    ClientMessage read() throws IOException, ClassNotFoundException, SAXException;
    void stop();
}
