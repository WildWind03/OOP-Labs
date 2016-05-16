package ru.nsu.ccfit.chirikhin.chat;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLParser {
    private static final Logger logger = Logger.getLogger(XMLParser.class.getName());
    private final static String LOGIN_STR = "login";
    private final static String LIST_STR = "list";
    private final static String MESSAGE_STR = "message";
    private final static String LOGOUT_STR = "logout";
    private final static String USER_LOGIN_STR = "userlogin";
    private final static String USER_LOGOUT_STR = "userlogout";


    public ClientMessage getMessage(Document document) throws InvalidXMLException {
        if (null == document) {
            throw new NullPointerException("Document can not be null");
        }

        String commandName;
        NodeList cmdParams = document.getElementsByTagName("command name");

        if (cmdParams.getLength() != 1) {
            logger.error("Invalid XML");
            throw new InvalidXMLException("Invalid XML. Too many fields with tag 'command name'");
        }

        Node node = cmdParams.item(0);
        if (Node.ELEMENT_NODE != node.getNodeType()) {
            logger.error("Invalid XML");
            throw new InvalidXMLException("Invalid XML");
        }

        Element cmd = (Element) node;
        String cmdString = cmd.getAttribute("command name");

        switch (cmdString) {
            case "login" :
                String name = cmd.getElementsByTagName("name").item(0).getTextContent();
                String type = cmd.getElementsByTagName("type").item(0).getTextContent();
                break;
            case "list" :

                break;
            case "message" :

                break;
            case "logout" :

                break;
            case "userlogin" :

                break;
            case "userlogout" :

                break;
        }

        return null;
    }
}
