package eu.cise.emulator.api.helpers;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;

/**
 * Exception Mapper a mechanism that allows restricted resources
 * <p>
 * A CISE HTTP Server is likely to be called by HTML pages directly served
 * to call the adecuate asset service
 */
public class ServerExceptionMapper {

    public Response toResponse(WebApiEmulatorException exception) {
        URI uri = null;
        try {
            uri = new URI("/base/index.html");
        } catch (Exception e) {
        }


        if (exception.getCode() == 404) return Response.seeOther(uri).build();
        //otherwise
        return Response.status(exception.getCode())
                .entity(exception.getMessage())
                .type(MediaType.TEXT_PLAIN).build();
    }
}
