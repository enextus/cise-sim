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

package eu.cise.sim.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.cise.sim.engine.SendParam;
import eu.cise.sim.api.helpers.SendParamsReader;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ParamReaderTest {

    private SendParamsReader paramReader;
    private ObjectMapper jsonMapper;

    @Before
    public void before() {
        jsonMapper = new ObjectMapper();
        paramReader = new SendParamsReader();
    }

    @Test
    public void it_extracts_from_the_json_message_the_messageId_value() {
        SendParam actual = paramReader.extractParams(msgParams());
        assertThat(actual.getMessageId()).isEqualTo("1234-123411-123411-1234");
    }

    @Test
    public void it_extracts_from_the_json_message_the_CorrelationId_value() {
        SendParam actual = paramReader.extractParams(msgParams());
        assertThat(actual.getCorrelationId()).isEqualTo("7777-666666-666666-5555");
    }

    @Test
    public void it_extracts_from_the_json_message_the_RequireAck_value() {
        SendParam actual = paramReader.extractParams(msgParams());
        assertThat(actual.isRequiresAck()).isEqualTo(true);
    }




    private JsonNode msgParams() {
        ObjectNode msgTemplateWithParamObject = jsonMapper.createObjectNode();
        msgTemplateWithParamObject.put("requiresAck", true);
        msgTemplateWithParamObject.put("messageId", "1234-123411-123411-1234");
        msgTemplateWithParamObject.put("correlationId", "7777-666666-666666-5555");

        return jsonMapper.valueToTree(msgTemplateWithParamObject);
    }

}
