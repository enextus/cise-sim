package eu.cise.emulator.httptransport;

import eu.cise.emulator.integration.Exception.CiseTransportException;
import eu.cise.emulator.integration.Validation.AcceptanceResponse;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Adapts an application response to a REST
 */
public class ResponseAdapter {

    public Response toJaxRSResponse(AcceptanceResponse response) {
        return toGenericResponse(response);
    }

    public Response toSoapResponse(AcceptanceResponse response) {
        return toGenericResponse(response);
    }

    public Response toGenericResponse(AcceptanceResponse response) {
        switch (response) {
            case PROCESSED:
                return buildXmlResponse(response, 202);
            case XML_MALFORMED:
                return buildXmlResponse(response, 400);
            case VALIDATION_ERROR:
                return buildXmlResponse(response, 400);
            case INVALID_SIGNATURE:
                return buildXmlResponse(response, 400);
            case INTERNAL_ERROR:
                return buildXmlResponse(response, 500);
            default:
                throw new CiseTransportException("Not Supported response", response.getDeclaringClass());
        }
    }

    private Response buildXmlResponse(AcceptanceResponse response, int status) {
        return Response.status(status)
                .type(MediaType.APPLICATION_XML)
                .entity(response.getXmlBody())
                .build();
    }
}
