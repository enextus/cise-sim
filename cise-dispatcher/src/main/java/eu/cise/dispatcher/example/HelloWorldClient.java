package eu.cise.dispatcher.example;


import eu.cise.datamodel.v1.entity.vessel.Vessel;
import eu.cise.dispatcher.soap.CISEMessageService;
import eu.cise.dispatcher.soap.CISEMessageServiceSoapImpl;
import eu.cise.servicemodel.v1.message.*;
import eu.cise.servicemodel.v1.service.ServiceOperationType;
import eu.cise.servicemodel.v1.service.ServiceType;

import java.util.Date;

import static eu.eucise.helpers.PushBuilder.newPush;
import static eu.eucise.helpers.ServiceBuilder.newService;

public class HelloWorldClient {
    public static void main(String[] argv) {
        CISEMessageServiceSoapImpl service = new CISEMessageService().getCISEMessageServiceSoapPort();
        //invoke business method
        Vessel vessel = new Vessel();
        Push pushMessage = newPush()
                .id("mesageId")
                .correlationId("correlation-id")
                .creationDateTime(new Date())
                .priority(PriorityType.HIGH)
                .informationSecurityLevel(InformationSecurityLevelType.NON_CLASSIFIED)
                .informationSensitivity(InformationSensitivityType.GREEN)
                .setEncryptedPayload("false")
                .isPersonalData(false)
                .purpose(PurposeType.NON_SPECIFIED)
                .sender(newService().id("service-id").operation(ServiceOperationType.PUSH).type(ServiceType.VESSEL_SERVICE).build())
                .recipient(newService().id("recipient-id").operation(ServiceOperationType.PUSH).build())
                .addEntity(vessel)
                .build();

        Acknowledgement ack = service.send(pushMessage);
        System.out.println(ack);
    }
}
