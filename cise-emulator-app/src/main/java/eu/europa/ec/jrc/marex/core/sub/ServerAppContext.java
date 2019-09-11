package eu.europa.ec.jrc.marex.core.sub;

import eu.cise.servicemodel.v1.message.Message;
import eu.cise.signature.SignatureService;
import eu.cise.signature.SignatureServiceBuilder;
import eu.eucise.xml.DefaultXmlMapper;
import eu.eucise.xml.DefaultXmlValidator;
import eu.eucise.xml.XmlMapper;
import eu.eucise.xml.XmlValidator;
import eu.europa.ec.jrc.marex.candidate.SimConfig;
import eu.europa.ec.jrc.marex.util.SignatureDecorator;
import eu.europa.ec.jrc.marex.util.SimLogger;
import org.aeonbits.owner.ConfigFactory;

public class ServerAppContext {


    private final XmlMapper xmlMapper;

    private final MessageValidator validator;
    private final SimConfig config;
    private final SimLogger logger;
    private final SignatureService signatureService;
    private final XmlValidator xmlValidator;
    private final AcceptanceAgent acceptanceAgent;
    private final SubmissionAgent submissionAgent;

    public ServerAppContext() {
        this.config = ConfigFactory.create(SimConfig.class);

        this.xmlMapper = new DefaultXmlMapper.NotValidating();
        this.xmlValidator = new DefaultXmlValidator();
        this.validator = new MessageValidator();
        this.logger = new SimLogger() {
            @Override
            public void logSenderResponses(Sender result) {

            }

            @Override
            public void logSenderError(Throwable throwable) {

            }
        };


        this.signatureService = SignatureServiceBuilder.newSignatureService()
                .withKeyStoreName(config.getKeyStoreName()).
                        withKeyStorePassword(config.getKeyStorePassword())
                .withPrivateKeyAlias(config.getPrivateKeyAlias())
                .withPrivateKeyPassword(config.getPrivateKeyPassword())
                .build();

        GatewayProcessor submissionGatewayProcessor = new GatewayProcessor() {
            @Override
            public void process(Message message) {
                notify();
            }
        };
        //newInstanceOf(config.getSubmissionGatewayProcessor(), GatewayProcessor.class);


        //MessageValidator validator,GatewayProcessor gatewayProcessor, String gatewayAddressString)
        SubmissionAgent asubmitagent = new DefaultSubmissionAgent(new MessageValidator(), submissionGatewayProcessor, config.getSimulatorId());
        this.submissionAgent = new SignatureDecorator(asubmitagent, signatureService);

        this.acceptanceAgent = (AcceptanceAgent) new DefaultAcceptanceAgent(xmlMapper, xmlValidator);


        if (config.getWebappWsMode().contains("REST")) {
            Server myserver = (ServerRestConcrete) new ServerRestConcrete(config.getSimulatorId(), acceptanceAgent);
            ((ServerRestConcrete) myserver).SetupServerRestConcrete(config.getSimulatorId(), acceptanceAgent);
            myserver = (ServerRest) myserver;
        }

    }

    public DefaultAcceptanceAgent makeMessageProcessor() {
        return new DefaultAcceptanceAgent(xmlMapper, xmlValidator);
    }


}
