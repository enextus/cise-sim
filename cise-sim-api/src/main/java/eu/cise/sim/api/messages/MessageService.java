package eu.cise.sim.api.messages;

import eu.cise.datamodel.v1.entity.incident.Incident;
import eu.cise.servicemodel.v1.authority.CountryType;
import eu.cise.servicemodel.v1.authority.Participant;
import eu.cise.servicemodel.v1.authority.SeaBasinType;
import eu.cise.servicemodel.v1.message.*;
import eu.cise.servicemodel.v1.service.*;
import eu.cise.sim.api.ResponseApi;
import eu.cise.sim.api.messages.builders.IncidentBuilder;
import eu.cise.sim.api.messages.builders.MockMessage;
import eu.cise.sim.api.messages.dto.discovery.DiscoveryRequestDto;
import eu.cise.sim.api.messages.dto.incident.IncidentRequestDto;
import eu.cise.sim.api.messages.dto.incident.IncidentTypeEnum;
import eu.cise.sim.api.messages.dto.label.DiscoveryMessageLabelDto;
import eu.cise.sim.api.messages.dto.label.IncidentMessageLabelDto;
import eu.eucise.helpers.MessageBuilder;
import eu.eucise.helpers.PullRequestBuilder;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class MessageService implements MessageBuilderAPI {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageService.class);

    /************************************/
    /* ----------- INCIDENT ----------- */
    /************************************/

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


    /************************************/
    /* ----------- DISCOVERY ---------- */
    /************************************/

    @Override
    public ResponseApi<Message> buildDiscovery(DiscoveryRequestDto discoveryRequestDto) {

        PullRequestBuilder pullBuilder = PullRequestBuilder.newPullRequest();
        initBuilder(pullBuilder);


        /*
        try {
            mockMessage = MockMessage.getDiscoveryMessage();
        } catch (IOException e) {
            return new ResponseApi<>(ResponseApi.ErrorId.FATAL, e.getMessage());
        }
        */

        Service sender = new Service();
        pullBuilder.sender(sender);
        if (!StringUtils.isEmpty(discoveryRequestDto.getDiscoverySender())) {
            sender.setServiceID(discoveryRequestDto.getDiscoverySender());
        }
        sender.setServiceOperation(ServiceOperationType.PULL);
        sender.setServiceRole(ServiceRoleType.CONSUMER);
        sender.setServiceType(ServiceType.VESSEL_SERVICE)
        ;
        ServiceProfile serviceProfile = new ServiceProfile();

        if (!StringUtils.isEmpty(discoveryRequestDto.getSeaBasin())) {
            serviceProfile.setSeaBasin(SeaBasinType.fromValue(discoveryRequestDto.getSeaBasin()));
        }

        if (!StringUtils.isEmpty(discoveryRequestDto.getServiceType())) {
            serviceProfile.setServiceType(ServiceType.fromValue(discoveryRequestDto.getServiceType()));
            sender.setServiceType(ServiceType.fromValue(discoveryRequestDto.getServiceType()));

        }

        if (!StringUtils.isEmpty(discoveryRequestDto.getCountry())) {
            serviceProfile.setCountry(CountryType.fromValue(discoveryRequestDto.getCountry()));
        }

        if (!StringUtils.isEmpty(discoveryRequestDto.getServiceOperation())) {
            serviceProfile.setServiceOperation(ServiceOperationType.fromValue(discoveryRequestDto.getServiceOperation()));
        }

        if (!StringUtils.isEmpty(discoveryRequestDto.getServiceRole())) {
            serviceProfile.setServiceRole(ServiceRoleType.fromValue(discoveryRequestDto.getServiceRole()));
        }

        PullRequest mockMessage = pullBuilder.build();
        mockMessage.setPullType(PullType.DISCOVER);
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

    protected void initBuilder(MessageBuilder<? extends Message, ? extends MessageBuilder<?, ?>> mb) {

        String messageId = UUID.randomUUID().toString();

        mb.id(messageId)
                .correlationId(messageId)
                .creationDateTime(new Date())
                .isRequiresAck(false)
                .informationSecurityLevel(InformationSecurityLevelType.NON_SPECIFIED)
                .informationSensitivity(InformationSensitivityType.NON_SPECIFIED)
                .isPersonalData(false)
                .purpose(PurposeType.NON_SPECIFIED)
                .priority(PriorityType.HIGH);
    }

}
