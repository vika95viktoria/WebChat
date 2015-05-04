package org.vika.chat.storage.xml; /**
 * Created by Администратор on 26.04.2015.

 */


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;


import org.apache.log4j.Logger;
import org.vika.chat.model.Message;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import java.text.SimpleDateFormat;
import java.util.Date;
import static org.vika.chat.util.MessageUtil.DATE;
import org.xml.sax.SAXException;
public final class XMLHistoryUtil {

   private static final String STORAGE_LOCATION = "file:///D:\\Мои Документы\\сервер чата\\WebChatApplication\\src\\main\\resources\\history.xml";
    private static final String filePath = "D:\\Мои Документы\\сервер чата\\WebChatApplication\\src\\main\\resources\\history.xml";
    private static final String MESSAGES = "messages";
    private static final String MESSAGE= "message";
    private static final String ID = "id";
    private static final String DESCRIPTION = "description";
    private static final String USER = "user";
    private static SimpleDateFormat timeFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");


    private XMLHistoryUtil() {
    }

    public static synchronized void createStorage() throws ParserConfigurationException, TransformerException {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

        Document doc = docBuilder.newDocument();
        Element rootElement = doc.createElement(MESSAGES);
        doc.appendChild(rootElement);

        Transformer transformer = getTransformer();

        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(filePath));
        transformer.transform(source, result);
    }
    private static Logger logger = Logger.getLogger(XMLHistoryUtil.class.getName());
    public static synchronized void addData(Message message) throws ParserConfigurationException, SAXException, IOException, TransformerException {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(STORAGE_LOCATION);
        document.getDocumentElement().normalize();
        Element root = document.getDocumentElement();


        Element messageElement = document.createElement("message");

        root.appendChild(messageElement);

        messageElement.setAttribute(ID, message.getId());

        Element description = document.createElement("description");
        description.appendChild(document.createTextNode(message.getDescription()));
        messageElement.appendChild(description);

        Element user = document.createElement("user");
        user.appendChild(document.createTextNode(message.getUser()));
        messageElement.appendChild(user);

        Element date = document.createElement(DATE);
        date.appendChild(document.createTextNode(message.setDate()));
        messageElement.appendChild(date);



        DOMSource source = new DOMSource(document);

        Transformer transformer = getTransformer();

        StreamResult result = new StreamResult(STORAGE_LOCATION);
        transformer.transform(source, result);
    }

    public static synchronized void updateData(Message message) throws ParserConfigurationException, SAXException, IOException, TransformerException, XPathExpressionException {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(STORAGE_LOCATION);
        document.getDocumentElement().normalize();

        Node messageToUpdate = getNodeById(document, message.getId());

        if (messageToUpdate!= null) {

            NodeList childNodes = messageToUpdate.getChildNodes();

            for (int i = 0; i < childNodes.getLength(); i++) {

                Node node = childNodes.item(i);

                if (DESCRIPTION.equals(node.getNodeName())) {
                    node.setTextContent(message.getDescription());
                }

                if (USER.equals(node.getNodeName())) {
                    node.setTextContent(message.getUser());
                }

            }

            Transformer transformer = getTransformer();

            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File(filePath));
            transformer.transform(source, result);
        } else {
            throw new NullPointerException();
        }
    }

    public static synchronized boolean doesStorageExist() {
        File file = new File(STORAGE_LOCATION);
        return file.exists();
    }

    public static synchronized List<Message> getMessages() throws SAXException, IOException, ParserConfigurationException {
        List<Message> messages = new ArrayList<Message>();
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(STORAGE_LOCATION);
        document.getDocumentElement().normalize();
        Element root = document.getDocumentElement();
        NodeList messageList = root.getElementsByTagName(MESSAGE);
        for (int i = 0; i < messageList.getLength(); i++) {
            Element messageElement = (Element) messageList.item(i);
            String id = messageElement.getAttribute(ID);
            String description = messageElement.getElementsByTagName(DESCRIPTION).item(0).getTextContent();
            String user= messageElement.getElementsByTagName(USER).item(0).getTextContent();
            messages.add(new Message(id, description, user));
        }
        return messages;
    }

    public static synchronized int getStorageSize() throws SAXException, IOException, ParserConfigurationException {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(STORAGE_LOCATION);
        document.getDocumentElement().normalize();
        Element root = document.getDocumentElement();
        return root.getElementsByTagName(MESSAGE).getLength();
    }

    private static Node getNodeById(Document doc, String id) throws XPathExpressionException {
        XPath xpath = XPathFactory.newInstance().newXPath();
        XPathExpression expr = xpath.compile("//" + MESSAGE + "[@id='" + id + "']");
        return (Node) expr.evaluate(doc, XPathConstants.NODE);
    }

    private static Transformer getTransformer() throws TransformerConfigurationException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        // Formatting XML properly
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        return transformer;
    }

}
