package eu.cise.emulator.app.transport;


import ch.qos.logback.classic.Logger;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import eu.cise.emulator.app.candidate.Message;
import eu.cise.emulator.app.candidate.MessageType;
import eu.cise.emulator.app.candidate.User;
import org.slf4j.LoggerFactory;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@ServerEndpoint("/websocket")
public class OutBoundWebSocketService {

    private final Logger logger = (Logger) LoggerFactory.getLogger(OutBoundWebSocketService.class);

    private HashMap<Session, User> users = new HashMap<Session, User>();

    private Set<Session> sessions = new HashSet<Session>();


    @OnOpen
    public void myOnOpen(final Session session) throws IOException {
        sessions.add(session);
        logger.info(" established new session " + session.toString());
    }




    @OnMessage
    public void myOnMsg(final Session session, String message) {

        ObjectMapper mapper = new ObjectMapper();
        try {
            Message msg = mapper.readValue(message, Message.class);

            switch (msg.getType()) {
                case USER_JOINED:
                    addUser(new User(msg.getUser().getName()), session);
                    break;
                case USER_LEFT:
                    removeUser(session);
                    break;
                case TEXT_MESSAGE:
                    broadcastMessage(msg);
            }

                logger.info("Message from user: " + msg.getUser() + ", text: " + msg.getData());
        } catch (IOException e) {
            logger.error("Wrong message format.");
            // return error message to user
        }
    }


    @OnClose
    public void myOnClose(final Session session, CloseReason cr) {
        sessions.remove(session);
        try {
            broadcastUserActivityMessage(MessageType.USER_LEFT);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        logger.info("Connection closed to: " + session.getUserPrincipal());

    }



    private void broadcastMessage(Message msg) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String messageJson = mapper.writeValueAsString(msg);
            for (Session session : sessions) {
                session.getAsyncRemote().sendText(messageJson);
            }
        } catch (JsonProcessingException e) {
            logger.error("Cannot convert message to json.");
        }
    }

    private void addUser(User user, Session session) throws JsonProcessingException {
        users.put(session, user);
        acknowledgeUserJoined(user, session);
        broadcastUserActivityMessage(MessageType.USER_JOINED);
    }

    private void removeUser(Session session) throws JsonProcessingException {
        users.remove(session);
        broadcastUserActivityMessage(MessageType.USER_LEFT);
    }

    private void acknowledgeUserJoined(User user, Session session) throws JsonProcessingException {
        Message message = new Message();
        message.setType(MessageType.USER_JOINED_ACK);
        message.setUser(user);
        session.getAsyncRemote().sendText(new ObjectMapper().writeValueAsString(message));
    }

    private void broadcastUserActivityMessage(MessageType messageType) throws JsonProcessingException {
        Message newMessage = new Message();
        ObjectMapper mapper = new ObjectMapper();
        String data = mapper.writeValueAsString(users.values());
        newMessage.setData(data);
        newMessage.setType(messageType);
        broadcastMessage(newMessage);
    }



}
