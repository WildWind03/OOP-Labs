package ru.nsu.ccfit.chirikhin.chat.serviceTest;

import org.apache.log4j.Logger;
import org.junit.Test;
import ru.nsu.ccfit.chirikhin.chat.service.CommandLogin;
import ru.nsu.ccfit.chirikhin.chat.service.XMLMessageParser;

public class XMLMessageParserTest {
    private static final Logger logger = Logger.getLogger(XMLMessageParserTest.class.getName());

    @Test
    public void testFromMessageToXml() {
       XMLMessageParser xmlMessageParser = new XMLMessageParser();
        String result = xmlMessageParser.createXMLFromMessage(new CommandLogin("Wild", "Windogram"));
    }
}
