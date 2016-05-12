package ru.nsu.ccfit.chirikhin.chat.server;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;

public class XMLSerializer implements MessageSerializer {
    private static final Logger logger = Logger.getLogger(XMLSerializer.class.getName());
    private final InputStream inputStream;
    private final DocumentBuilder documentBuilder;

    public XMLSerializer(InputStream inputStream) throws ParserConfigurationException {
        if (null  == inputStream) {
            throw new NullPointerException("Null instead of input stream");
        }
        this.inputStream = inputStream;

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        documentBuilder = documentBuilderFactory.newDocumentBuilder();
    }

    @Override
    public Message read() throws IOException, SAXException {
        Document document = documentBuilder.parse(inputStream);
        Message message = null;

        return message;
    }
}
