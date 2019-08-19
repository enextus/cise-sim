package eu.europa.ec.jrc.marex.candidate;

import eu.europa.ec.jrc.marex.client.RestResult;

public interface RestClient {

    RestResult post(String address, String payload);

    RestResult get(String address);

    RestResult delete(String address);

}


//*REPENTIR **/
//*package jrc.cise.transport;*//