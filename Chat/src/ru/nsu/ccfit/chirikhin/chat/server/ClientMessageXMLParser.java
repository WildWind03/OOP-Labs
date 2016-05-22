package ru.nsu.ccfit.chirikhin.chat.server;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import ru.nsu.ccfit.chirikhin.chat.*;

public class ClientMessageXMLParser {
    private static final Logger logger = Logger.getLogger(ClientMessageXMLParser.class.getName());
    private final static String LOGIN_STR = "login";
    private final static String LIST_STR = "list";
    private final static String MESSAGE_STR = "message";
    private final static String LOGOUT_STR = "logout";


    public ClientMessage getMessage(Document document) throws InvalidXMLException {
        if (null == document) {
            throw new NullPointerException("Document can not be null");
        }

        NodeList cmdParams = document.getElementsByTagName("command");

        if (cmdParams.getLength() != 1) {
            logger.error("Invalid XML");
            throw new InvalidXMLException("Invalid XML. Not one field with tag 'command");
        }

        Node node = cmdParams.item(0);

        if (Node.ELEMENT_NODE != node.getNodeType()) {
            logger.error("Invalid XML");
            throw new InvalidXMLException("Invalid XML");
        }

        Element cmd = (Element) node;
        String cmdString = cmd.getAttribute("name");

        if (cmdString.isEmpty()) {
            throw new InvalidXMLException("There is not attribute 'name' in XML");
        }

        ClientMessage clientMessage = null;

        switch (cmdString) {
            case LOGIN_STR:
                NodeList nodeListName = cmd.getElementsByTagName("name");
                if (1 != nodeListName.getLength()) {
                    throw new InvalidXMLException("Invalid XML. Tag 'name'");
                }

                NodeList nodeListType = cmd.getElementsByTagName("type");
                if (1 != nodeListType.getLength()) {
                    throw new InvalidXMLException("Invalid XML. Tag 'type'");
                }

                String name = nodeListName.item(0).getTextContent();
                String type = nodeListType.item(0).getTextContent();

                clientMessage = new LoginMessage(name, type);
                break;
            case LIST_STR:
                NodeList nodeListSessionId = cmd.getElementsByTagName("session");
                if (1 != nodeListSessionId.getLength()) {
                    throw new InvalidXMLException("Invalid XML. Tag 'session'");
                }

                String sessionId = nodeListSessionId.item(0).getTextContent();
                clientMessage = new ClientListMessage(Long.parseLong(sessionId));
                break;
            case MESSAGE_STR:
                NodeList nodeListMessage = cmd.getElementsByTagName("message");
                if (1 != nodeListMessage.getLength()) {
                    throw new InvalidXMLException("Invalid XML. Tag 'message'");
                }

                NodeList nodeListSession = cmd.getElementsByTagName("session");
                if (1 != nodeListSession.getLength()) {
                    throw new InvalidXMLException("Invalid XML. Tag 'session'");
                }

                String message = nodeListMessage.item(0).getTextContent();
                String session = nodeListSession.item(0).getTextContent();
                clientMessage = new ClientTextMessage(message, Long.parseLong(session));
                break;
            case LOGOUT_STR:
                NodeList nodeListSessionIdLogout = cmd.getElementsByTagName("session");
                if (1 != nodeListSessionIdLogout.getLength()) {
                    throw new InvalidXMLException("Invalid XML. Tag 'session'");
                }

                String sessionIdLogout = nodeListSessionIdLogout.item(0).getTextContent();
                clientMessage = new ClientListMessage(Long.parseLong(sessionIdLogout));
                break;
        }

        return clientMessage;
    }
}
