package ru.nsu.ccfit.chirikhin.chat;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import ru.nsu.ccfit.chirikhin.chat.server.ClientMessageXMLParser;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;

public class XMLMessageSerializer implements MessageSerializer {
    private static final Logger logger = Logger.getLogger(XMLMessageSerializer.class.getName());
    private final InputStream inputStream;
    private final DocumentBuilder documentBuilder;

    public XMLMessageSerializer(InputStream inputStream) throws ParserConfigurationException {
        if (null == inputStream) {
            throw new NullPointerException("Null instead of input stream");
        }
        this.inputStream = inputStream;

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        documentBuilder = documentBuilderFactory.newDocumentBuilder();
    }


    @Override
    public Message serialize() throws IOException, ClassNotFoundException, SAXException, InvalidXMLException {
        Document document = documentBuilder.parse(inputStream);
        ClientMessageXMLParser clientMessageXmlParser = new ClientMessageXMLParser();
        ClientMessage clientMessage = clientMessageXmlParser.getMessage(document);

        return clientMessage;
    }

    @Override
    public void close() throws IOException {

    }
}
