package eu.cise.emulator.deprecated.web.app.transport;


import ch.qos.logback.classic.Logger;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import eu.cise.emulator.deprecated.web.app.core.WebSocketMessage;
import eu.cise.emulator.deprecated.web.app.core.WebSocketMessageType;
import org.slf4j.LoggerFactory;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import static eu.cise.emulator.deprecated.web.app.core.WebSocketMessageType.*;

@ServerEndpoint("/websocket")
public class OutBoundWebSocketService {

    private final Logger logger = (Logger) LoggerFactory.getLogger(OutBoundWebSocketService.class);

    private static HashMap<String, Session> members = new HashMap<String, Session>();

    private static Set<Session> sessions = new HashSet<Session>();


    @OnOpen
    public void myOnOpen(final Session session) throws IOException {
        sessions.add(session);
        logger.info(" ## OutBoundWebSocketService ## create session " + session.toString());
    }


    @OnMessage
    public void myOnMsg(final Session session, String message) {

        ObjectMapper mapper = new ObjectMapper();
        try {
            WebSocketMessage msg = mapper.readValue(message, WebSocketMessage.class);

            switch (msg.getType()) {
                case MEMBER_JOINED:
                    addMember(msg.getMember(), session);
                    break;
                case MEMBER_JOINED_ACK:
                    break;
                case MEMBER_LEFT:
                    _LEFT:
                    removeMember(msg.getMember());
                    break;
                case TEXT_MESSAGE:
                    broadcastMessage(msg);
            }

            logger.info(" ## OutBoundWebSocketService ##  obtained message from user: " + msg.getMember() + ", text: " + msg.getData());
        } catch (IOException e) {
            logger.error(" ## OutBoundWebSocketService ## obtained message with wrong message format.");
            // return error message to user
        }
    }


    @OnClose
    public void myOnClose(final Session session, CloseReason cr) {
        sessions.remove(session);
        try {
            broadcastUserActivityMessage(MEMBER_LEFT);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        logger.info("## OutBoundWebSocketService ## get connection closed to: " + session.getUserPrincipal());

    }


    private void broadcastMessage(WebSocketMessage msg) {
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

    private void addMember(String member, Session session) throws JsonProcessingException {
        members.put(member, session);
        acknowledgeUserJoined(member, session);
        broadcastUserActivityMessage(MEMBER_JOINED);
    }

    private void removeMember(String id) throws JsonProcessingException {
        members.remove(id);
        broadcastUserActivityMessage(MEMBER_LEFT);
    }

    private void acknowledgeUserJoined(String member, Session session) throws JsonProcessingException {
        WebSocketMessage message = new WebSocketMessage();
        message.setType(MEMBER_JOINED_ACK);
        message.setMember(member);
        session.getAsyncRemote().sendText(new ObjectMapper().writeValueAsString(message));
    }

    private void broadcastUserActivityMessage(WebSocketMessageType webSocketMessageType) throws JsonProcessingException {
        WebSocketMessage newMessage = new WebSocketMessage();
        ObjectMapper mapper = new ObjectMapper();
        String data = mapper.writeValueAsString(members.values());
        newMessage.setMember("WS_BroadCast_Service");
        newMessage.setData(data);
        newMessage.setType(webSocketMessageType);
        broadcastMessage(newMessage);
    }


}
