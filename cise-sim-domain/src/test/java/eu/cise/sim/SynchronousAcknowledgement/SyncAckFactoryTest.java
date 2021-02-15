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

package eu.cise.sim.SynchronousAcknowledgement;

import eu.cise.servicemodel.v1.authority.SeaBasinType;
import eu.cise.servicemodel.v1.message.*;
import eu.cise.servicemodel.v1.service.Service;
import eu.cise.servicemodel.v1.service.ServiceOperationType;
import eu.eucise.helpers.PushBuilder;
import eu.eucise.helpers.ServiceBuilder;
import eu.eucise.helpers.ServiceProfileBuilder;
import org.junit.Before;
import org.junit.Test;

import static eu.cise.servicemodel.v1.message.AcknowledgementType.BAD_REQUEST;
import static eu.cise.servicemodel.v1.message.InformationSecurityLevelType.NON_CLASSIFIED;
import static eu.cise.servicemodel.v1.message.InformationSensitivityType.GREEN;
import static eu.cise.servicemodel.v1.message.PurposeType.NON_SPECIFIED;
import static eu.cise.sim.SynchronousAcknowledgement.SyncAckType.INTERNAL_ERROR;
import static eu.eucise.helpers.PushBuilder.newPush;
import static eu.eucise.helpers.ServiceBuilder.newService;
import static org.assertj.core.api.Assertions.assertThat;

public class SyncAckFactoryTest {

    private PushBuilder messageBuilder;
    private ServiceBuilder serviceBuilder;
    private SyncAckFactory syncAckFactory;

    @Before
    public void before() {
        serviceBuilder = newService().id("my.fake.id");
        messageBuilder = newPush()
            .sender(serviceBuilder.build())
            .recipient(newService().id("recipient-id"))
            .correlationId("fakeId")
            .id("fakeId")
            .informationSensitivity(GREEN)
            .informationSecurityLevel(NON_CLASSIFIED)
            .purpose(NON_SPECIFIED);
        syncAckFactory = new SyncAckFactory();
    }

    @Test
    public void it_convert_success_to_acknowledge_with_success_Code() {
        Acknowledgement acknowledgement = syncAckFactory.buildAck(messageBuilder.build(), SyncAckType.SUCCESS, "");
        assertThat(acknowledgement.getAckCode()).isEqualTo(AcknowledgementType.SUCCESS);
    }


    @Test
    public void it_convert_error_XML_malformed_to_acknowledge_with_BadRequest_Code() {
        Acknowledgement acknowledgement = syncAckFactory.buildAck(messageBuilder.build(), SyncAckType.XML_MALFORMED, "xml ...");
        assertThat(acknowledgement.getAckCode()).isEqualTo(BAD_REQUEST);
    }

    @Test
    public void it_convert_error_InternalError_to_acknowledge_with_BadRequest_Code() {
        Acknowledgement acknowledgement = syncAckFactory.buildAck(
            messageBuilder.build(), INTERNAL_ERROR, "unknown ...");

        assertThat(acknowledgement.getAckCode()).isEqualTo(BAD_REQUEST);
    }


    @Test
    public void it_convert_error_Semantic_to_acknowledge_with_BadRequest_Code() {
        Acknowledgement acknowledgement = syncAckFactory.buildAck(messageBuilder.build(), SyncAckType.SEMANTIC, "semantic rule ...");
        assertThat(acknowledgement.getAckCode()).isEqualTo(BAD_REQUEST);
    }

    @Test
    public void it_reply_with_DiscoveredProfiles_in_case_of_PushSubscribe_with_no_Recipient_and_no_DiscoveryProfiles() {
        Push message = messageBuilder
            .sender(serviceBuilder.operation(ServiceOperationType.SUBSCRIBE).build())
            .build();
        message.setRecipient(null);

        Acknowledgement acknowledgement = syncAckFactory.buildAck(message, SyncAckType.SUCCESS, "");
        assertThat(acknowledgement.getDiscoveredServices().size()).isGreaterThan(0);
    }

    @Test
    public void it_reply_with_DiscoveredProfiles_in_case_of_PushSubscribe_with_no_Recipient_and_a_DiscoveryProfiles() {

        Push message = messageBuilder
            .sender(serviceBuilder.operation(ServiceOperationType.SUBSCRIBE).build())
            .addProfile(ServiceProfileBuilder.newProfile().seaBasin(SeaBasinType.ATLANTIC))
            .build();
        message.setRecipient(null);

        Acknowledgement acknowledgement = syncAckFactory.buildAck(message, SyncAckType.SUCCESS, "");

        assertThat(acknowledgement.getDiscoveredServices().size()).isGreaterThan(0);
    }

    @Test
    public void it_reply_with_error_in_case_of_PushSubscribe_with_both_Recipient_and_a_DiscoveryProfiles() {

        Acknowledgement acknowledgement = syncAckFactory.buildAck(
            messageBuilder.sender(serviceBuilder.operation(ServiceOperationType.SUBSCRIBE).build())
                .addProfile(ServiceProfileBuilder.newProfile().seaBasin(SeaBasinType.ATLANTIC))
                .build(),
            SyncAckType.SUCCESS,
            "");

        assertThat(acknowledgement.getAckCode()).isEqualTo(BAD_REQUEST);
    }

    @Test
    public void it_replies_with_error_for_Push_not_Subscribe_without_no_Recipient_and_no_DiscoveryProfiles() {

        Push message = messageBuilder.recipient((Service) null).build();

        Acknowledgement acknowledgement =
            syncAckFactory.buildAck(message, SyncAckType.SUCCESS, "");

        assertThat(acknowledgement.getAckCode()).isEqualTo(BAD_REQUEST);
    }


}