package eu.cise.emulator.api.resources;

import eu.cise.emulator.api.CiseMessageResponse;
import eu.cise.emulator.api.MessageAPI;
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

    private String fileNameTemplate;
    private XmlMapper xmlMapper = new DefaultXmlMapper();


    public CiseMessageResource(MessageAPI messageAPI) {
        this.messageAPI = messageAPI;
    }

    @POST
    @Consumes("text/plain,text/xml,application/xml")
    @Produces("text/xml")
    @Path("/api/cisemessage")
    public Response receive(String inputXmlMessage) throws Exception {
        CiseMessageResponse receivedCiseMessage = messageAPI.receive(inputXmlMessage);
        //String createdreffile = InteractIOFile.createRelativeRef(fileNameTemplate, filePost, createdFile, MESSAGE_MODAL, new StringBuffer(ackMessage));
        Response.Status status = (receivedCiseMessage.isUnmarshallableMessage() ?
                Response.Status.EXPECTATION_FAILED
                : Response.Status.ACCEPTED);

        return Response
                .status(status)
                .entity(receivedCiseMessage.getAcknowledgementContent())
                .build();
    }
}
