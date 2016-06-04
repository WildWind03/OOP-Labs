package ru.nsu.ccfit.chirikhin.chat.service;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import com.thoughtworks.xstream.io.xml.StaxWriter;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.util.LinkedList;

public class XMLMessageParser {
    private static final Logger logger = Logger.getLogger(XMLMessageParser.class.getName());

    private final static String LOGIN_STR = "login";
    private final static String LIST_STR = "list";
    private final static String MESSAGE_STR = "message";
    private final static String LOGOUT_STR = "logout";

    private final static String COMMAND_STR = "command";
    private final static String EVENT_STR = "event";
    private final static String SUCCESS_STR = "success";
    private final static String ERROR_STR = "error";
    private final static String USER_STR = "user";

    private final static String MESSAGE_TYPE_FIELD_STR = "messageType";
    private final static String ALIAS_FOR_MESSAGE_TYPE_FIELD = "name";

    private final XStream xStream;

    public XMLMessageParser() {
        StaxDriver driver = new StaxDriver() {
            @Override
            public StaxWriter createStaxWriter(XMLStreamWriter out) throws XMLStreamException {
                return super.createStaxWriter(out, false);
            }
        };

        xStream = new XStream(driver);

        xStream.alias(COMMAND_STR, CommandLogin.class);
        xStream.useAttributeFor(CommandLogin.class, MESSAGE_TYPE_FIELD_STR);
        xStream.aliasField(ALIAS_FOR_MESSAGE_TYPE_FIELD, CommandLogin.class, MESSAGE_TYPE_FIELD_STR);

        xStream.alias(COMMAND_STR, CommandClientList.class);
        xStream.useAttributeFor(CommandClientList.class, MESSAGE_TYPE_FIELD_STR);
        xStream.aliasField(ALIAS_FOR_MESSAGE_TYPE_FIELD, CommandClientList.class, MESSAGE_TYPE_FIELD_STR);

        xStream.alias(COMMAND_STR, CommandLogout.class);
        xStream.useAttributeFor(CommandLogout.class, MESSAGE_TYPE_FIELD_STR);
        xStream.aliasField(ALIAS_FOR_MESSAGE_TYPE_FIELD, CommandLogout.class, MESSAGE_TYPE_FIELD_STR);

        xStream.alias(COMMAND_STR, CommandText.class);
        xStream.useAttributeFor(CommandText.class, MESSAGE_TYPE_FIELD_STR);
        xStream.aliasField(ALIAS_FOR_MESSAGE_TYPE_FIELD, CommandText.class, MESSAGE_TYPE_FIELD_STR);

        xStream.alias(EVENT_STR, EventNewClient.class);
        xStream.useAttributeFor(EventNewClient.class, MESSAGE_TYPE_FIELD_STR);
        xStream.aliasField(ALIAS_FOR_MESSAGE_TYPE_FIELD, EventNewClient.class, MESSAGE_TYPE_FIELD_STR);

        xStream.alias(EVENT_STR, EventText.class);
        xStream.useAttributeFor(EventText.class, MESSAGE_TYPE_FIELD_STR);
        xStream.aliasField(ALIAS_FOR_MESSAGE_TYPE_FIELD, EventText.class, MESSAGE_TYPE_FIELD_STR);

        xStream.alias(EVENT_STR, EventLogout.class);
        xStream.useAttributeFor(EventLogout.class, MESSAGE_TYPE_FIELD_STR);
        xStream.aliasField(ALIAS_FOR_MESSAGE_TYPE_FIELD, EventLogout.class, MESSAGE_TYPE_FIELD_STR);

        xStream.alias(ERROR_STR, AnswerError.class);

        xStream.alias(SUCCESS_STR, AnswerSuccess.class);
        xStream.alias(SUCCESS_STR, AnswerSuccessLogin.class);
        xStream.alias(SUCCESS_STR, AnswerClientList.class);

        xStream.alias(USER_STR, ClientDescriptor.class);
    }

    public Message getMessage(Document document) throws InvalidXMLException {
        if (null == document) {
            throw new NullPointerException("Document can not be null");
        }

        NodeList nodeList = document.getElementsByTagName("*");

        if (nodeList.getLength() < 1) {
            throw new InvalidXMLException("There is no elements in xml");
        }

        Node node = nodeList.item(0);

        if (Node.ELEMENT_NODE != node.getNodeType()) {
            logger.error("Invalid XML");
            throw new InvalidXMLException("Invalid XML");
        }

        Element nodeElement = (Element) node;

        switch(nodeElement.getTagName()) {

            case COMMAND_STR :

                Element cmd = (Element) node;
                String cmdString = cmd.getAttribute(ALIAS_FOR_MESSAGE_TYPE_FIELD);

                if (cmdString.isEmpty()) {
                    throw new InvalidXMLException("There is not attribute 'name' in XML");
                }

                ClientMessage clientMessage = null;

                switch (cmdString) {
                    case LOGIN_STR:
                        NodeList nodeListName = cmd.getElementsByTagName(ALIAS_FOR_MESSAGE_TYPE_FIELD);
                        if (1 != nodeListName.getLength()) {
                            throw new InvalidXMLException("Invalid XML. Tag 'name'");
                        }

                        NodeList nodeListType = cmd.getElementsByTagName("type");
                        if (1 != nodeListType.getLength()) {
                            throw new InvalidXMLException("Invalid XML. Tag 'type'");
                        }

                        String name = nodeListName.item(0).getTextContent();
                        String type = nodeListType.item(0).getTextContent();

                        clientMessage = new CommandLogin(name, type);
                        break;
                    case LIST_STR:
                        NodeList nodeListSessionId = cmd.getElementsByTagName("session");
                        if (1 != nodeListSessionId.getLength()) {
                            throw new InvalidXMLException("Invalid XML. Tag 'session'");
                        }

                        String sessionId = nodeListSessionId.item(0).getTextContent();
                        clientMessage = new CommandClientList(sessionId);
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
                        clientMessage = new CommandText(message, session);
                        break;
                    case LOGOUT_STR:
                        NodeList nodeListSessionIdLogout = cmd.getElementsByTagName("session");
                        if (1 != nodeListSessionIdLogout.getLength()) {
                            throw new InvalidXMLException("Invalid XML. Tag 'session'");
                        }

                        String sessionIdLogout = nodeListSessionIdLogout.item(0).getTextContent();
                        clientMessage = new CommandLogout(sessionIdLogout);
                        break;
                }

                return clientMessage;

            case ERROR_STR :
                NodeList nodeListReason = nodeElement.getElementsByTagName("reason");
                if (1 != nodeListReason.getLength()) {
                    throw new InvalidXMLException("Invalid XML. Tag 'reason'");
                }

                String reason = nodeListReason.item(0).getTextContent();
                return new AnswerError(reason);

            case SUCCESS_STR :
                NodeList nodeListSessionId = nodeElement.getElementsByTagName("session");
                if (1 == nodeListSessionId.getLength()) {
                    String  sessionId = nodeListSessionId.item(0).getTextContent();
                    return new AnswerSuccessLogin(sessionId);
                }


                NodeList nodeListUsers = nodeElement.getElementsByTagName("listusers");
                if (1 == nodeListUsers.getLength()) {
                    LinkedList<ClientDescriptor> clients = new LinkedList<>();

                    Node nodeUsers = nodeListUsers.item(0);

                    if (Node.ELEMENT_NODE != nodeUsers.getNodeType()) {
                        logger.error("Invalid XML");
                        throw new InvalidXMLException("Invalid XML");
                    }

                    Element elementUsers = (Element) node;

                    NodeList userList = elementUsers.getElementsByTagName(USER_STR);

                    for (int i = 0; i < userList.getLength(); ++i) {

                        Node userNode = userList.item(i);

                        if (Node.ELEMENT_NODE != userNode.getNodeType()) {
                            logger.error("Invalid XML");
                            throw new InvalidXMLException("Invalid XML. Not Element Node");
                        }

                        Element userNodeElement = (Element) userNode;

                        NodeList userNodeListName = userNodeElement.getElementsByTagName(ALIAS_FOR_MESSAGE_TYPE_FIELD);
                        if (1 != userNodeListName.getLength()) {
                            throw new InvalidXMLException("Invalid XML. Tag 'name'");
                        }

                        NodeList userNodeListType = userNodeElement.getElementsByTagName("type");
                        if (1 != userNodeListType.getLength()) {
                            throw new InvalidXMLException("Invalid XML. Tag 'type'");
                        }

                        String userStr = userNodeListName.item(0).getTextContent();
                        String typeStr = userNodeListType.item(0).getTextContent();
                        clients.add(new ClientDescriptor(userStr, typeStr));
                    }

                    return new AnswerClientList(clients);
                }

                NodeList nodeListEmpty = nodeElement.getElementsByTagName("*");
                if (0 == nodeListEmpty.getLength()) {
                    return new AnswerSuccess();
                }

                throw new InvalidXMLException("There is error while looking for 'session', 'listusers' and no tags");

            case EVENT_STR :
                String eventName = nodeElement.getAttribute(ALIAS_FOR_MESSAGE_TYPE_FIELD);

                if (eventName.isEmpty()) {
                    throw new InvalidXMLException("There is no attribute 'name' in event tag");
                }

                switch(eventName) {
                    case "userlogout":
                        NodeList nodeListNickname = nodeElement.getElementsByTagName(ALIAS_FOR_MESSAGE_TYPE_FIELD);
                        if (1 != nodeListNickname.getLength()) {
                            throw new InvalidXMLException("Invalid XML. Tag 'name'");
                        }

                        String nickname = nodeListNickname.item(0).getTextContent();
                        return new EventLogout(nickname);
                    case "userlogin":
                        NodeList nodeListNicknameLogin = nodeElement.getElementsByTagName(ALIAS_FOR_MESSAGE_TYPE_FIELD);
                        if (1 != nodeListNicknameLogin.getLength()) {
                            throw new InvalidXMLException("Invalid XML. Tag 'name'");
                        }

                        String nicknameLogin = nodeListNicknameLogin.item(0).getTextContent();
                        return new EventNewClient(nicknameLogin);
                    case "message":

                        NodeList nodeListMessage = nodeElement.getElementsByTagName("message");
                        if (1 != nodeListMessage.getLength()) {
                            throw new InvalidXMLException("Invalid XML. Tag 'message'");
                        }

                        String message = nodeListMessage.item(0).getTextContent();

                        NodeList nodeListType = nodeElement.getElementsByTagName("name");
                        if (1 != nodeListType.getLength()) {
                            throw new InvalidXMLException("Invalid XML. Tag 'name'");
                        }

                        String clientType = nodeListType.item(0).getTextContent();

                        return new EventText(clientType, message);
                    default:
                        throw new InvalidXMLException("Tag 'event'");
                }
        }

        throw new InvalidXMLException("There is not possible tags in xml");
    }

    public String createXMLFromMessage(Message message) {
        return xStream.toXML(message);

    }
}
