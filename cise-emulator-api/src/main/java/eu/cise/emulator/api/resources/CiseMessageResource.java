package eu.cise.emulator.api.resources;

import eu.cise.emulator.api.MessageAPI;
import eu.cise.emulator.api.MessageApiDto;
import eu.cise.io.MessageStorage;
import eu.cise.servicemodel.v1.message.Acknowledgement;
import eu.eucise.xml.DefaultXmlMapper;
import eu.eucise.xml.XmlMapper;
import org.slf4j.LoggerFactory;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

public class CiseMessageResource {
    public static final String ERROR = "ERROR";
    public static final String ACK = "ACK";
    public static final String MESSAGE_MODAL = "RECEIVE";
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(CiseMessageResource.class);
    private final MessageAPI messageAPI;
    private final MessageStorage messageStorage;

    private String fileNameTemplate;
    private XmlMapper xmlMapper = new DefaultXmlMapper();


    public CiseMessageResource(MessageAPI messageAPI, MessageStorage messageStorage) {
        this.messageAPI = messageAPI;
        this.messageStorage = messageStorage;
    }

    @POST
    @Consumes("text/plain,text/xml,application/xml")
    @Produces("text/xml")
    @Path("/api/cisemessage")
    public Response receive(String inputXmlMessage) {
        Acknowledgement acknowledgement = messageAPI.receive(inputXmlMessage);

        // store the input message and the acknowledgement
        XmlMapper xmlMapper = new DefaultXmlMapper.NotValidating();
        String acknowledgementXml = xmlMapper.toXML(acknowledgement);
        MessageApiDto messageApiDto = new MessageApiDto(Response.Status.CREATED.getStatusCode(),
                "",
                acknowledgementXml,
                inputXmlMessage);

        messageStorage.store(messageApiDto);

        return Response
                .status(Response.Status.CREATED)
                .entity(acknowledgement)
                .build();
    }
}
