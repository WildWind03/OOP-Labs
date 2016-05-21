package ru.nsu.ccfit.chirikhin.chat;

import org.xml.sax.SAXException;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface MessageSerializer extends Closeable {
    Message serialize() throws IOException, ClassNotFoundException, SAXException, InvalidXMLException;
}
