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

package eu.cise.sim.dropw.rest;
/*
import eu.cise.datamodel.v1.entity.Entity;
import eu.cise.datamodel.v1.entity.vessel.Vessel;
import eu.cise.dispatcher.rest.RestDispatcher;
import eu.cise.servicemodel.v1.message.*;
import eu.cise.servicemodel.v1.service.ServiceOperationType;
import eu.cise.servicemodel.v1.service.ServiceType;
import eu.cise.signature.SignatureService;
import eu.cise.sim.app.SimApp;
import eu.cise.sim.app.SimConf;
import eu.cise.sim.engine.DispatchResult;
import eu.cise.sim.engine.Dispatcher;
import eu.eucise.xml.DefaultXmlMapper;
import eu.eucise.xml.XmlMapper;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static eu.cise.signature.SignatureServiceBuilder.newSignatureService;
import static eu.eucise.helpers.PushBuilder.newPush;
import static eu.eucise.helpers.ServiceBuilder.newService;
import static io.dropwizard.testing.ResourceHelpers.resourceFilePath;
import static org.assertj.core.api.Assertions.assertThat;

*/
public class RestDispatcherTest {
/* TODO decomment and fix
    @ClassRule
    public static final DropwizardAppRule<SimConf> DROPWIZARD =
        new DropwizardAppRule<>(SimApp.class, resourceFilePath("test-config.yml"));

    private String restEndpointDestination;
    private Dispatcher restDispatcher;
    private SignatureService signatureService;
    private final XmlMapper xmlMapperNoValidPretty = new DefaultXmlMapper.PrettyNotValidating();

    @Before
    public void before() {
        restEndpointDestination = "http://localhost:" + DROPWIZARD.getLocalPort() + "/api/messages";
        signatureService = makeSignatureService();
    }

    private SignatureService makeSignatureService() {
        return newSignatureService()
                .withKeyStoreName("cisesim-nodeex.jks")
                .withKeyStorePassword("cisesim")
                .withPrivateKeyAlias("cisesim-nodeex.nodeex.eucise.ex")
                .withPrivateKeyPassword("cisesim")
                .build();
    }

    @Test
    public void it_sends_the_signed_rest_message_with_success() {

        Vessel vessel = new Vessel();
        vessel.setIMONumber(123L);
        vessel.setCallSign("callSign");

        Message msg = buildMessagePush(vessel);

        Message signedMessage = signatureService.sign(msg);
        restDispatcher = new RestDispatcher(xmlMapperNoValidPretty);
        DispatchResult sendResult = restDispatcher.send(signedMessage, restEndpointDestination);
        Acknowledgement ack = sendResult.getResult();

        assertThat(ack.getAckCode()).isEqualTo(AcknowledgementType.SUCCESS);
    }

    private Message buildMessagePush(Entity entity) {

        List<Entity> entities = new ArrayList<>();
        entities.add(entity);

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
                .sender(newService().id("cx.cisesim1-nodecx.vessel.push.provider").operation(ServiceOperationType.PUSH).type(ServiceType.VESSEL_SERVICE).build())
                .recipient(newService().id("cx.cisesim2-nodecx.vessel.push.consumer").operation(ServiceOperationType.PUSH).build())
                .addEntities(entities)
                .build();
        return pushMessage;
    }
*/
}
