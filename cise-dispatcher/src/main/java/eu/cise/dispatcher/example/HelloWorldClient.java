package eu.cise.dispatcher.example;


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
import java.util.Date;

import static eu.cise.signature.SignatureServiceBuilder.newSignatureService;
import static eu.eucise.helpers.PushBuilder.newPush;
import static eu.eucise.helpers.ServiceBuilder.newService;

public class HelloWorldClient {
    public static void main(String[] argv) {
        CISEMessageServiceSoapImpl service = new CISEMessageService().getCISEMessageServiceSoapPort();
        //invoke business method
        Vessel vessel = new Vessel();
        vessel.setCallSign("coucou");
        WsHandler test ;
        try {
                URL baseUrl = CISEMessageService.class.getClassLoader().getResource(".");
                URL url = new URL(baseUrl, "META-INF/wsdl/CISEMessageService.wsdl");
                test = new WsHandler();
        } catch (MalformedURLException murl) { throw new RuntimeException(murl); }



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
                .sender(newService().id("service-id").operation(ServiceOperationType.PUSH).type(ServiceType.VESSEL_SERVICE).build())
                .recipient(newService().id("recipient-id").operation(ServiceOperationType.PUSH).build())
                .addEntity(vessel)
                .build();

        SignatureService signatureService= makeSignatureService();
        Message signedPushMessage=  signatureService.sign(pushMessage);

        Acknowledgement ack = service.send(signedPushMessage);
        System.out.println(ack);
    }

    public static SignatureService makeSignatureService() {
        System.setProperty("conf.dir","/home/cise/IdeaProjects/cise-emu/cise-dispatcher/src/main/resources/");
        return newSignatureService()
                .withKeyStoreName("cisesim1-nodecx.jks")
                .withKeyStorePassword("password")
                .withPrivateKeyAlias("cisesim1-nodecx.nodecx.eucise.cx")
                .withPrivateKeyPassword("password")
                .build();
    }
}
