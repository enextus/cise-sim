package eu.cise.emulator.deprecated.web.app.transport;

public interface RestClient {

    RestResult post(String address, String payload);

    RestResult get(String address);

    RestResult delete(String address);

}
