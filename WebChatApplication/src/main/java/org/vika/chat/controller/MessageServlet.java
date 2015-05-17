package org.vika.chat.controller; /**
 * Created by Администратор on 26.04.2015.
 */



import static org.vika.chat.util.MessageUtil.MESSAGES;
import static org.vika.chat.util.MessageUtil.TOKEN;
import static org.vika.chat.util.MessageUtil.getIndex;
import static org.vika.chat.util.MessageUtil.getToken;
import static org.vika.chat.util.MessageUtil.jsonToMessage;
import static org.vika.chat.util.MessageUtil.stringToJson;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.AsyncContext;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;

import org.apache.log4j.Logger;
import org.vika.chat.model.Message;
import org.vika.chat.model.MessageStorage;
import org.vika.chat.storage.xml.XMLHistoryUtil;
import org.vika.chat.util.ServletUtil;
import java.util.LinkedList;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.xml.sax.SAXException;



@WebServlet("/chat")
public class MessageServlet  extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static Logger logger = Logger.getLogger(MessageServlet.class.getName());
    private List<AsyncContext> contexts = new LinkedList<>();

    public MessageServlet() {
    }

    public void init() throws ServletException {
        try {
            this.loadHistory();
        } catch (SAXException | IOException | ParserConfigurationException | TransformerException var2) {
            logger.error(var2);
        }

    }



    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      /*  final AsyncContext asyncContext = request.startAsync(request, response);
        asyncContext.setTimeout(10 * 60 * 1000);
        contexts.add(asyncContext);*/

        logger.info("doGet");

        String token = request.getParameter("token");
        logger.info("Token " + token);
        response.setCharacterEncoding("UTF-8");
        if (token != null && !"".equals(token)) {
            int index = getIndex(token);
            logger.info("Index " + index);
            String messages = formResponse(index);
            response.setContentType(ServletUtil.APPLICATION_JSON);
            PrintWriter out = response.getWriter();
            out.print(messages);
            out.flush();
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "'token' parameter needed");
        }

    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       /* List<AsyncContext> asyncContexts = new ArrayList<>(this.contexts);
        this.contexts.clear();*/
        logger.info("doPost");
        String data = ServletUtil.getMessageBody(request);
        logger.info(data);

        try {

            JSONObject e = stringToJson(data);
            Message message = jsonToMessage(e);
            MessageStorage.addMessage(message);
            XMLHistoryUtil.addData(message);
            response.setStatus(200);
        } catch (ParserConfigurationException | SAXException | TransformerException | ParseException var6) {
            logger.error(var6);
            response.sendError(400);
        }


    }


    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      /*  List<AsyncContext> asyncContexts = new ArrayList<>(this.contexts);
        this.contexts.clear();*/
        logger.info("doPut");
        String data = ServletUtil.getMessageBody(request);
        logger.info(data);

        try {
            JSONObject e = stringToJson(data);
            Message message = jsonToMessage(e);
            String id = message.getId();
            Message messageToUpdate = MessageStorage.getMessageById(id);
            if (messageToUpdate != null) {
                messageToUpdate.setDescription(message.getDescription());
                messageToUpdate.setUser(message.getUser());
                XMLHistoryUtil.updateData(messageToUpdate);
                response.setStatus(200);
            } else {
                response.sendError(400, "Message does not exist");
            }

        } catch (ParserConfigurationException | SAXException | TransformerException | XPathExpressionException | ParseException var8) {
            logger.error(var8);
            response.sendError(400);
        }

    }
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("doDelete");
        String data = ServletUtil.getMessageBody(request);
        logger.info(data);

        try {
            JSONObject e = stringToJson(data);
            Message message = jsonToMessage(e);
            String id = message.getId();
            Message messageToUpdate = MessageStorage.getMessageById(id);
            if (messageToUpdate != null) {
                messageToUpdate.setDescription(message.getDescription());
                messageToUpdate.setUser(message.getUser());
                XMLHistoryUtil.updateData(messageToUpdate);
                response.setStatus(200);
            } else {
                response.sendError(400, "Message does not exist");
            }
        } catch (ParserConfigurationException | SAXException | TransformerException | XPathExpressionException | ParseException var8) {
            logger.error(var8);
            response.sendError(400);
        }

    }

    private String formResponse(int index) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(MESSAGES, MessageStorage.getSubTasksByIndex(index));
        jsonObject.put(TOKEN, getToken(MessageStorage.getSize()));
        return jsonObject.toJSONString();
    }

    private void loadHistory() throws SAXException, IOException, ParserConfigurationException, TransformerException {
        if (XMLHistoryUtil.doesStorageExist()) {
            MessageStorage.addAll(XMLHistoryUtil.getMessages());
        } else {
            XMLHistoryUtil.createStorage();
            this.addStubData();
        }

    }

    private void addStubData() throws ParserConfigurationException, TransformerException {
        Message[] stubTasks = new Message[]{new Message("1", "Hello!", "vika")};
        MessageStorage.addAll(stubTasks);
        Message[] var2 = stubTasks;
        int var3 = stubTasks.length;

        for (int var4 = 0; var4 < var3; ++var4) {
            Message message = var2[var4];

            try {
                XMLHistoryUtil.addData(message);
            } catch (SAXException | IOException | TransformerException | ParserConfigurationException var7) {
                logger.error(var7);
            }
        }

    }
}
