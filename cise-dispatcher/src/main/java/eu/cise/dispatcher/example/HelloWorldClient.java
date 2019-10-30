package eu.cise.dispatcher.example;


import eu.cise.datamodel.v1.entity.Entity;
import eu.cise.datamodel.v1.entity.cargo.Cargo;
import eu.cise.datamodel.v1.entity.cargo.CargoType;
import eu.cise.datamodel.v1.entity.uniqueidentifier.UniqueIdentifier;
import eu.cise.datamodel.v1.entity.vessel.Vessel;
import eu.cise.dispatcher.soap.CISEMessageService;
import eu.cise.dispatcher.soap.CISEMessageServiceSoapImpl;
import eu.cise.dispatcher.soap.WsHandler;
import eu.cise.servicemodel.v1.message.*;
import eu.cise.servicemodel.v1.service.ServiceOperationType;
import eu.cise.servicemodel.v1.service.ServiceType;
import eu.cise.signature.DefaultSignatureService;
import eu.cise.signature.DomSigner;
import eu.cise.signature.SignatureService;
import eu.eucise.xml.DefaultXmlMapper;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static eu.cise.signature.SignatureServiceBuilder.newSignatureService;
import static eu.eucise.helpers.PushBuilder.newPush;
import static eu.eucise.helpers.ServiceBuilder.newService;

public class HelloWorldClient {
    public static void main(String[] argv) {
        CISEMessageServiceSoapImpl service = new CISEMessageService().getCISEMessageServiceSoapPort();
        //invoke business method
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

        SignatureService signatureService= makeSignatureService();
        Message signedPushMessage=  signatureService.sign(pushMessage);
        Acknowledgement ack = service.send(signedPushMessage);

        System.out.println(ack);
    }

    public static SignatureService makeSignatureService() {
        System.setProperty("conf.dir","/home/longama/IdeaProjects/cise-emu/cise-dispatcher/src/main/resources/");
        return newSignatureService()
                .withKeyStoreName("cisesim1-nodecx.jks")
                .withKeyStorePassword("password")
                .withPrivateKeyAlias("cisesim1-nodecx.nodecx.eucise.cx")
                .withPrivateKeyPassword("password")
                .build();
    }
}
