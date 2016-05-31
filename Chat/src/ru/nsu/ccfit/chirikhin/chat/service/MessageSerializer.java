package ru.nsu.ccfit.chirikhin.chat.service;

import org.xml.sax.SAXException;

import java.io.Closeable;
import java.io.IOException;

public interface MessageSerializer extends Closeable {
    Message serialize() throws IOException, ClassNotFoundException, SAXException, InvalidXMLException;
}
