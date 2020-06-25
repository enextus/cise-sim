package eu.cise.sim.api.messages;

import eu.cise.servicemodel.v1.message.Message;
import eu.cise.sim.api.messages.dto.discovery.DiscoveryRequestDto;
import eu.cise.sim.api.messages.dto.incident.IncidentRequestDto;

import java.io.IOException;

public interface MessageBuilderAPI {

    Message buildIncident(IncidentRequestDto incidentRequestDto) throws IOException;

    Message buildDiscovery(DiscoveryRequestDto discoveryRequestDto) throws IOException;
}
