package eu.cise.emulator;

import eu.cise.servicemodel.v1.message.Message;

public interface SignatureService {
    /**
     * This method will validate the signature of an incoming message against
     * the public key of the Sender System (Gateway or Legacy System).
     *
     * @param message message to be verified
     */
    void verify(Message message);

    /**
     * This method will sign a CISE Message and will return an instance of the
     * signed message
     *
     * @param message message to be signed
     * @return the signed message
     */
    Message sign(Message message);

}
