package eu.europa.ec.jrc.marex.candidate;

import eu.europa.ec.jrc.marex.client.SendResult;

public interface RestClient {

    SendResult post(String address, String payload);

    SendResult get(String address);

    SendResult delete(String address);

}


//*REPENTIR **/
//*package jrc.cise.transport;*//