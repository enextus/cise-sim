package eu.cise.sim.api;

import com.fasterxml.jackson.databind.JsonNode;
import eu.cise.servicemodel.v1.message.Acknowledgement;
import eu.cise.servicemodel.v1.message.Message;
import eu.cise.signature.exceptions.InvalidMessageSignatureEx;
import eu.cise.signature.exceptions.SigningCACertInvalidSignatureEx;
import eu.cise.sim.SynchronousAcknowledgement.SynchronousAcknowledgementFactory;
import eu.cise.sim.SynchronousAcknowledgement.SynchronousAcknowledgementType;
import eu.cise.sim.api.helpers.SendParamsReader;
import eu.cise.sim.engine.MessageProcessor;
import eu.cise.sim.engine.SendParam;
import eu.cise.sim.exceptions.NullSenderEx;
import eu.cise.sim.templates.Template;
import eu.cise.sim.templates.TemplateLoader;
import eu.cise.sim.utils.Pair;
import eu.eucise.xml.XmlMapper;
import eu.eucise.xml.XmlNotParsableException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class DefaultMessageAPI implements MessageAPI {

    private final Logger logger = LoggerFactory.getLogger(MessageAPI.class);

   // private final MessageStorage<Object> messageStorage;
    private final MessageProcessor engineMessageProcessor;
    private final XmlMapper xmlMapper;
    private final XmlMapper prettyNotValidatingXmlMapper;
    private final TemplateLoader templateLoader;
    private final SynchronousAcknowledgementFactory synchronousAcknowledgementFactory = new SynchronousAcknowledgementFactory();

    public DefaultMessageAPI(MessageProcessor engineMessageProcessor,
                             TemplateLoader templateLoader,
                             XmlMapper xmlMapper,
                             XmlMapper prettyNotValidatingXmlMapper) {

        this.engineMessageProcessor = engineMessageProcessor;
        this.xmlMapper = xmlMapper;
        this.prettyNotValidatingXmlMapper = prettyNotValidatingXmlMapper;
        this.templateLoader = templateLoader;
    }

    // todo this should be put outside this domain
    @Override
    public ResponseApi<MessageResponse>  send(String templateId, JsonNode params) {
        logger.debug("send is passed through api templateId: {}, params: {}", templateId, params);

        Template template = templateLoader.loadTemplate(templateId);
        String xmlContent = template.getTemplateContent();

        Message message = xmlMapper.fromXML(xmlContent);
        SendParam sendParam = new SendParamsReader().extractParams(params);
        return send(message, sendParam);
    }

    @Override
    public ResponseApi<MessageResponse>  send(Message message) {

            String messageId = UUID.randomUUID().toString();
            SendParam sendParam = new SendParam(false, messageId, messageId);
            return send(message, sendParam);
    }

    private  ResponseApi<MessageResponse> send(Message message, SendParam sendParam) {

        try {

            Pair<Acknowledgement, Message> sendResponse = engineMessageProcessor.send(message, sendParam);
            return new ResponseApi<>(new MessageResponse(sendResponse.getB(), sendResponse.getA()));

        } catch (Exception e) {
            logger.error("Error sending a message to destination.url", e);
            return new ResponseApi<>(ResponseApi.ErrorId.FATAL, e.getMessage());

        }
    }

    @Override
    public ResponseApi<String> receiveXML(String content) {
        logger.debug("receive is receiving through api : {}", content.substring(0, 200));
        Message message = prettyNotValidatingXmlMapper.fromXML(content);
        ResponseApi<Acknowledgement> acknowledgement = receive(message);
        return new ResponseApi<>(xmlMapper.toXML(acknowledgement.getResult()));
    }

    @Override
    public ResponseApi<Acknowledgement> receive(Message message) {
        logger.debug("receiving message");

        try {

            return new ResponseApi<>(engineMessageProcessor.receive(message));

        } catch (InvalidMessageSignatureEx | SigningCACertInvalidSignatureEx eInvalidSignature) {
            return new ResponseApi<>(synchronousAcknowledgementFactory
                    .buildAck(message, SynchronousAcknowledgementType.INVALID_SIGNATURE,
                            "" + eInvalidSignature.getMessage()));
        } catch (XmlNotParsableException eXmlMalformed) {
            return new ResponseApi<>(synchronousAcknowledgementFactory
                    .buildAck(message, SynchronousAcknowledgementType.XML_MALFORMED,
                            "" + eXmlMalformed.getMessage()));
        } catch (NullSenderEx eSemantic) {
            return new ResponseApi<>(synchronousAcknowledgementFactory
                    .buildAck(message, SynchronousAcknowledgementType.SEMANTIC,
                            "" + eSemantic.getMessage()));
        } catch (Exception eAny) {
            return new ResponseApi<>(synchronousAcknowledgementFactory
                    .buildAck(message, SynchronousAcknowledgementType.INTERNAL_ERROR,
                            "Unknown Error : " + eAny.getMessage()));
        }
    }
}
