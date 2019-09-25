package eu.cise.emulator.deprecated.cli.candidate;

import eu.cise.emulator.deprecated.cli.client.SendResult;

public interface RestClient {

    SendResult post(String address, String payload);

    SendResult get(String address);

    SendResult delete(String address);

}
