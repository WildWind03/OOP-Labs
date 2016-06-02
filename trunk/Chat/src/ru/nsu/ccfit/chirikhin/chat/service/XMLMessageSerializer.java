package ru.nsu.ccfit.chirikhin.chat.service;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class XMLMessageSerializer implements MessageSerializer {
    private static final Logger logger = Logger.getLogger(XMLMessageSerializer.class.getName());
    //private final InputStream inputStream;
    private final DataInputStream dataInputStream;
    private final DocumentBuilder documentBuilder;
    private final XMLMessageParser xmlMessageParser = new XMLMessageParser();

    public XMLMessageSerializer(InputStream inputStream) throws ParserConfigurationException {
        if (null == inputStream) {
            throw new NullPointerException("Null instead of input stream");
        }

        this.dataInputStream = new DataInputStream(inputStream);
        //this.inputStream = inputStream;

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        documentBuilder = documentBuilderFactory.newDocumentBuilder();
    }


    @Override
    public Message serialize() throws IOException, ClassNotFoundException, SAXException, InvalidXMLException {
        //int size = inputStream.read();
        int size = dataInputStream.readInt();

        System.out.println(size);
        System.out.flush();
        if (size < 0) {
            throw new IOException("Size can not be less than zero");
        }
        byte[] xmlBytes = new byte[size];
        //int readChar = inputStream.read(xmlBytes, 0, size);
        dataInputStream.readFully(xmlBytes);

        String str1 = new String(xmlBytes);
        System.out.println(str1);
        System.out.flush();

       // if (readChar != size) {
        //    throw new IOException("Error while reading");
        //}

        String str = new String(xmlBytes, StandardCharsets.UTF_8);

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(xmlBytes);

        Document document = documentBuilder.parse(byteArrayInputStream);

        return xmlMessageParser.getMessage(document);
    }

    @Override
    public void close() throws IOException {
        //inputStream.close();
        dataInputStream.close();
    }
}
