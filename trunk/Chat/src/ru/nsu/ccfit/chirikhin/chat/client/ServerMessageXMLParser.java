package ru.nsu.ccfit.chirikhin.chat.client;

import org.apache.log4j.Logger;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import ru.nsu.ccfit.chirikhin.chat.*;

public class ServerMessageXMLParser {
    private static final Logger logger = Logger.getLogger(ru.nsu.ccfit.chirikhin.chat.server.ClientMessageXMLParser.class.getName());
    private final static String ERRORSTR = "error";
    private final static String SUCCESS_STR = "success";
    private final static String EVENT_STR = "event";
    private final static String LOGOUT_STR = "logout";


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
                }
            }

            NodeList nodeList = success.getElementsByTagName("*");
            if (0 == nodeList.getLength()) {
                return new ServerSuccessAnswer();
            }

            throw new InvalidXMLException("There is error while looking for 'session', 'listusers' and no tags")

        }

        NodeList event = document.getElementsByTagName("event");
        if (1 == event.getLength()) {

        }

        throw new InvalidXMLException("There is not error, event or success tag in file");

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
