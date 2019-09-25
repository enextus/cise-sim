package eu.cise.emulator.api.resources;

import eu.cise.emulator.api.helpers.WebApiEmulatorException;

import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;

public class DefaultMessageResource {


    @Path("/")
    public Response redirect() throws WebApiEmulatorException {
        URI uri = null;
        try {
            uri = new URI("/base/index.html");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return Response
                .temporaryRedirect(uri)
                .build();
    }

}
