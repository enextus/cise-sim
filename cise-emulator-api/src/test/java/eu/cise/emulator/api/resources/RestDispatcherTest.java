package eu.cise.emulator.api.resources;

import eu.cise.datamodel.v1.entity.Entity;
import eu.cise.datamodel.v1.entity.cargo.Cargo;
import eu.cise.datamodel.v1.entity.vessel.Vessel;
import eu.cise.dispatcher.DispatchResult;
import eu.cise.dispatcher.Dispatcher;
import eu.cise.dispatcher.RestDispatcher;
import eu.cise.emulator.api.EmulatorApp;
import eu.cise.emulator.api.EmulatorConf;
import eu.cise.servicemodel.v1.message.*;
import eu.cise.servicemodel.v1.service.ServiceOperationType;
import eu.cise.servicemodel.v1.service.ServiceType;
import eu.cise.signature.SignatureService;
import eu.eucise.xml.DefaultXmlMapper;
import eu.eucise.xml.XmlMapper;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static eu.cise.signature.SignatureServiceBuilder.newSignatureService;
import static eu.eucise.helpers.PushBuilder.newPush;
import static eu.eucise.helpers.ServiceBuilder.newService;
import static org.assertj.core.api.Assertions.assertThat;


public class RestDispatcherTest {

    @ClassRule
    public static final DropwizardAppRule<EmulatorConf> DROPWIZARD = new DropwizardAppRule<>(EmulatorApp.class, new EmulatorConf());

    public String restEndpointDestination;
    public Dispatcher restDispacher;
    public SignatureService signatureService;
    public XmlMapper xmlmapperNoValidNoPretty= new DefaultXmlMapper.NotValidating();
    public XmlMapper xmlmapperNoValidPretty= new DefaultXmlMapper.PrettyNotValidating();
    @Before
    public void before() {
        restEndpointDestination = "http://localhost:" + DROPWIZARD.getLocalPort() + "/api/messages";
        signatureService = makeSignatureService();
    }

    public static SignatureService makeSignatureService() {
        URL systemResource = ClassLoader.getSystemResource("");
        System.setProperty("conf.dir", systemResource.getPath());
        return newSignatureService()
                .withKeyStoreName("cisesim-nodeex.jks")
                .withKeyStorePassword("cisesim")
                .withPrivateKeyAlias("cisesim-nodeex.nodeex.eucise.ex")
                .withPrivateKeyPassword("cisesim")
                .build();
    }

    // correlation shown from node in Dispacher - Signature definition
    // where P= pretty V=Valid p=nonpretty or v=nonvalid
    // signature.fail:Pv-Pv,Pv-pv,pv-Pv
    // sax.fail: PV-PV,pV-pV
    // success:pv-pv

    @Test
    public void it_sends_the_signed_rest_message_with_success() {

        Vessel vessel = new Vessel();
        vessel.setIMONumber(123L);
        vessel.setCallSign("callSign");

        Message msg = buildMessagePush(vessel);

        Message signedMessage = signatureService.sign(msg);
        restDispacher = new RestDispatcher(xmlmapperNoValidPretty);
        DispatchResult sendResult = restDispacher.send(signedMessage, restEndpointDestination);
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
                .sender(newService().id("cx.cisesim1-nodecx.vessel.push.provider").operation(ServiceOperationType.PUSH).type(ServiceType.VESSEL_SERVICE).build())
                .recipient(newService().id("cx.cisesim2-nodecx.vessel.push.consumer").operation(ServiceOperationType.PUSH).build())
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
                .sender(newService().id("cx.cisesim1-nodecx.vessel.push.provider").operation(ServiceOperationType.PUSH).type(ServiceType.VESSEL_SERVICE).build())
                .recipient(newService().id("cx.cisesim2-nodecx.vessel.push.consumer").operation(ServiceOperationType.PUSH).build())
                .addEntities(entities)
                .build();
        return pushMessage;
    }

}
