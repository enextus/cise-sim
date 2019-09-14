package eu.cise.emulator.app.resource;

import ch.qos.logback.classic.Logger;
import com.codahale.metrics.annotation.Metric;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.cise.emulator.EmulatorEngine;
import eu.cise.emulator.SendParam;
import eu.cise.servicemodel.v1.message.Message;
import eu.cise.servicemodel.v1.message.Push;
import eu.eucise.xml.XmlMapper;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

//import eu.cise.emulator.api.XmlFileReference;TODO-GK modified by GK to make it compile


@Path("webapi")
public class Outbound {
    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(Outbound.class.getName());


    private DelegateFileAccess deleguate;
    private EmulatorEngine emulator;
    private ObjectMapper jsonMapper;
    private XmlMapper xmlMapper;

    public Outbound(DelegateFileAccess deleguate, EmulatorEngine emulator, XmlMapper xmlMapper) {
        this.deleguate = deleguate;
        this.emulator = emulator;
        this.xmlMapper = xmlMapper;
        this.jsonMapper = new ObjectMapper();
    }


    @GET
    @Metric
    @Produces("application/json")
    @Path("/member/{ref}")
    public String getReturnParticipantName(@PathParam("ref") String ref) throws Exception {
        return (new Participant("").build("it.sim.egn", "REST")).toString();
    }


    @GET
    @Produces("application/json")
    @Path("/template")
    public String getListTemplate() throws Exception {
        // TODO-GK modified by GK to make it compile

/*
        List<File> activelistFile = deleguate.getListOfTemplates();
        List<XmlFileReference> activeListFileReference = new ArrayList<>();
        activelistFile.forEach(s -> {
            activeListFileReference.add(new XmlFileReference(s.getName(), s.getAbsolutePath()));
        });
        String messageJson = "{}";
        try {
            messageJson = jsonMapper.writeValueAsString(activeListFileReference);
        } catch (JsonProcessingException ex) {
            LOGGER.error("json exception: " + ex.getMessage());
        }
        return (messageJson);
*/
        return "[]";
    }

    @GET
    @Produces("application/json")
    @Path("/payload")
    public String getListPayload() throws Exception {
        // TODO-GK modified by GK to make it compile
/*

        List<File> activelistFile = deleguate.getListOfPayloads();
        List<XmlFileReference> activeListFileReference = new ArrayList<XmlFileReference>();
        activelistFile.forEach(s -> {
            activeListFileReference.add(new XmlFileReference(s.getName(), s.getAbsolutePath()));
        });
        String messageJson = "{}";
        try {
            messageJson = jsonMapper.writeValueAsString(activeListFileReference);
        } catch (JsonProcessingException ex) {
            LOGGER.error("json exception: " + ex.getMessage());
        }
        return (messageJson);
*/
        return "{}";
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

        Message loadedMessage = loadMessage(service, payload);
        SendParam sendParam = new SendParam(requestAsyncAck, messageId, consolidationId);
        Message result = emulator.prepare(loadedMessage, sendParam);
        return (new MessageReturn("").build("", xmlMapper.toXML(result), ""));
    }


    //send/-1330230982?payload=358277851&request_async_ack=false&consolidation_id=test
    @GET
    @Produces("application/json")
    @Path("/send/{service}{payload: (/payload)?}")
    public JsonNode send(
            @PathParam("service") String service,
            @DefaultValue("") @QueryParam("payload") String payload,
            @DefaultValue("") @QueryParam("message_id") String messageId,
            @DefaultValue("false") @QueryParam("request_async_ack") boolean requestAsyncAck,
            @DefaultValue("") @QueryParam("consolidation_id") String consolidationId) {

        Message loadedMessage = loadMessage(service, payload);
        SendParam sendParam = new SendParam(requestAsyncAck, messageId, consolidationId);
        Message preparedMessage = emulator.prepare(loadedMessage, sendParam);
        // TODO-GK modified by GK to make it compile
        Message result = emulator.send(preparedMessage, sendParam);
        return (new MessageReturn("").build("", xmlMapper.toXML(preparedMessage), xmlMapper.toXML(result)));
    }

    /*!--inner class to distribute --*/
    public Message loadMessage(String refTemplate, String refPayload) {
        return new Push();
    }

    /*!--inner class to distribute --*/
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

    private class Participant {
        final String source;
        final ObjectMapper jsonmapper = new ObjectMapper();
        final ArrayNode jsonmapperarrayNode = jsonmapper.createArrayNode();

        private Participant(String source) {
            this.source = source;
        }

        public ArrayNode build(String participantId, String mode) {
            ObjectNode objectNode1 = jsonmapper.createObjectNode();
            objectNode1.put("participantId", participantId);
            objectNode1.put("mode", mode);
            jsonmapperarrayNode.add(objectNode1);
            return (jsonmapperarrayNode);
        }
    }

    private class DelegateFileAccess {

        private List<File> getListOfTemplates() {
            return new ArrayList<File>();
        }

        private List<File> getListOfPayloads() {
            return new ArrayList<File>();
        }

    }
}
