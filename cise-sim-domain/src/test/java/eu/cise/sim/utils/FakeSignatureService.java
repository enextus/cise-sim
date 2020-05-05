package eu.cise.sim.utils;

import eu.cise.servicemodel.v1.message.Message;
import eu.cise.signature.SignatureService;

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
