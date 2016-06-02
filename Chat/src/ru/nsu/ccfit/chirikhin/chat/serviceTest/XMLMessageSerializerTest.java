package ru.nsu.ccfit.chirikhin.chat.serviceTest;

import org.junit.Assert;
import org.junit.Test;
import org.xml.sax.SAXException;
import ru.nsu.ccfit.chirikhin.chat.service.*;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;

public class XMLMessageSerializerTest {

    @Test
    public void checkLoginXML() {
        XMLMessageSerializer xmlMessageSerializer = null;

        try {
            xmlMessageSerializer = new XMLMessageSerializer(new FileInputStream(new File("./src/resources/testLoginCommand.xml")));
        } catch (ParserConfigurationException | FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            CommandLogin commandLogin = (CommandLogin) xmlMessageSerializer.serialize();
            Assert.assertTrue(commandLogin.getType().equals("Windograd"));
            Assert.assertTrue(commandLogin.getName().equals("Wind"));
        } catch (IOException | ClassNotFoundException | InvalidXMLException | SAXException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void checkTextXML() {
        XMLMessageSerializer xmlMessageSerializer = null;

        try {
            xmlMessageSerializer = new XMLMessageSerializer(new FileInputStream(new File("./src/resources/testTextEvent.xml")));
        } catch (ParserConfigurationException | FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            EventText eventText = (EventText) xmlMessageSerializer.serialize();
            Assert.assertTrue(eventText.getName().equals("Windograd"));
            Assert.assertTrue(eventText.getMessage().equals("Hi"));
        } catch (IOException | ClassNotFoundException | InvalidXMLException | SAXException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void checkListXML() {
        XMLMessageSerializer xmlMessageSerializer = null;

        try {
            xmlMessageSerializer = new XMLMessageSerializer(new FileInputStream(new File("./src/resources/testListAnswer.xml")));
        } catch (ParserConfigurationException | FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            AnswerClientList answerClientList = (AnswerClientList) xmlMessageSerializer.serialize();
            LinkedList<ClientDescriptor> clients = answerClientList.getListusers();

            ClientDescriptor clientDescriptor1 = clients.get(0);
            Assert.assertTrue(clientDescriptor1.getType().equals("Windogram"));
            Assert.assertTrue(clientDescriptor1.getName().equals("Wind"));

            ClientDescriptor clientDescriptor2 = clients.get(1);
            Assert.assertTrue(clientDescriptor2.getType().equals("NONAME"));
            Assert.assertTrue(clientDescriptor2.getName().equals("14203"));
        } catch (IOException | ClassNotFoundException | InvalidXMLException | SAXException e) {
            e.printStackTrace();
        }
    }
}