package ru.nsu.ccfit.chirikhin.chat;

import com.thoughtworks.xstream.XStream;
import org.apache.log4j.Logger;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.LinkedList;

public class XMLMessageParser {
    private static final Logger logger = Logger.getLogger(XMLMessageParser.class.getName());

    private final static String LOGIN_STR = "login";
    private final static String LIST_STR = "list";
    private final static String MESSAGE_STR = "message";
    private final static String LOGOUT_STR = "logout";


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

            case "command" :

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

                        clientMessage = new CommandLogin(name, type);
                        break;
                    case LIST_STR:
                        NodeList nodeListSessionId = cmd.getElementsByTagName("session");
                        if (1 != nodeListSessionId.getLength()) {
                            throw new InvalidXMLException("Invalid XML. Tag 'session'");
                        }

                        String sessionId = nodeListSessionId.item(0).getTextContent();
                        clientMessage = new CommandClientList(Long.parseLong(sessionId));
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
                        clientMessage = new CommandText(message, Long.parseLong(session));
                        break;
                    case LOGOUT_STR:
                        NodeList nodeListSessionIdLogout = cmd.getElementsByTagName("session");
                        if (1 != nodeListSessionIdLogout.getLength()) {
                            throw new InvalidXMLException("Invalid XML. Tag 'session'");
                        }

                        String sessionIdLogout = nodeListSessionIdLogout.item(0).getTextContent();
                        clientMessage = new CommandClientList(Long.parseLong(sessionIdLogout));
                        break;
                }

                return clientMessage;

            case "error" :
                NodeList nodeListReason = nodeElement.getElementsByTagName("reason");
                if (1 != nodeListReason.getLength()) {
                    throw new InvalidXMLException("Invalid XML. Tag 'reason'");
                }

                String reason = nodeListReason.item(0).getTextContent();
                return new AnswerError(reason);

            case "success" :
                NodeList nodeListSessionId = nodeElement.getElementsByTagName("session");
                if (1 == nodeListSessionId.getLength()) {
                    String  sessionId = nodeListSessionId.item(0).getTextContent();
                    return new AnswerSuccessLogin(Long.parseLong(sessionId));
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

                    NodeList userList = elementUsers.getElementsByTagName("user");

                    for (int i = 0; i < userList.getLength(); ++i) {

                        Node userNode = userList.item(i);

                        if (Node.ELEMENT_NODE != userNode.getNodeType()) {
                            logger.error("Invalid XML");
                            throw new InvalidXMLException("Invalid XML");
                        }

                        Element userNodeElement = (Element) userNode;

                        NodeList userNodeListName = userNodeElement.getElementsByTagName("name");
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

            case "event" :
                String eventName = nodeElement.getAttribute("name");

                if (eventName.isEmpty()) {
                    throw new InvalidXMLException("There is no attribute 'name' in event tag");
                }

                switch(eventName) {
                    case "userlogout":
                        NodeList nodeListNickname = nodeElement.getElementsByTagName("name");
                        if (1 != nodeListNickname.getLength()) {
                            throw new InvalidXMLException("Invalid XML. Tag 'name'");
                        }

                        String nickname = nodeListNickname.item(0).getTextContent();
                        return new EventLogout(nickname);
                    case "userlogin":
                        NodeList nodeListNicknameLogin = nodeElement.getElementsByTagName("name");
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
        XStream xStream = new XStream();
        xStream.alias("command", CommandLogin.class);
        xStream.useAttributeFor(CommandLogin.class, "messageType");
        xStream.aliasField("name", CommandLogin.class, "messageType");

        xStream.alias("command", CommandClientList.class);
        xStream.useAttributeFor(CommandClientList.class, "messageType");
        xStream.aliasField("name", CommandClientList.class, "messageType");

        xStream.alias("command", CommandLogout.class);
        xStream.useAttributeFor(CommandLogout.class, "messageType");
        xStream.aliasField("name", CommandLogout.class, "messageType");

        xStream.alias("command", CommandText.class);
        xStream.useAttributeFor(CommandText.class, "messageType");
        xStream.aliasField("name", CommandText.class, "messageType");

        xStream.alias("event", EventNewClient.class);
        xStream.useAttributeFor(EventNewClient.class, "messageType");
        xStream.aliasField("name", EventNewClient.class, "messageType");

        xStream.alias("event", EventText.class);
        xStream.useAttributeFor(EventText.class, "messageType");
        xStream.aliasField("name", EventText.class, "messageType");

        xStream.alias("event", EventLogout.class);
        xStream.useAttributeFor(EventLogout.class, "messageType");
        xStream.aliasField("name", EventLogout.class, "messageType");

        xStream.alias("error", AnswerError.class);

        xStream.alias("success", AnswerSuccess.class);
        xStream.alias("success", AnswerSuccessLogin.class);
        xStream.alias("success", AnswerClientList.class);

        xStream.alias("user", ClientDescriptor.class);

        String str = xStream.toXML(message);
        return str;

    }
}
