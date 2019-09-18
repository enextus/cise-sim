package eu.cise.emulator.utils;

import eu.cise.signature.SignatureService;
import eu.cise.servicemodel.v1.message.Message;

public class FakeSignatureService implements SignatureService {
    @Override
    public void verify(Message message) {
        // do nothing
    }

    @Override
    public Message sign(Message message) {
        return message;
    }
}
