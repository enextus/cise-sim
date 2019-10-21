package eu.cise.emulator.api.resources;

import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;

public class AssetRedirectionResource {


    @Path("/")
    public Response redirectRoot() {
        return Response.temporaryRedirect(getAssetBaseURI()).build();
    }


    private URI getAssetBaseURI() {
        try {
            return new URI("/index.html");
        } catch (URISyntaxException e) {
            throw new RuntimeException("Should never happen. the URI of /base/index.html is correct.");
        }
    }
}
