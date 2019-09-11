package eu.cise.emulator.httptransport;

public interface TransportFacade {

    ServerRest getServerRest();

    ServerSoap getServerSoap();

    SenderRest getClientRest();

    SenderSoap getClientSoap();

}
