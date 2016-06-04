package ru.nsu.ccfit.chirikhin.chat.service;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

public class XMLMessageSerializer implements MessageSerializer {
    private static final Logger logger = Logger.getLogger(XMLMessageSerializer.class.getName());
    private final DataInputStream dataInputStream;
    private final DocumentBuilder documentBuilder;
    private final XMLMessageParser xmlMessageParser = new XMLMessageParser();

    public XMLMessageSerializer(InputStream inputStream) throws ParserConfigurationException {
        if (null == inputStream) {
            throw new NullPointerException("Null instead of input stream");
        }

        this.dataInputStream = new DataInputStream(inputStream);

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        documentBuilder = documentBuilderFactory.newDocumentBuilder();
    }


    @Override
    public Message serialize() throws IOException, ClassNotFoundException, SAXException, InvalidXMLException {
        int size = dataInputStream.readInt();

        if (size < 0) {
            throw new IOException("Size can not be less than zero");
        }

        byte[] xmlBytes;
        try {
            xmlBytes = new byte[size];
        } catch (Throwable t) {
            throw new InvalidXMLException("Invalid size!");
        }

        dataInputStream.readFully(xmlBytes);

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(xmlBytes);

        Document document = documentBuilder.parse(byteArrayInputStream);

        return xmlMessageParser.getMessage(document);
    }

    @Override
    public void close() throws IOException {
        dataInputStream.close();
    }
}
