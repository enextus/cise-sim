package eu.cise.emulator.app.interop;

import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public interface SoapClient {
    String hello(@WebParam(name = "text") String text);
}

