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
import eu.cise.sim.api.ResponseApi;
import eu.cise.sim.api.messages.builders.IncidentBuilder;
import eu.cise.sim.api.messages.builders.MockMessage;
import eu.cise.sim.api.messages.dto.discovery.DiscoveryRequestDto;
import eu.cise.sim.api.messages.dto.incident.IncidentRequestDto;
import eu.cise.sim.api.messages.dto.incident.IncidentTypeEnum;
import eu.cise.sim.api.messages.dto.label.DiscoveryMessageLabelDto;
import eu.cise.sim.api.messages.dto.label.IncidentMessageLabelDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class MessageService implements MessageBuilderAPI {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageService.class);

    @Override
    public ResponseApi<Message> buildIncident(IncidentRequestDto incidentRequestDto)  {

        Message mockMessage;
        try {
            mockMessage = MockMessage.getIncidentMessage();
        } catch (IOException e) {
            return new ResponseApi<>(ResponseApi.ErrorId.FATAL, e.getMessage());
        }

        IncidentTypeEnum type = IncidentTypeEnum.valueOfGuiValue(incidentRequestDto.getIncident().getIncidentType());
        IncidentBuilder builder = type.getIncidentBuilder();
        Incident incident = builder.build(incidentRequestDto);

        XmlEntityPayload payload = new XmlEntityPayload();
        payload.getAnies().add(incident);
        mockMessage.setPayload(payload);

       return  new ResponseApi<>(mockMessage);
    }

    @Override
    public IncidentMessageLabelDto getLabelsIncident() {
        return IncidentMessageLabelDto.getInstance();
    }


    @Override
    public ResponseApi<Message> buildDiscovery(DiscoveryRequestDto discoveryRequestDto) {

        PullRequest mockMessage;
        try {
            mockMessage = MockMessage.getDiscoveryMessage();
        } catch (IOException e) {
            return new ResponseApi<>(ResponseApi.ErrorId.FATAL, e.getMessage());
        }

        ServiceProfile serviceProfile = new ServiceProfile();
        serviceProfile.setSeaBasin(SeaBasinType.fromValue(discoveryRequestDto.getSeaBasin()));
        serviceProfile.setServiceType(ServiceType.fromValue(discoveryRequestDto.getServiceType()));
        serviceProfile.setCountry(CountryType.fromValue(discoveryRequestDto.getCountry()));
        mockMessage.getDiscoveryProfiles().add(serviceProfile);

        return new ResponseApi<>(mockMessage);
    }

    @Override
    public DiscoveryMessageLabelDto getLabelsDiscovery() {
        return DiscoveryMessageLabelDto.getInstance();
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

    public HashMap<String, List<String>> getDiscoveryMap() {
        return discoveryMap;
    }
}
