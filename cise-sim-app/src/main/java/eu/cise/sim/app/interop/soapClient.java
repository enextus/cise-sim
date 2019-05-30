package eu.cise.sim.app.interop;

import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public interface soapClient {
    String hello(@WebParam(name = "text") String text);
}