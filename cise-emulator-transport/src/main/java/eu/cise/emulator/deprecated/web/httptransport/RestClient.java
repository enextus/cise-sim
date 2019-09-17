package eu.cise.emulator.deprecated.web.httptransport;

public interface RestClient {

    RestResult post(String address, String payload);

    RestResult get(String address);

    RestResult delete(String address);

}
