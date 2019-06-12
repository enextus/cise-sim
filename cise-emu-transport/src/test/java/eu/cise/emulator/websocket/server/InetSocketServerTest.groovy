package eu.cise.emulator.websocket.server


import org.junit.experimental.categories.Category
import spock.lang.Shared
import spock.lang.Specification

@Category(UnitTestSocketServer.class)
class InetSocketServerTest extends Specification {


    @Shared IhmWebSocket messageService = new IhmWebSocket(17932)


    def 'start with no connection '() {
        expect: 'Should show no connection at start'
        messageService.connections().size() ==0
    }
    def 'Whenever create a connection should show connection > 0 '() {
        setup: 'having a connection added to messageService'

        mySocket = awebsocketFactory.createWebSocket("wss:/localhost:17932")
        mWebSocket.connectAsynchronously();
        expect: 'Should accept an incoming message'
        messageService.connections().size() >0
    }
}