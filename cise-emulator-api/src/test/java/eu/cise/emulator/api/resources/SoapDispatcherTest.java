package eu.cise.emulator.api.resources;

import static eu.cise.signature.SignatureServiceBuilder.newSignatureService;
import static eu.eucise.helpers.PushBuilder.newPush;
import static eu.eucise.helpers.ServiceBuilder.newService;
import static org.assertj.core.api.Assertions.assertThat;

import eu.cise.datamodel.v1.entity.Entity;
import eu.cise.datamodel.v1.entity.cargo.Cargo;
import eu.cise.datamodel.v1.entity.incident.Incident;
import eu.cise.datamodel.v1.entity.incident.SeverityType;
import eu.cise.datamodel.v1.entity.location.PortLocation;
import eu.cise.datamodel.v1.entity.vessel.Vessel;
import eu.cise.dispatcher.DispatchResult;
import eu.cise.dispatcher.Dispatcher;
import eu.cise.dispatcher.SoapDispatcher;
import eu.cise.emulator.api.EmulatorApp;
import eu.cise.emulator.api.EmulatorConf;
import eu.cise.servicemodel.v1.message.Acknowledgement;
import eu.cise.servicemodel.v1.message.AcknowledgementType;
import eu.cise.servicemodel.v1.message.InformationSecurityLevelType;
import eu.cise.servicemodel.v1.message.InformationSensitivityType;
import eu.cise.servicemodel.v1.message.Message;
import eu.cise.servicemodel.v1.message.PriorityType;
import eu.cise.servicemodel.v1.message.PurposeType;
import eu.cise.servicemodel.v1.message.Push;
import eu.cise.servicemodel.v1.service.ServiceOperationType;
import eu.cise.servicemodel.v1.service.ServiceType;
import eu.cise.signature.SignatureService;
import io.dropwizard.testing.junit.DropwizardAppRule;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Test;


public class SoapDispatcherTest {

    @ClassRule
    public static final DropwizardAppRule<EmulatorConf> DROPWIZARD = new DropwizardAppRule<>(
        EmulatorApp.class, new EmulatorConf());

    public String soapEndpointDestination;
    public Dispatcher soapDispatcher;
    public SignatureService signatureService;

    private static SignatureService makeSignatureService() {
        URL systemResource = ClassLoader.getSystemResource("");
        System.setProperty("conf.dir", systemResource.getPath());
        return newSignatureService()
            .withKeyStoreName("cisesim-nodeex.jks")
            .withKeyStorePassword("cisesim")
            .withPrivateKeyAlias("cisesim-nodeex.nodeex.eucise.ex")
            .withPrivateKeyPassword("cisesim")
            .build();
    }

    @Before
    public void before() {
        soapEndpointDestination =
            "http://localhost:" + DROPWIZARD.getLocalPort() + "/api/soap/messages";
        soapDispatcher = new SoapDispatcher();
        signatureService = makeSignatureService();
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

    @Ignore
    @Test
    public void it_sends_the_message_Push_Events_to_the_specified_address_with_success() {

        Incident incident = new Incident();
        incident.setInfectionOnBoard(true);
        incident.setSeverity(SeverityType.NON_SPECIFIED);

        Message msg = buildMessagePush(incident);

        Message signedMessage = signatureService.sign(msg);

        DispatchResult sendResult = soapDispatcher.send(signedMessage, soapEndpointDestination);
        Acknowledgement ack = sendResult.getResult();

        assertThat(ack.getAckCode()).isEqualTo(AcknowledgementType.SUCCESS);
    }


    private Message buildMessage() {
        Vessel vessel = new Vessel();
        vessel.setIMONumber(123L);
        vessel.setCallSign("callSign");
        Cargo cargo = new Cargo();
        List<Entity> entities = new ArrayList<>();
        entities.add(vessel);
        entities.add(cargo);

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

}
