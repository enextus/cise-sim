package eu.cise.emulator.deprecated.web.httptransport;

public class TransportFacadeConcrete implements TransportFacade {
    @Override
    public ServerRest getServerRest() {
        ServerRest myserver = (ServerRest) ServerRestConcrete.getInstance();
        return (ServerRest) myserver;
    }

    @Override
    public ServerSoap getServerSoap() {
        ServerSoap myserver = (ServerSoap) ServerSoapConcrete.getInstance();
        return (ServerSoap) myserver;
    }

    @Override
    public SenderRest getClientRest() {
        return null;
    }

    @Override
    public SenderSoap getClientSoap() {
        return null;
    }
}
