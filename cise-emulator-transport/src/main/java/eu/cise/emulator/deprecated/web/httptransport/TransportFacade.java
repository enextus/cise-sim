package eu.cise.emulator.deprecated.web.httptransport;

public interface TransportFacade {

    ServerRest getServerRest();

    ServerSoap getServerSoap();

    SenderRest getClientRest();

    SenderSoap getClientSoap();

}
