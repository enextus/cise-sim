package eu.cise.sim.api.messages;

import eu.cise.datamodel.v1.entity.incident.Incident;
import eu.cise.servicemodel.v1.authority.CountryType;
import eu.cise.servicemodel.v1.authority.Participant;
import eu.cise.servicemodel.v1.authority.SeaBasinType;
import eu.cise.servicemodel.v1.message.Acknowledgement;
import eu.cise.servicemodel.v1.message.Message;
import eu.cise.servicemodel.v1.message.PullRequest;
import eu.cise.servicemodel.v1.message.XmlEntityPayload;
import eu.cise.servicemodel.v1.service.Service;
import eu.cise.servicemodel.v1.service.ServiceProfile;
import eu.cise.servicemodel.v1.service.ServiceType;
import eu.cise.sim.api.messages.builders.IncidentBuilder;
import eu.cise.sim.api.messages.builders.MockMessage;
import eu.cise.sim.api.messages.dto.discovery.DiscoveryRequestDto;
import eu.cise.sim.api.messages.dto.incident.IncidentRequestDto;
import eu.cise.sim.api.messages.dto.incident.IncidentTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class MessageService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageService.class);

    public Message buildIncidentMsg(IncidentRequestDto incidentRequestDto) throws IOException {

        IncidentTypeEnum type = IncidentTypeEnum.valueOfGuiValue(incidentRequestDto.getIncident().getIncidentType());
        IncidentBuilder builder = type.getIncidentBuilder();
        Incident incident = builder.build(incidentRequestDto);
        Message mockMessage = MockMessage.getIncidentMessage();

        XmlEntityPayload payload = new XmlEntityPayload();
        payload.getAnies().add(incident);
        mockMessage.setPayload(payload);

       return mockMessage;
    }

    public Message buildDiscoveryMsg(DiscoveryRequestDto discoveryRequestDto) throws IOException {

        PullRequest mockMessage = MockMessage.getDiscoveryMessage();

        ServiceProfile serviceProfile = new ServiceProfile();
        serviceProfile.setSeaBasin(SeaBasinType.fromValue(discoveryRequestDto.getSeaBasin()));
        serviceProfile.setServiceType(ServiceType.fromValue(discoveryRequestDto.getServiceType()));
        serviceProfile.setCountry(CountryType.fromValue(discoveryRequestDto.getCountry()));
        mockMessage.getDiscoveryProfiles().add(serviceProfile);

        return mockMessage;
    }

    private HashMap<String, List<String>> discoveryMap;
    public void manageDiscoveryAnswer(Acknowledgement discoveryAck) {

        discoveryMap = new HashMap<>();
        List<Service> serviceList = discoveryAck.getDiscoveredServices();
        for (Service service : serviceList) {

            Participant partecipant = service.getParticipant();
            String partecipantId = partecipant.getId();
            List<String> servicesIdList =  partecipant.getProvidedServicesIds();

            discoveryMap.put(partecipantId, servicesIdList);
        }

        LOGGER.info("Discovery Map : " + discoveryMap);
    }
}
