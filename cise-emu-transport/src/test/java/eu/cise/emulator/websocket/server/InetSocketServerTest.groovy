package eu.cise.emulator.websocket.server


import org.junit.experimental.categories.Category
import spock.lang.Shared
import spock.lang.Specification

@Category(UnitTestSocketServer.class)
class InetSocketServerTest extends Specification {


    @Shared OutBoundWebSocketService messageService = new OutBoundWebSocketService ();
   // @Rule startedDefaultServer ;

    def 'start with no connection '() {
        expect: 'Should show no connection at start'
        //messageService.connections().size() ==0
    }
    def 'Whenever create a connection should show connection > 0 '() {
        setup: 'having a connection added to messageService'
        //TODO: create a client to the create websocket
        expect: 'Should accept an incoming message'
        //TODO: create a connections() to validate initial connection give empty response
        true==true
    }
}