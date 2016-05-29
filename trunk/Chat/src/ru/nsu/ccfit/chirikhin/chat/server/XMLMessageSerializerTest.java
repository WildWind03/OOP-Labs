package ru.nsu.ccfit.chirikhin.chat.server;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import ru.nsu.ccfit.chirikhin.chat.ClientMessage;
import ru.nsu.ccfit.chirikhin.chat.InvalidXMLException;
import ru.nsu.ccfit.chirikhin.chat.LoginMessage;
import ru.nsu.ccfit.chirikhin.chat.XMLMessageSerializer;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.Assert.*;

public class XMLMessageSerializerTest {

    @Before
    public void init() {

    }
    @Test
    public void checkLoginXML() {
        File file = new File("./resources/testLoginCommand.xml");

        System.out.println(file.getAbsolutePath());

        XMLMessageSerializer xmlMessageSerializer = null;
        try {
            xmlMessageSerializer = new XMLMessageSerializer(new FileInputStream(new File("./src/resources/testLoginCommand.xml")));
        } catch (ParserConfigurationException | FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            LoginMessage loginMessage = (LoginMessage) xmlMessageSerializer.serialize();
            Assert.assertTrue(loginMessage.getChatClientName().equals("Windograd"));
            Assert.assertTrue(loginMessage.getUsername().equals("Wind"));
        } catch (IOException | ClassNotFoundException | InvalidXMLException | SAXException e) {
            e.printStackTrace();
        }
    }
}