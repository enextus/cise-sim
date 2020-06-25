package eu.cise.sim.api.messages;

import eu.cise.servicemodel.v1.message.Message;
import eu.cise.sim.api.ResponseApi;
import eu.cise.sim.api.messages.dto.discovery.DiscoveryRequestDto;
import eu.cise.sim.api.messages.dto.incident.IncidentRequestDto;
import eu.cise.sim.api.messages.dto.label.DiscoveryMessageLabelDto;
import eu.cise.sim.api.messages.dto.label.IncidentMessageLabelDto;

import java.io.IOException;


public interface MessageBuilderAPI {

    /* ----------- INCIDENT ----------- */
    ResponseApi<Message> buildIncident(IncidentRequestDto incidentRequestDto) throws IOException;
    IncidentMessageLabelDto getLabelsIncident();

    /* ----------- DISCOVERY ----------- */
    ResponseApi<Message> buildDiscovery(DiscoveryRequestDto discoveryRequestDto) throws IOException;
    DiscoveryMessageLabelDto getLabelsDiscovery();
}
