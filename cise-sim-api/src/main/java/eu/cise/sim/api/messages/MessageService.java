package eu.cise.sim.api.messages;

import eu.cise.datamodel.v1.entity.incident.Incident;
import eu.cise.servicemodel.v1.message.Message;
import eu.cise.servicemodel.v1.message.XmlEntityPayload;
import eu.cise.sim.api.messages.builders.IncidentBuilderFactory;
import eu.cise.sim.api.messages.builders.IncidentBuilderInterface;
import eu.cise.sim.api.messages.builders.MockMessage;
import eu.cise.sim.api.messages.dto.incident.IncidentRequestDto;
import eu.cise.sim.api.messages.dto.incident.IncidentTypeEnum;

import java.io.IOException;

public class MessageService {


    public Message buildIncidentMsg(IncidentRequestDto incidentRequestDto) throws IOException {

        IncidentTypeEnum type = IncidentTypeEnum.valueOfGuiValue(incidentRequestDto.getIncident().getIncidentType());
        IncidentBuilderInterface builder = IncidentBuilderFactory.getBuilder(type);
        Incident incident = builder.build(incidentRequestDto);
        Message mockMessage = MockMessage.getPullMessage();

        XmlEntityPayload payload = new XmlEntityPayload();
        payload.getAnies().add(incident);
        mockMessage.setPayload(payload);

       return mockMessage;

    }
}
