package ru.nsu.ccfit.chirikhin.chat.client;

import org.apache.log4j.Logger;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import ru.nsu.ccfit.chirikhin.chat.*;

import java.util.LinkedList;

public class ServerMessageXMLParser {
    private static final Logger logger = Logger.getLogger(ru.nsu.ccfit.chirikhin.chat.server.ClientMessageXMLParser.class.getName());


    public ServerMessage getMessage(Document document) throws InvalidXMLException {
        if (null == document) {
            throw new NullPointerException("Document can not be null");
        }

        NodeList errorList = document.getElementsByTagName("error");

        if (1 == errorList.getLength()) {
            Node node = errorList.item(0);

            if (Node.ELEMENT_NODE != node.getNodeType()) {
                logger.error("Invalid XML");
                throw new InvalidXMLException("Invalid XML");
            }

            Element error = (Element) node;

            NodeList nodeListReason = error.getElementsByTagName("reason");
            if (1 != nodeListReason.getLength()) {
                throw new InvalidXMLException("Invalid XML. Tag 'reason'");
            }

            String reason = nodeListReason.item(0).getTextContent();
            return new ServerErrorAnswer(reason);
        }

        NodeList successList = document.getElementsByTagName("success");
        if (1 == successList.getLength()) {
            Node node = successList.item(0);

            if (Node.ELEMENT_NODE != node.getNodeType()) {
                logger.error("Invalid XML");
                throw new InvalidXMLException("Invalid XML");
            }

            Element success = (Element) node;

            NodeList nodeListSessionId = success.getElementsByTagName("session");
            if (1 == nodeListSessionId.getLength()) {
                String  sessionId = nodeListSessionId.item(0).getTextContent();
                return new ServerSuccessLoginAnswer(Long.parseLong(sessionId));
            }

            NodeList nodeListUsers = success.getElementsByTagName("listusers");
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

                    Element userNodeElement = (Element) node;

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

                return new ServerClientListMessage(clients);
            }

            NodeList nodeList = success.getElementsByTagName("*");
            if (0 == nodeList.getLength()) {
                return new ServerSuccessAnswer();
            }

            throw new InvalidXMLException("There is error while looking for 'session', 'listusers' and no tags");

        }

        NodeList event = document.getElementsByTagName("event");
        if (1 == event.getLength()) {
            Node eventNode = event.item(0);

            if (Node.ELEMENT_NODE != eventNode.getNodeType()) {
                logger.error("Invalid XML");
                throw new InvalidXMLException("Invalid XML");
            }

            Element eventElement = (Element) eventNode;

            String eventName = eventElement.getAttribute("name");

            if (eventName.isEmpty()) {
                throw new InvalidXMLException("There is no attribute 'name' in event tag");
            }

            switch(eventName) {
                case "userlogout" :
                    NodeList nodeListNickname = eventElement.getElementsByTagName("name");
                    if (1 != nodeListNickname.getLength()) {
                        throw new InvalidXMLException("Invalid XML. Tag 'name'");
                    }

                    String nickname = nodeListNickname.item(0).getTextContent();
                    return new ClientLogoutServerMessage(nickname);
                case "userlogin" :
                    NodeList nodeListNicknameLogin = eventElement.getElementsByTagName("name");
                    if (1 != nodeListNicknameLogin.getLength()) {
                        throw new InvalidXMLException("Invalid XML. Tag 'name'");
                    }

                    String nicknameLogin = nodeListNicknameLogin.item(0).getTextContent();
                    return new NewClientServerMessage(nicknameLogin);
                case "message" :

                    NodeList nodeListMessage = eventElement.getElementsByTagName("message");
                    if (1 != nodeListMessage.getLength()) {
                        throw new InvalidXMLException("Invalid XML. Tag 'message'");
                    }

                    String message = nodeListMessage.item(0).getTextContent();

                    NodeList nodeListType = eventElement.getElementsByTagName("name");
                    if (1 != nodeListType.getLength()) {
                        throw new InvalidXMLException("Invalid XML. Tag 'name'");
                    }

                    String clientType = nodeListType.item(0).getTextContent();

                    return new ServerTextMessage(clientType, message);
            }

            NodeList eventElementNodeListLogout = eventElement.getElementsByTagName("userlogout");

            if (1 == eventElementNodeListLogout.getLength()) {

            }

            NodeList eventElementNodeListLogin = eventElement.getElementsByTagName("userlogin");

            NodeList eventElementNodeListMessage = eventElement.getElementsByTagName("message");

            throw new InvalidXMLException("Invalid xml. In 'event'");

        }

        throw new InvalidXMLException("There is not error, event or success tag in file");
    }
}
