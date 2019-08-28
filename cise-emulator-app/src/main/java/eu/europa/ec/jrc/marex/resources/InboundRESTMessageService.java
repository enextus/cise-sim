package eu.europa.ec.jrc.marex.resources;

import ch.qos.logback.classic.Logger;
import eu.eucise.xml.DefaultXmlMapper;
import eu.eucise.xml.XmlMapper;
import eu.europa.ec.jrc.marex.core.Executor;
import eu.europa.ec.jrc.marex.core.sub.MessageValidator;
import eu.europa.ec.jrc.marex.core.sub.Sender;
import eu.europa.ec.jrc.marex.core.sub.SourceStreamProcessor;
import eu.europa.ec.jrc.marex.core.sub.ValidationResult;
import eu.europa.ec.jrc.marex.CiseEmulatorConfiguration;
import eu.europa.ec.jrc.marex.util.InteractIOFile;
import eu.europa.ec.jrc.marex.util.SimLogger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import java.io.IOException;

@Path("/emu/rest")
public class InboundRESTMessageService {
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(InboundRESTMessageService.class);
    private String fileNameTemplate;
    private XmlMapper xmlMapper = new DefaultXmlMapper();
    private Executor executor;

    private CiseEmulatorConfiguration emulatorConfig;

    public InboundRESTMessageService(CiseEmulatorConfiguration emulatorConfig) {
        this.fileNameTemplate = emulatorConfig.getOutputDirectory() + emulatorConfig.getPublishedId() + "_in_";
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
    @Produces("text/plain")
    @Path("/CISEMessageServiceREST")
    public String sendMessage(String inputXmlMessage) throws Exception {

        Logger mylogger = (Logger) LoggerFactory.getLogger(InboundRESTMessageService.class.getName());
        // TODO: implement the xml rest reception
        /*XmlMapper mapper = new DefaultXmlMapper.Pretty();
        String completedMessage = mapper.toXML(someMessage ).toString();
        return someMessage;*/


        String createdfile = InteractIOFile.createRef(this.fileNameTemplate, new StringBuffer(inputXmlMessage));
        if (createdfile == null)
            throw new IOException("unable to create send file : " + InteractIOFile.getFilename(this.fileNameTemplate, createdfile, ""));

        ValidationResult validResult = executor.validateIncoming(inputXmlMessage);
        LOGGER.info("inputXmlMessage validated {} : xml {} and conformed structure:{} signature {} semantic {}", validResult.isOK(emulatorConfig.getSignatureOnReceive().contains("true")),
                validResult.isOkXML(), validResult.isOkEntity(), validResult.isOkSignedEntity(), validResult.isOkSemantic());
        String ackMessage = "";
        if (validResult.isOkEntity()==false) {
            ackMessage = executor.AcknowledgmentFailMessage(inputXmlMessage, "BAD_REQUEST", "content could not be validated as Entity");
            LOGGER.info(" respond with a BAD REQUEST ACK for  structure:{} semantic {}", validResult.isOkEntity(), validResult.isOkSemantic());
        }else if (emulatorConfig.getSignatureOnReceive().equals("true") && validResult.isOkSignedEntity()==false){
            ackMessage =  executor.AcknowledgmentFailMessage(inputXmlMessage,"SECURITY_ERROR","signature error");
        LOGGER.info(" respond with a SECURITY ERROR ACK for  signature required on start :{} signature validated {}", validResult.isOK(emulatorConfig.getSignatureOnReceive().contains("true")),validResult.isOkSignedEntity());}
        else
            ackMessage =  executor.AcknowledgmentSuccessMessage(inputXmlMessage);


        String createdreffile = InteractIOFile.createRelativeRef(fileNameTemplate, "Ack", createdfile, new StringBuffer(ackMessage));
        LOGGER.error("{} => {} / {}", createdfile, InteractIOFile.getFilename(fileNameTemplate, createdfile, ""), (createdfile.equals("200") ? "ACK" : createdfile));
        if (createdreffile != null)
            return (ackMessage);
        else
            throw new IOException("unable to create ack file : " + InteractIOFile.getFilename(this.fileNameTemplate, createdreffile, "ack"));
    }

}
