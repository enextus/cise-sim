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

package eu.cise.sim.engine;

import eu.cise.sim.exceptions.EmptyMessageIdEx;

import java.util.Objects;

import static eu.cise.sim.helpers.Asserts.notBlank;

/**
 * This class is a value object that contains the xml elements
 * to be overridden in the message.
 * <p>
 * The requireAck is a mandatory field
 * The messageId is a mandatory field
 * The correlationId is optional
 */
public class SendParam {

    private final boolean requiresAck;
    private final String messageId;
    private final String correlationId;

    public SendParam(boolean requiresAck, String messageId, String correlationId) {
        this.requiresAck = requiresAck;
        this.messageId = notBlank(messageId, EmptyMessageIdEx.class);
        this.correlationId = correlationId;
    }

    public boolean isRequiresAck() {
        return requiresAck;
    }

    public String getMessageId() {
        return messageId;
    }

    public String getCorrelationId() {
        return correlationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SendParam sendParam = (SendParam) o;
        return requiresAck == sendParam.requiresAck &&
                Objects.equals(messageId, sendParam.messageId) &&
                Objects.equals(correlationId, sendParam.correlationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(requiresAck, messageId, correlationId);
    }
}
