package eu.cise.sim.transport;
public interface TransportFacade {

    ServerRest getServerRest();
    ServerSoap getServerSoap();
    SenderRest getClientRest();
    SenderSoap getClientSoap();

}
