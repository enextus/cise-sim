package eu.cise.emulator.app.transport;

import ch.qos.logback.classic.Logger;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import eu.cise.emulator.app.core.WebSocketMessage;
import eu.cise.emulator.app.core.WebSocketMessageType;
import org.eclipse.jetty.websocket.api.WebSocketException;
import org.slf4j.LoggerFactory;

import javax.websocket.*;
import java.net.URI;
import java.net.URISyntaxException;


@ClientEndpoint
public class OutBoundWebSocketClient {
    static OutBoundWebSocketClient clientEndPoint;
    private final Logger logger = (Logger) LoggerFactory.getLogger(OutBoundWebSocketService.class);
    private final WebSocketContainer container;
    private Session userSession = null;
    private URI internalUri;
    private MessageHandler messageHandler;
    private boolean firstConnect = true;

    private OutBoundWebSocketClient(String port, MessageHandler messageHandler) {
        // async wait for available
        try {
            port = (port == null ? "8080" : port);
            internalUri = new URI("ws://localhost:" + port + "/websocket");
            container = ContainerProvider.getWebSocketContainer();
            this.messageHandler = messageHandler;
        } catch (URISyntaxException es) {
            throw new RuntimeException(es);
        }

    }

    public static OutBoundWebSocketClient build(String port) {
        if (clientEndPoint != null) {
            return clientEndPoint;
        } else {
            OutBoundWebSocketClient outBoundWebSocketClient = new OutBoundWebSocketClient(port, new DefaultMessageHandler());
            return outBoundWebSocketClient;
        }
    }

    public static OutBoundWebSocketClient rebuild() throws Exception {
        if (clientEndPoint != null) {
            clientEndPoint.firstConnect = true;
            return clientEndPoint;
        } else {
            throw new WebSocketException();
        }
    }

    public void startConnect() {
        try {
            if (firstConnect) {
                container.connectToServer(this, internalUri);
                WebSocketMessage message = new WebSocketMessage();
                message.setMember("#Master#");
                message.setType(WebSocketMessageType.MEMBER_JOINED);
                message.setData("");
                message.setStatus("Success");
                sendMessage(message);
                firstConnect = false;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Callback hook for Connection open events.
     *
     * @param userSession the userSession which is opened.
     */
    @OnOpen
    public void onOpen(Session userSession) {
        System.out.println("opening websocket");
        this.userSession = userSession;
    }

    /**
     * Callback hook for Connection close events.
     *
     * @param userSession the userSession which is getting closed.
     * @param reason      the reason for connection close
     */
    @OnClose
    public void onClose(Session userSession, CloseReason reason) {
        System.out.println("closing websocket");
        this.userSession = null;
    }

    /**
     * Callback hook for WebSocketMessage Events. This method will be invoked when a client send a message.
     *
     * @param message The text message
     */
    @OnMessage
    public void onMessage(String message) {
        if (this.messageHandler != null) {
            this.messageHandler.handleMessage(message);
        }
    }

    /**
     * register message handler
     *
     * @param msgHandler
     */
    public void addMessageHandler(MessageHandler msgHandler) {
        this.messageHandler = msgHandler;
    }

    /**
     * Send a message.
     *
     * @param message
     */
    public void sendMessage(WebSocketMessage message) {
        String messageAsJson = "{}";
        try {
            messageAsJson = new ObjectMapper().writeValueAsString(message);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        this.userSession.getAsyncRemote().sendText(messageAsJson);
    }


}


