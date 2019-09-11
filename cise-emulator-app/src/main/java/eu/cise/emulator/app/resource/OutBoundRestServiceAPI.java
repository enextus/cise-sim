package eu.cise.emulator.app.resource;

import ch.qos.logback.classic.Logger;
import com.codahale.metrics.annotation.Metric;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.cise.emulator.app.context.SimConfig;
import eu.cise.emulator.app.core.InstanceID;
import eu.cise.emulator.app.core.Member;
import eu.cise.emulator.app.core.XmlFileReference;
import eu.cise.emulator.app.transport.JerseyRestClient;
import eu.cise.emulator.app.transport.RestClient;
import eu.cise.emulator.app.transport.RestResult;
import eu.cise.emulator.integration.Validation.MessageValidator;
import eu.cise.servicemodel.v1.message.Acknowledgement;
import eu.cise.servicemodel.v1.message.Message;
import eu.cise.servicemodel.v1.message.XmlEntityPayload;
import eu.cise.signature.SignatureService;
import eu.cise.signature.SignatureServiceBuilder;
import eu.eucise.xml.DefaultXmlMapper;
import eu.eucise.xml.XmlMapper;
import eu.eucise.xml.XmlNotParsableException;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.stream.Collectors;

import static java.time.Instant.now;


@Path("webapi")
public class OutBoundRestServiceAPI {
    private final ObjectMapper mapper = new ObjectMapper();
    private final Logger logger = (Logger) LoggerFactory.getLogger(OutBoundRestServiceAPI.class.getName());
    private final MessageValidator validator;
    private InstanceID instanceID;
    private SimConfig config;

    public OutBoundRestServiceAPI(InstanceID instanceID, SimConfig config) {
        this.validator = new MessageValidator();
        this.instanceID = instanceID;
        this.config = config;
    }


    @GET
    @Metric
    @Produces("application/json")
    @Path("/member/{ref}")
    public String getReturnMemberId(@PathParam("ref") String ref) throws Exception {
        Member activeMember = new Member();
        String messageJson = "#Undefined#";
        if (ref.equals("0")) {
            activeMember.setId("" + instanceID.getNumId());
            activeMember.setName(instanceID.getName() + "@" + instanceID.getVersion());
            try {
                messageJson = getMapper().writeValueAsString(activeMember);
            } catch (JsonProcessingException ex) {
                getLogger().error("json exception: " + ex.getMessage());
            }
        } else {
            logger.debug("member/1 as destination is not implemented yet");
        }
        return (messageJson);
    }


    @GET
    @Produces("application/json")
    @Path("/template")
    public /*Map<Integer,String>*/String getListTemplate() throws Exception {
        List<XmlFileReference> activeListFileReference = new ArrayList<XmlFileReference>();
        SourceBufferFileSource aSourceBufferutil = new SourceBufferFileSource();
        List<File> activelistFile = aSourceBufferutil.getReferenceDirectoryListing(config.getSimulatorMessageDir());
        activelistFile.forEach(s -> {
            activeListFileReference.add(new XmlFileReference(s.getName(), s.getAbsolutePath()));
        });
        String messageJson = "{}";
        try {
            messageJson = getMapper().writeValueAsString(activeListFileReference);
        } catch (JsonProcessingException ex) {
            getLogger().error("json exception: " + ex.getMessage());
        }
        return (messageJson);
    }

    @GET
    @Produces("application/json")
    @Path("/payload")
    public /*Map<Integer,String>*/String getListPayload() throws Exception {
        List<XmlFileReference> activeListFileReference = new ArrayList<XmlFileReference>();
        SourceBufferFileSource aSourceBufferutil = new SourceBufferFileSource();
        List<File> activelistFile = aSourceBufferutil.getReferenceDirectoryListing(config.getSimulatorPayloadDir());
        activelistFile.forEach(s -> {
            activeListFileReference.add(new XmlFileReference(s.getName(), s.getAbsolutePath()));
        });
        String messageJson = "{}";
        try {
            messageJson = getMapper().writeValueAsString(activeListFileReference);
        } catch (JsonProcessingException ex) {
            getLogger().error("json exception: " + ex.getMessage());
        }
        return (messageJson);
    }

    @GET
    @Produces("application/json")
    @Path("/preview/{service}{payload: (/payload)?}")
    public JsonNode getPreview(
            @PathParam("service") String service,
            @DefaultValue("") @QueryParam("payload") String payload,
            @DefaultValue("") @QueryParam("message_id") String messageId,
            @DefaultValue("false") @QueryParam("request_async_ack") boolean requestAsyncAck,
            @DefaultValue("") @QueryParam("consolidation_id") String consolidationId) {

        List<File> singleTemplateListResult = extractvalue(service, config.getSimulatorMessageDir());
        List<File> singlePayloadListResult = extractvalue(payload, config.getSimulatorPayloadDir());
        MessageReturn messageReturn = new MessageReturn(config.getSimulatorId());
        String phase = "init phase (upload files content)";
        // produce adecuate JSON content structure {status:, body:} // actually flat structuree no object related but FUTUR need if should include sub notice:{line:,message:}}
        if (singleTemplateListResult.size() == 0) {
            return (messageReturn.build("an error occured in " + phase +
                            " ; in more detail ... \n " + "could not find file with path hash = " + service + "\n message(" + messageId + "::service:" + service + "/payload:" + payload + "/ack:" + requestAsyncAck + "/consolidationId:" + consolidationId + ")"
                    , "", ""));
        }

        XmlMapper mapper = new DefaultXmlMapper.Pretty();
        eu.cise.servicemodel.v1.message.Message loadedMessage = null;
        String refMessageReturned = "";
        phase = "init phase (upload files content)";
        try {
            if (!(payload.equals("")) && singlePayloadListResult.size() > 0) {
                loadedMessage = loadMessageBothFile(singleTemplateListResult.get(0).getAbsolutePath(), singlePayloadListResult.get(0).getAbsolutePath());
                getLogger().warn("not implemented yet : will only take into account the payload");
            } else {
                loadedMessage = loadMessageWithOnlyServiceFile(singleTemplateListResult.get(0).getAbsolutePath());
            }
            phase = "sending phase ( update metadata)";
            loadedMessage = conformMessage(loadedMessage, messageId, consolidationId, requestAsyncAck);
            phase = "sending phase ( sign content)";
            loadedMessage = finaliseMessage(mapper, loadedMessage);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return (messageReturn.build("an error occured in " + phase +
                            " ; in more detail ... \n " + e.toString() +
                            "\n message(" + messageId + "::service:" + service + "/payload:" + payload + "/ack:" + requestAsyncAck + "/consolidationId:" + consolidationId + ")"
                    , "", (loadedMessage != null ? mapper.toXML(loadedMessage) : "")));
        }
        return (messageReturn.build("", "", mapper.toXML(loadedMessage)));
    }


    //send/-1330230982?payload=358277851&request_async_ack=false&consolidation_id=test
    @GET
    @Produces("application/json")
    @Path("/send/{service}{payload: (/payload)?}")
    public /*Map<Integer,String>*/JsonNode send(
            @PathParam("service") String service,
            @DefaultValue("") @QueryParam("payload") String payload,
            @DefaultValue("") @QueryParam("message_id") String messageId,
            @DefaultValue("false") @QueryParam("request_async_ack") boolean requestAsyncAck,
            @DefaultValue("") @QueryParam("consolidation_id") String consolidationId) {

        List<File> singleTemplateListResult = extractvalue(service, config.getSimulatorMessageDir());
        List<File> singlePayloadListResult = extractvalue(payload, config.getSimulatorPayloadDir());
        MessageReturn messageReturn = new MessageReturn(config.getSimulatorId());
        String phase = "init phase (upload files content)";
        // produce adecuate JSON content structure {status:, body:} // actually flat structuree no object related but FUTUR need if should include sub notice:{line:,message:}}
        if (singleTemplateListResult.size() == 0) {
            return (messageReturn.build("an error occured in " + phase +
                            " ; in more detail ... \n " + "could not find file with path hash = " + service + "\n message(" + messageId + "::service:" + service + "/payload:" + payload + "/ack:" + requestAsyncAck + "/consolidationId:" + consolidationId + ")"
                    , "", ""));
        }

        XmlMapper mapper = new DefaultXmlMapper.Pretty();
        eu.cise.servicemodel.v1.message.Message loadedMessage = null;
        String refMessageReturned = "";
        phase = "init phase (upload files content)";
        try {
            if (!(payload.equals("")) && singlePayloadListResult.size() > 0) {
                loadedMessage = loadMessageBothFile(singleTemplateListResult.get(0).getAbsolutePath(), singlePayloadListResult.get(0).getAbsolutePath());
                getLogger().warn("not implemented yet : will only take into account the payload");
            } else {

                phase = "init phase (merge files content)";
                loadedMessage = loadMessageWithOnlyServiceFile(singleTemplateListResult.get(0).getAbsolutePath());
            }
            phase = "sending phase ( merged content)";
            //loadedMessage= conformMessage(loadedMessage, message_id, consolidation_id, request_async_ack);
            phase = "sending phase ( sign content)";
            loadedMessage = finaliseMessage(mapper, loadedMessage);
            phase = "sending phase ( envoice content)";
            refMessageReturned = sendMessage(loadedMessage);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return (messageReturn.build("an error occured in " + phase +
                            " ; in more detail ... \n " + e.toString() +
                            "\n message(" + messageId + "::service:" + service + "/payload:" + payload + "/ack:" + requestAsyncAck + "/consolidationId:" + consolidationId + ")"
                    , "", (loadedMessage != null ? mapper.toXML(loadedMessage) : "")));
        }
        return (messageReturn.build("", refMessageReturned, mapper.toXML(loadedMessage)));
    }


    private Message conformMessage(Message loadedMessage, String messageId, String consolidationId, boolean requestAsyncAck) {

        loadedMessage.setCorrelationID(consolidationId);
        loadedMessage.setMessageID(messageId);
        loadedMessage.setRequiresAck(requestAsyncAck);
        try {
            GregorianCalendar thisMomentGC;
            thisMomentGC = new GregorianCalendar();
            thisMomentGC.setTime(Date.from(now()));
            loadedMessage.setCreationDateTime(DatatypeFactory.newInstance().newXMLGregorianCalendar(thisMomentGC));
        } catch (DatatypeConfigurationException e) {
            e.printStackTrace();
        }
        return loadedMessage;
    }

    public String sendMessage(eu.cise.servicemodel.v1.message.Message refMessage) {

        RestClient client = new JerseyRestClient();
        XmlMapper refmapper = new DefaultXmlMapper.Pretty();
        String refMessageString = refmapper.<Message>toXML(refMessage);
        Acknowledgement thisAcknoledgment = new Acknowledgement();
        RestResult result = new RestResult(200, "#noEnvoice", "#noEnvoice");
        try {
            result = client.post(config.getCounterpartRestUrl(), refMessageString);
            // construct the acknoledgmente from the rest resulting.
        } catch (Exception e) {
            logger.debug("error on calling client : " + e.getMessage());
        }
        System.out.println("Client send to Server : " + refMessage);

        return (result.getBody());
        // TODO: post on websocket ({messageType:... , timeSent:... , timeSent:...

    }


    private List<File> extractvalue(String expectedHashString, String directory) {
        SourceBufferFileSource aSourceBufferutil = new SourceBufferFileSource();
        List<File> activeFileList = aSourceBufferutil.getReferenceDirectoryListing(directory);
        List<File> singleListResult = activeFileList.stream()
                .filter(file1 -> expectedHashString.equals(Integer.toString(XmlFileReference.getCalculedHash(((File) file1).getAbsolutePath()))))
                .collect(Collectors.toList());
        return (singleListResult);
    }


    private eu.cise.servicemodel.v1.message.Message loadMessageWithOnlyServiceFile(String servicefile) throws FileNotFoundException, XmlNotParsableException, UnknownError {

        SourceBufferInterface sourceBuffer = new SourceBufferFileSource();
        StringBuffer serviceBuffer = sourceBuffer.getReferenceFileContent(servicefile);
        XmlMapper mapper = new DefaultXmlMapper.Pretty();
        eu.cise.servicemodel.v1.message.Message tempMessageObject = mapper.fromXML(serviceBuffer.toString());


        return tempMessageObject;
    }


    private eu.cise.servicemodel.v1.message.Message loadMessageBothFile(String servicefile, String payloadfile) throws FileNotFoundException, XmlNotParsableException, UnknownError {

        SourceBufferInterface sourceBuffer = new SourceBufferFileSource();
        StringBuffer serviceBuffer = sourceBuffer.getReferenceFileContent(servicefile);


        XmlMapper mapper = new DefaultXmlMapper.Pretty();

        eu.cise.servicemodel.v1.message.Message tempMessageObject = mapper.fromXML(serviceBuffer.toString());
        tempMessageObject.setPayload(new XmlEntityPayload());
        XmlMapper mapper2 = new DefaultXmlMapper.Pretty();

        if (!("".equals(payloadfile))) {
            StringBuffer payloadBuffer = sourceBuffer.getReferenceFileContent(payloadfile);
            if (!(payloadBuffer.toString().equals(""))) {
                //XmlEntityPayload sourcePayload = new XmlEntityPayload();
                eu.cise.servicemodel.v1.message.Message resultMapper = mapper.fromXML(payloadBuffer.toString());
                //XmlEntityPayload res= (XmlEntityPayload)resultMapper;

                List<Object> res = ((XmlEntityPayload) resultMapper.getPayload()).getAnies();
                ((XmlEntityPayload) tempMessageObject.getPayload()).getAnies().add(res);
            }
        }

        return tempMessageObject;
    }

    private Message finaliseMessage(XmlMapper mapper, Message tempMessageObject) throws XmlNotParsableException, UnknownError {
        Message finalMessageObject = null;
        String finalMessage = "";
        if (config.isSignatureMessageSend().contains("true")) {
            System.out.println("before signed  : " + mapper.<Message>toXML(tempMessageObject));
            SignatureServiceBuilder signBuilder = SignatureServiceBuilder.newSignatureService(mapper);
            String resolvedConfDir = (System.getProperty("user.dir") + "/conf/");
            /*wa01:in >  work around until modification of eventual change in signature lib null ="" */
            String oldConfDir = System.getProperty("conf.dir"); /*wa01:*/
            String actualUserDir = System.getProperty("user.dir");
            SignatureService signature = signBuilder
                    .withKeyStoreName((String) config.getKeyStoreName())
                    .withKeyStorePassword((String) config.getKeyStorePassword())
                    .withPrivateKeyAlias((String) config.getPrivateKeyAlias())
                    .withPrivateKeyPassword((String) config.getPrivateKeyPassword())
                    .build();

            System.setProperty("conf.dir", resolvedConfDir); /*wa01:*/
            /*< out:wa01*/

            finalMessageObject = signature.sign(tempMessageObject);
        } else {
            return tempMessageObject;
        }
        if (finalMessageObject != null) return finalMessageObject;
        else throw new UnknownError();
    }

    public ObjectMapper getMapper() {
        return mapper;
    }

    public Logger getLogger() {
        return logger;
    }

    private class MessageReturn {
        final String source;
        final ObjectMapper jsonmapper = new ObjectMapper();
        final ArrayNode jsonmapperarrayNode = jsonmapper.createArrayNode();

        private MessageReturn(String source) {
            this.source = source;
        }

        public ArrayNode build(String refError, String refAcknowledge, String refMessageString) {
            ObjectNode objectNode1 = jsonmapper.createObjectNode();
            objectNode1.put("status", refError);
            objectNode1.put("body", refMessageString);
            objectNode1.put("ack", refAcknowledge);
            jsonmapperarrayNode.add(objectNode1);
            return (jsonmapperarrayNode);
        }
    }
}
