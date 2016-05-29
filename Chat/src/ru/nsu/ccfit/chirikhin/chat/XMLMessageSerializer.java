package ru.nsu.ccfit.chirikhin.chat;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.mapper.SystemAttributeAliasingMapper;
import org.apache.log4j.Logger;
import org.apache.log4j.net.SyslogAppender;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class XMLMessageSerializer implements MessageSerializer {
    private static final Logger logger = Logger.getLogger(XMLMessageSerializer.class.getName());
    private final InputStream inputStream;
    private final DocumentBuilder documentBuilder;
    private final XMLMessageParser xmlMessageParser = new XMLMessageParser();

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
        int size = inputStream.read();
        byte[] xmlBytes = new byte[size];
        int readChar = inputStream.read(xmlBytes, 0, size);

        String str = new String(xmlBytes, StandardCharsets.UTF_8);

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(xmlBytes);

        Document document = documentBuilder.parse(byteArrayInputStream);

        return xmlMessageParser.getMessage(document);
    }

    @Override
    public void close() throws IOException {
        inputStream.close();
    }
}
