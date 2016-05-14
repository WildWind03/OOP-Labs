package ru.nsu.ccfit.chirikhin.chat;

import org.xml.sax.SAXException;
import ru.nsu.ccfit.chirikhin.chat.Message;

import java.io.IOException;

public interface MessageSerializer  {
    Message read() throws IOException, ClassNotFoundException, SAXException;
}
