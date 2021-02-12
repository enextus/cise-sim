/*
 * BSD 3-Clause License
 *
 * Copyright (c) 2021, Joint Research Centre (JRC) All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its
 *    contributors may be used to endorse or promote products derived from
 *    this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 */

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

        Service sender = new Service();
        pullBuilder.sender(sender);
        if (!StringUtils.isEmpty(discoveryRequestDto.getDiscoverySender())) {
            sender.setServiceID(discoveryRequestDto.getDiscoverySender());
            sender.setServiceType(ServiceType.fromValue(discoveryRequestDto.getDiscoveryServiceType()));
            sender.setServiceOperation(ServiceOperationType.fromValue(discoveryRequestDto.getDiscoveryServiceOperation()));
        }
        //sender.setServiceRole(ServiceRoleType.CONSUMER);

        ServiceProfile serviceProfile = new ServiceProfile();

        if (!StringUtils.isEmpty(discoveryRequestDto.getSeaBasin())) {
            serviceProfile.setSeaBasin(SeaBasinType.fromValue(discoveryRequestDto.getSeaBasin()));
        }

        if (!StringUtils.isEmpty(discoveryRequestDto.getServiceType())) {
            serviceProfile.setServiceType(ServiceType.fromValue(discoveryRequestDto.getServiceType()));
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

        PullRequest discoveryMessage = pullBuilder.build();
        discoveryMessage.setPullType(PullType.DISCOVER);
        discoveryMessage.getDiscoveryProfiles().add(serviceProfile);

        return new ResponseApi<>(discoveryMessage);
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
