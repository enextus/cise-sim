package eu.cise.emulator.websocket.server;


import ch.qos.logback.classic.Logger;
import com.codahale.metrics.annotation.ExceptionMetered;
import com.codahale.metrics.annotation.Metered;
import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import eu.cise.emulator.websocket.repentir.WebSocketMessage;
import eu.cise.emulator.websocket.repentir.WebSocketMessageType;
import eu.cise.emulator.websocket.user.User;
import org.slf4j.LoggerFactory;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@Metered
@Timed
@ExceptionMetered
@ServerEndpoint("/websocket")
public class OutBoundWebSocketService {

    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(OutBoundWebSocketService.class);
    private HashMap<Session, User> users = new HashMap<Session, User>();
    private Set<Session> sessions = new HashSet<Session>();


    @OnOpen
    public void myOnOpen(final Session session) throws IOException {
        sessions.add(session);
        LOGGER.debug(" client Called @OnOpen  " + session.toString());
    }


    @OnMessage
    public void myOnMsg(final Session session, String message) {

        LOGGER.debug(" client Called @OnMessage with following content " + message.toString());
        ObjectMapper mapper = new ObjectMapper();
        try {
            WebSocketMessage msg = mapper.readValue(message, WebSocketMessage.class);
            LOGGER.debug("@OnMessage method traduce it to  msg:Data" + msg.getData() + "msg.Type" + msg.getType() + "msg.User" + msg.getUser());
            switch (msg.getType()) {
                case MEMBER_JOINED:
                    addUser(new User(msg.getUser().getName()), session);
                    break;
                case MEMBER_LEFT:
                    removeUser(session);
                    break;
                case TEXT_MESSAGE:
                    broadcastMessage(msg);
            }


        } catch (IOException e) {
            LOGGER.error("Wrong message format.");
            // return error message to user
        }
    }


    @OnClose
    public void myOnClose(final Session session, CloseReason cr) {
        LOGGER.debug(" client Called @OnClose with following reason " + cr.toString());
        sessions.remove(session);
        try {
            broadcastUserActivityMessage(WebSocketMessageType.MEMBER_LEFT);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        LOGGER.info("Connection closed to: " + session.getUserPrincipal());

    }


    private void broadcastMessage(WebSocketMessage msg) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String messageJson = mapper.writeValueAsString(msg);
            for (Session session : sessions) {
                session.getAsyncRemote().sendText(messageJson);
            }
        } catch (JsonProcessingException e) {
            LOGGER.error("Cannot convert message to json.");
        }
    }


    private void addUser(User user, Session session) throws JsonProcessingException {
        users.put(session, user);
        acknowledgeUserJoined(user, session);
        broadcastUserActivityMessage(WebSocketMessageType.MEMBER_JOINED);
    }

    private void removeUser(Session session) throws JsonProcessingException {
        users.remove(session);
        broadcastUserActivityMessage(WebSocketMessageType.MEMBER_LEFT);
    }

    private void acknowledgeUserJoined(User user, Session session) throws JsonProcessingException {
        WebSocketMessage webSocketMessage = new WebSocketMessage();
        webSocketMessage.setType(WebSocketMessageType.MEMBER_JOINED_ACK);
        webSocketMessage.setUser(user);
        //webSocketMessage.setData(CiseEmuApplication.appLiveRunning.instanceID.getName()); cannot inform of inicial
        session.getAsyncRemote().sendText(new ObjectMapper().writeValueAsString(webSocketMessage));
    }

    private void broadcastUserActivityMessage(WebSocketMessageType webSocketMessageType) throws JsonProcessingException {
        WebSocketMessage newWebSocketMessage = new WebSocketMessage();
        ObjectMapper mapper = new ObjectMapper();
        String data = mapper.writeValueAsString(users.values());
        newWebSocketMessage.setData(data);
        newWebSocketMessage.setType(webSocketMessageType);
        broadcastMessage(newWebSocketMessage);
    }


}
