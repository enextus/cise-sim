package eu.cise.dispatcher;

import eu.cise.datamodel.v1.entity.Entity;
import eu.cise.datamodel.v1.entity.cargo.Cargo;
import eu.cise.datamodel.v1.entity.vessel.Vessel;
import eu.cise.dispatcher.DispatchResult;
import eu.cise.dispatcher.Dispatcher;
import eu.cise.dispatcher.SoapDispatcher;
import eu.cise.servicemodel.v1.message.*;
import eu.cise.servicemodel.v1.service.ServiceOperationType;
import eu.cise.servicemodel.v1.service.ServiceType;
import eu.cise.signature.SignatureService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static eu.cise.signature.SignatureServiceBuilder.newSignatureService;
import static eu.eucise.helpers.PushBuilder.newPush;
import static eu.eucise.helpers.ServiceBuilder.newService;

import static org.assertj.core.api.Assertions.assertThat;


public class SoapDispatcherTest {

    @Before
    public void before() {
    }

    @After
    public void after() {
    }

    @Test
    public void it_sends_the_message_to_the_specified_address_with_success() {
        Dispatcher soapDispatcher = new SoapDispatcher();

        Message msg = buildMessage();
        SignatureService signatureService = makeSignatureService();
        Message signedMessage = signatureService.sign(msg);

        String soapEndpointDestination = "http://192.168.42.37:8180/eucise-com-services-web/CISEMessageService?wsdl";

        DispatchResult sendResult = soapDispatcher.send(signedMessage, soapEndpointDestination);
        Acknowledgement ack = sendResult.getResult();
        System.out.println(ack);

        assertThat(ack.getAckCode()).isEqualTo(AcknowledgementType.SUCCESS);
    }

    private Message buildMessage(){
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
                .sender(newService().id("cx.cisesim1-nodecx.vessel.push.provider").operation(ServiceOperationType.PUSH).type(ServiceType.VESSEL_SERVICE).build())
                .recipient(newService().id("cx.cisesim2-nodecx.vessel.push.consumer").operation(ServiceOperationType.PUSH).build())
                .addEntities(entities)
                .build();
        return pushMessage;
    }

    public static SignatureService makeSignatureService() {
        System.setProperty("conf.dir", "/home/longama/IdeaProjects/cise-emu/cise-dispatcher/src/main/resources/");
        return newSignatureService()
                .withKeyStoreName("cisesim1-nodecx.jks")
                .withKeyStorePassword("password")
                .withPrivateKeyAlias("cisesim1-nodecx.nodecx.eucise.cx")
                .withPrivateKeyPassword("password")
                .build();
    }

}
