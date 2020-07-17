package eu.cise.sim.api.helpers;

import eu.cise.sim.api.APIError;
import eu.cise.sim.api.ResponseApi;

import javax.ws.rs.core.Response;

public class BuildHelper {

    public static Response buildResponse(ResponseApi<?> response, Response.Status statusKo, Response.Status optionalStatusOk) {

        if (!response.isOk()) {
            return Response
                    .status(statusKo)
                    .entity(new APIError(response.getErrDetail()))
                    .build();
        }

        return Response.status(optionalStatusOk != null ? optionalStatusOk : Response.Status.OK)
                .entity(response.getResult())
                .build();
    }

    public static Response buildResponse(ResponseApi<?> response, Response.Status statusKo) {
        return buildResponse(response, statusKo, null);
    }
}
