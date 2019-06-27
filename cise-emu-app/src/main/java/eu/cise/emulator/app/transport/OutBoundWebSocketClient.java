package eu.cise.emulator.app.transport;

import javax.websocket.*;
import java.net.URI;
import java.net.URISyntaxException;


@ClientEndpoint
public class OutBoundWebSocketClient {

    static OutBoundWebSocketClient clientEndPoint;

    Session userSession = null;
    private MessageHandler messageHandler;

    private OutBoundWebSocketClient() {
        try {
            URI internalUri = new URI("ws://localhost:8080/websocket");
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            container.connectToServer(this, internalUri);
        } catch (URISyntaxException es) {
            throw new RuntimeException(es);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public static OutBoundWebSocketClient build() {
        if (clientEndPoint != null) {
            return clientEndPoint;
        } else {
            return new OutBoundWebSocketClient();
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
     * Callback hook for Message Events. This method will be invoked when a client send a message.
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
    public void sendMessage(String message) {
        this.userSession.getAsyncRemote().sendText(message);
    }

    /**
     * Message handler.
     *
     * @author Jiji_Sasidharan
     */
    public interface MessageHandler {
        void handleMessage(String message);
    }
}


