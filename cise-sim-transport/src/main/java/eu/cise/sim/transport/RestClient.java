package eu.cise.sim.transport;

public interface RestClient {

    RestResult post(String address, String payload);

    RestResult get(String address);

    RestResult delete(String address);

}


//*REPENTIR **/
//*package jrc.cise.transport;*//