package eu.cise.emulator.httptransport;

public interface RestClient {

    RestResult post(String address, String payload);

    RestResult get(String address);

    RestResult delete(String address);

}
