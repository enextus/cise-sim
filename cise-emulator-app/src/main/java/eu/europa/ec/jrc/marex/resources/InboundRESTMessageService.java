package eu.europa.ec.jrc.marex.resources;

import ch.qos.logback.classic.Logger;
import eu.eucise.xml.DefaultXmlMapper;
import eu.eucise.xml.XmlMapper;
import eu.europa.ec.jrc.marex.CiseEmulatorConfiguration;
import eu.europa.ec.jrc.marex.core.Executor;
import eu.europa.ec.jrc.marex.core.sub.MessageValidator;
import eu.europa.ec.jrc.marex.core.sub.Sender;
import eu.europa.ec.jrc.marex.core.sub.SourceStreamProcessor;
import eu.europa.ec.jrc.marex.core.sub.ValidationResult;
import eu.europa.ec.jrc.marex.util.InteractIOFile;
import eu.europa.ec.jrc.marex.util.SimLogger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.io.IOException;

@Path("/emu/rest")
public class InboundRESTMessageService {
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(InboundRESTMessageService.class);
    public static final String ERROR = "ERROR";
    public static final String ACK = "ACK";
    public static final String MESSAGE_MODAL = "RECEIVE";
    private String fileNameTemplate;
    private XmlMapper xmlMapper = new DefaultXmlMapper();
    private Executor executor;

    private CiseEmulatorConfiguration emulatorConfig;

    public InboundRESTMessageService(CiseEmulatorConfiguration emulatorConfig) {
        this.fileNameTemplate = emulatorConfig.getInputDirectory() + "/" + emulatorConfig.getPublishedId();
        this.emulatorConfig = emulatorConfig;
        DefaultXmlMapper xmlMapper = new DefaultXmlMapper();
        MessageValidator validator = new MessageValidator();

        this.executor = new Executor(new SourceStreamProcessor(),
                new Sender(),
                new SimLogger.Slf4j(),
                emulatorConfig,
                xmlMapper,
                validator);
    }

    @POST
    @Consumes("text/plain,text/xml,application/xml")
    @Produces("text/xml")
    @Path("/CISEMessageServiceREST")
    public String sendMessage(String inputXmlMessage) throws Exception {

        Logger mylogger = (Logger) LoggerFactory.getLogger(InboundRESTMessageService.class.getName());
        // TODO: implement the xml rest reception
        /*XmlMapper mapper = new DefaultXmlMapper.Pretty();
        String completedMessage = mapper.toXML(someMessage ).toString();
        return someMessage;*/


        String createdFile = InteractIOFile.createRef(this.fileNameTemplate, MESSAGE_MODAL, new StringBuffer(inputXmlMessage));
        if (createdFile == null)
            throw new IOException("unable to create send file : " + InteractIOFile.getFilename(this.fileNameTemplate, null, MESSAGE_MODAL, ""));

        ValidationResult validResult = executor.validateIncoming(inputXmlMessage);
        LOGGER.info("inputXmlMessage validated {} : xml {} and conformed structure:{} signature {} semantic {}", validResult.isOK(emulatorConfig.getSignatureOnReceive().contains("true")),
                validResult.isOkXML(), validResult.isOkEntity(), validResult.isOkSignedEntity(), validResult.isOkSemantic());
        String ackMessage = "";
        String filePost = ACK;
        if (validResult.isOkEntity() == false) {
            ackMessage = executor.AcknowledgmentFailMessage(inputXmlMessage, "BAD_REQUEST", "content could not be validated as Entity");
            filePost = ERROR;
            // LOGGER.warn("BAD_REQUEST ACK sent for  structural {}, semantic {} reasons ; see file://{}", validResult.isOkEntity(), validResult.isOkSemantic(),InteractIOFile.getFilename(this.fileNameTemplate, filePost,MESSAGE_MODAL, filePost));
        } else if (emulatorConfig.getSignatureOnReceive().equals("true") && validResult.isOkSignedEntity() == false) {
            ackMessage = executor.AcknowledgmentFailMessage(inputXmlMessage, "SECURITY_ERROR", "signature error");
            filePost = ERROR;
            // LOGGER.warn("SECURITY ERROR ACK sent for signature required on start {} signature validated {}  ; see file://{} ", validResult.isOK(emulatorConfig.getSignatureOnReceive().contains("true")),validResult.isOkSignedEntity(),InteractIOFile.getFilename(this.fileNameTemplate, filePost,MESSAGE_MODAL, filePost));

        } else
            executor.AcknowledgmentSuccessMessage(inputXmlMessage);


        String createdreffile = InteractIOFile.createRelativeRef(fileNameTemplate, filePost, createdFile, MESSAGE_MODAL, new StringBuffer(ackMessage));
        LOGGER.warn("New content RECEIVED with timestamp {} result in {} / see AcknowledgementFile://{}", createdFile, filePost, InteractIOFile.getFilename(fileNameTemplate, createdFile, MESSAGE_MODAL, filePost));
        if (createdreffile != null)
            return (ackMessage);
        else
            throw new IOException("New content RECEIVED with timestamp " + createdFile + " / unable to create ack file : " + InteractIOFile.getFilename(this.fileNameTemplate, createdreffile, MESSAGE_MODAL, filePost));
    }

}
