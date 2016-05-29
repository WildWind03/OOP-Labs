package ru.nsu.ccfit.chirikhin.chat;

import org.apache.log4j.Logger;
import org.junit.Test;

public class XMLMessageParserTest {
    private static final Logger logger = Logger.getLogger(XMLMessageParserTest.class.getName());

    @Test
    public void testFromMessageToXml() {
        XMLMessageParser xmlMessageParser = new XMLMessageParser();
        String result = xmlMessageParser.createXMLFromMessage(new CommandLogin("Wild", "Windogram"));
        System.out.println(result);
    }
}
