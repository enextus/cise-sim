package eu.cise.emulator.app.util;

import eu.cise.emulator.integration.Validation.SubmissionAgent;
import eu.cise.servicemodel.v1.message.Message;
import eu.cise.signature.SignatureService;


public class SignatureDecorator implements SubmissionAgent {

    private final SubmissionAgent agent;
    private final SignatureService signatureService;

    public SignatureDecorator(SubmissionAgent agent, SignatureService signatureService) {
        this.agent = agent;
        this.signatureService = signatureService;
    }

    @Override
    public void forward(Message message) {
        signatureService.verify(message);

        agent.forward(message);
    }
}

