package eu.cise.emulator.httptransport;


import eu.cise.emulator.httptransport.Exception.CiseTransportException;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.function.Function;

/**
 * This class is meant to perform RESTful request to nodes or legacy systems.
 * The current implementation is just sending CISE Messages
 */
public class JerseyRestClient implements RestClient {

    private final Client client;

    public JerseyRestClient() {
        this(ClientBuilder.newClient());
    }

    public JerseyRestClient(Client client) {
        this.client = client;
    }

    @Override
    public RestResult post(String address, String payload) {
        return vestException(address, (a) -> translateResult(targetXml(a).post(Entity.xml(payload))));
    }

    @Override
    public RestResult get(String address) {
        return vestException(address, (a) -> translateResult(targetXml(a).get()));

    }

    @Override
    public RestResult delete(String address) {
        return vestException(address, (a) -> translateResult(targetXml(a).delete()));
    }

    private Invocation.Builder targetXml(String address) {
        return client.target(address).request(MediaType.APPLICATION_XML);
    }

    private RestResult translateResult(Response r) {
        return new RestResult(r.getStatus(), r.readEntity(String.class),
                r.getStatusInfo().getReasonPhrase());
    }

    private RestResult vestException(String address, Function<String, RestResult> function) {
        try {
            return function.apply(address);
        } catch (Throwable t) {
            throw new CiseTransportException("Error while contacting address=" + address + "|");
        }
    }

}


//*REPENTIR **/
//*package jrc.cise.transport;*//