package eu.cise.sim.app.interop;

import javax.jws.WebParam;
import javax.jws.WebService;
@WebService
public interface restInter {
    String echoCISEMessageServiceREST(@WebParam(name="text") String text);
}