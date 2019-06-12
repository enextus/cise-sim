package eu.cise.emulator.websocket.server;


import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import eu.cise.emulator.websocket.message.Message;
import eu.cise.emulator.websocket.message.MessageType;
import eu.cise.emulator.websocket.user.User;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@ServerEndpoint("/")
public class IhmWebSocket {

    private final static Logger logger = LogManager.getLogger(IhmWebSocket.class);

    private HashMap<Session, User> users;

    private Set<Session> sessions;

    protected IhmWebSocket(int port) {
        sessions = new HashSet<>();
        users = new HashMap<>();
        logger.info(" established service from : " + port);
    }

        @OnOpen
        public void myOnOpen(final Session session) throws IOException {
            sessions.add(session);
            logger.info(" established new session " + session.toString() );
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
            logger.info( "Message from user: " + msg.getUser() + ", text: " + msg.getData() + ", type:" + msg.getType());

        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

        @OnClose
        public void myOnClose(final Session session, CloseReason cr) {
                users.remove(session);
                try {
                    broadcastUserActivityMessage(MessageType.USER_LEFT);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
         }


    private void broadcastMessage(Message msg) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String messageJson = mapper.writeValueAsString(msg);
            for (Session sess : sessions) {
                /****/sess.getAsyncRemote().sendText(messageJson);
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
        /****/session.getAsyncRemote().sendText(new ObjectMapper().writeValueAsString(message));
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
