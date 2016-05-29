package ru.nsu.ccfit.chirikhin.chat;

import org.apache.log4j.Logger;
import org.junit.Test;

import java.util.LinkedList;

public class XMLMessageParserTest {
    private static final Logger logger = Logger.getLogger(XMLMessageParserTest.class.getName());

    @Test
    public void testFromMessageToXml() {
        XMLMessageParser xmlMessageParser = new XMLMessageParser();
        LinkedList<ClientDescriptor> clientDescriptors = new LinkedList<>();
        clientDescriptors.add(new ClientDescriptor("Wind", "Windogram"));
        clientDescriptors.add(new ClientDescriptor("Me", "VK"));
        String result = xmlMessageParser.createXMLFromMessage(new AnswerClientList(clientDescriptors));
        System.out.println(result);
    }
}
