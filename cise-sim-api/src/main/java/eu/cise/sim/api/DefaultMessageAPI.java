package eu.cise.sim.api;

import com.fasterxml.jackson.databind.JsonNode;
import eu.cise.emulator.MessageProcessor;
import eu.cise.emulator.SendParam;
import eu.cise.emulator.SynchronousAcknowledgement.SynchronousAcknowledgementFactory;
import eu.cise.emulator.SynchronousAcknowledgement.SynchronousAcknowledgementType;
import eu.cise.sim.api.helpers.SendParamsReader;
import eu.cise.emulator.exceptions.NullSenderEx;
import eu.cise.emulator.io.MessageStorage;
import eu.cise.emulator.templates.Template;
import eu.cise.emulator.templates.TemplateLoader;
import eu.cise.emulator.utils.Pair;
import eu.cise.servicemodel.v1.message.Acknowledgement;
import eu.cise.servicemodel.v1.message.Message;
import eu.cise.servicemodel.v1.message.Push;
import eu.cise.signature.exceptions.InvalidMessageSignatureEx;
import eu.cise.signature.exceptions.SigningCACertInvalidSignatureEx;
import eu.eucise.xml.XmlMapper;
import eu.eucise.xml.XmlNotParsableException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultMessageAPI implements MessageAPI {

    private final Logger logger = LoggerFactory.getLogger(MessageAPI.class);

    private final MessageStorage messageStorage;
    private final MessageProcessor messageProcessor;
    private final XmlMapper xmlMapper;
    private final XmlMapper prettyNotValidatingXmlMapper;
    private final TemplateLoader templateLoader;
    private final SynchronousAcknowledgementFactory synchronousAcknowledgementFactory = new SynchronousAcknowledgementFactory();

    DefaultMessageAPI(MessageProcessor messageProcessor,
        MessageStorage messageStorage,
        TemplateLoader templateLoader,
        XmlMapper xmlMapper,
        XmlMapper prettyNotValidatingXmlMapper) {

        this.messageProcessor = messageProcessor;
        this.messageStorage = messageStorage;
        this.xmlMapper = xmlMapper;
        this.prettyNotValidatingXmlMapper = prettyNotValidatingXmlMapper;
        this.templateLoader = templateLoader;
    }

    @Override
    public SendResponse send(String templateId, JsonNode params) {
        logger.debug("send is passed through api templateId: {}, params: {}", templateId, params);

        try {
            Template template = templateLoader.loadTemplate(templateId);
            String xmlContent = template.getTemplateContent();
            SendParam sendParam = new SendParamsReader().extractParams(params);
            Message message = xmlMapper.fromXML(xmlContent);
            Pair<Acknowledgement, Message> sendResponse = messageProcessor.send(message, sendParam);

            return new SendResponse.OK(
                new MessageApiDto(
                    prettyNotValidatingXmlMapper.toXML(sendResponse.getA()),
                    prettyNotValidatingXmlMapper.toXML(sendResponse.getB())));

        } catch (Exception e) {
            logger.error("Error sending a message to destination.url", e);
            return new SendResponse.KO(e.getMessage());
        }
    }

    @Override
    public Acknowledgement receive(String content) {
        logger.debug("receive is receiving through api : {}", content.substring(0, 200));
        Message message = new Push();
        try {
            message = prettyNotValidatingXmlMapper.fromXML(content);

            // store the input message and the acknowledgement
            Acknowledgement acknowledgement = messageProcessor.receive(message);

            String acknowledgementXml = prettyNotValidatingXmlMapper.toXML(acknowledgement);
            String messageXml = prettyNotValidatingXmlMapper.toXML(message);

            MessageApiDto messageApiDto = new MessageApiDto(acknowledgementXml, messageXml);

            messageStorage.store(messageApiDto);

            return acknowledgement;


        } catch (InvalidMessageSignatureEx | SigningCACertInvalidSignatureEx eInvalidSignature) {
            return synchronousAcknowledgementFactory
                .buildAck(message, SynchronousAcknowledgementType.INVALID_SIGNATURE,
                    "" + eInvalidSignature.getMessage());
        } catch (XmlNotParsableException eXmlMalformed) {
            return synchronousAcknowledgementFactory
                .buildAck(message, SynchronousAcknowledgementType.XML_MALFORMED,
                    "" + eXmlMalformed.getMessage());
        } catch (NullSenderEx eSemantic) {
            return synchronousAcknowledgementFactory
                .buildAck(message, SynchronousAcknowledgementType.SEMANTIC,
                    "" + eSemantic.getMessage());
        } catch (Exception eAny) {
            return synchronousAcknowledgementFactory
                .buildAck(message, SynchronousAcknowledgementType.INTERNAL_ERROR,
                    "Unknown Error : " + eAny.getMessage());
        }


    }

    @Override
    public MessageApiDto getLastStoredMessage() {
        return (MessageApiDto) messageStorage.read();
    }

    public boolean consumeStoredMessage(MessageApiDto storedMessage) {
        //TODO: read the template from fileStorage
        return messageStorage.delete(storedMessage);
    }
}
