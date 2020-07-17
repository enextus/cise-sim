package eu.cise.sim.dropw.rest;
/*
import eu.cise.datamodel.v1.entity.Entity;
import eu.cise.datamodel.v1.entity.incident.Incident;
import eu.cise.datamodel.v1.entity.incident.MaritimeSafetyIncident;
import eu.cise.datamodel.v1.entity.incident.SeverityType;
import eu.cise.datamodel.v1.entity.location.PortLocation;
import eu.cise.datamodel.v1.entity.vessel.Vessel;
import eu.cise.dispatcher.soap.SoapDispatcher;
import eu.cise.servicemodel.v1.message.*;
import eu.cise.servicemodel.v1.service.ServiceOperationType;
import eu.cise.servicemodel.v1.service.ServiceType;
import eu.cise.signature.SignatureService;
import eu.cise.sim.app.SimApp;
import eu.cise.sim.app.SimConf;
import eu.cise.sim.engine.DispatchResult;
import eu.cise.sim.engine.Dispatcher;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static eu.cise.signature.SignatureServiceBuilder.newSignatureService;
import static eu.eucise.helpers.PushBuilder.newPush;
import static eu.eucise.helpers.ServiceBuilder.newService;
import static io.dropwizard.testing.ResourceHelpers.resourceFilePath;
import static org.assertj.core.api.Assertions.assertThat;

*/
public class SoapDispatcherTest {
/* TODO decomment and fix
    @ClassRule
    public static final DropwizardAppRule<SimConf> DROPWIZARD =
        new DropwizardAppRule<>(SimApp.class, resourceFilePath("test-config.yml"));

    private String soapEndpointDestination;
    private Dispatcher soapDispatcher;
    private SignatureService signatureService;

    @Before
    public void before() {
        soapEndpointDestination = getSoapEndpointDestination(DROPWIZARD.getLocalPort());
        soapDispatcher = new SoapDispatcher();
        signatureService = newSignatureService()
            .withKeyStoreName("cisesim-nodeex.jks")
            .withKeyStorePassword("cisesim")
            .withPrivateKeyAlias("cisesim-nodeex.nodeex.eucise.ex")
            .withPrivateKeyPassword("cisesim")
            .build();
    }

    @Test
    public void it_sends_the_message_Push_Vehicles_to_the_specified_address_with_success() {
        Vessel vessel = new Vessel();
        vessel.setIMONumber(123L);
        vessel.setCallSign("callSign");

        Message msg = buildMessagePush(vessel);

        Message signedMessage = signatureService.sign(msg);

        DispatchResult sendResult = soapDispatcher.send(signedMessage, soapEndpointDestination);
        Acknowledgement ack = sendResult.getResult();

        assertThat(ack.getAckCode()).isEqualTo(AcknowledgementType.SUCCESS);
    }


    @Test
    public void it_sends_the_message_Push_Locations_to_the_specified_address_with_success() {

        PortLocation portLocation = new PortLocation();
        portLocation.setPortName("Nantes");

        Message msg = buildMessagePush(portLocation);
        Message signedMessage = signatureService.sign(msg);

        DispatchResult sendResult = soapDispatcher.send(signedMessage, soapEndpointDestination);
        Acknowledgement ack = sendResult.getResult();

        assertThat(ack.getAckCode()).isEqualTo(AcknowledgementType.SUCCESS);
    }

    @Test
    public void it_sends_the_message_Push_Events_to_the_specified_address_with_success() {

        Incident incident = new MaritimeSafetyIncident();
        incident.setInfectionOnBoard(true);
        incident.setSeverity(SeverityType.NON_SPECIFIED);

        Message msg = buildMessagePush(incident);
        Message signedMessage = signatureService.sign(msg);

        DispatchResult sendResult = soapDispatcher.send(signedMessage, soapEndpointDestination);
        Acknowledgement ack = sendResult.getResult();

        assertThat(ack.getAckCode()).isEqualTo(AcknowledgementType.SUCCESS);
    }

    private String getSoapEndpointDestination(int localPort) {
        return "http://localhost:" + localPort + "/api/soap/messages";
    }

    private Message buildMessagePush(Entity entity) {

        List<Entity> entities = new ArrayList<>();
        entities.add(entity);

        Push pushMessage = newPush()
            .id("messageId")
            .correlationId("correlation-id")
            .creationDateTime(new Date())
            .priority(PriorityType.HIGH)
            .isRequiresAck(true)
            .informationSecurityLevel(InformationSecurityLevelType.NON_CLASSIFIED)
            .informationSensitivity(InformationSensitivityType.GREEN)
            .setEncryptedPayload("false")
            .isPersonalData(false)
            .purpose(PurposeType.NON_SPECIFIED)
            .sender(newService().id("cx.cisesim1-nodecx.vessel.push.provider")
                .operation(ServiceOperationType.PUSH).type(ServiceType.VESSEL_SERVICE).build())
            .recipient(newService().id("cx.cisesim2-nodecx.vessel.push.consumer")
                .operation(ServiceOperationType.PUSH).build())
            .addEntities(entities)
            .build();
        return pushMessage;
    }
*/
}
