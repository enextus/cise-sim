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

import eu.cise.servicemodel.v1.message.Acknowledgement;
import eu.cise.servicemodel.v1.message.Message;
import eu.cise.signature.SignatureService;
import eu.cise.signature.exceptions.InvalidMessageSignatureEx;
import eu.cise.signature.exceptions.SignatureMarshalEx;
import eu.cise.signature.exceptions.SigningCACertInvalidSignatureEx;
import eu.cise.sim.SynchronousAcknowledgement.SynchronousAcknowledgementFactory;
import eu.cise.sim.SynchronousAcknowledgement.SynchronousAcknowledgementType;
import eu.cise.sim.config.SimConfig;
import eu.cise.sim.exceptions.*;

import javax.xml.datatype.XMLGregorianCalendar;
import java.sql.Date;
import java.time.Clock;

import static com.google.common.base.Strings.isNullOrEmpty;
import static eu.cise.sim.helpers.Asserts.notBlank;
import static eu.cise.sim.helpers.Asserts.notNull;
import static eu.eucise.helpers.DateHelper.toXMLGregorianCalendar;


public class DefaultSimEngine implements SimEngine {

    private final Clock clock;
    private final SimConfig simConfig;
    private final SignatureService signature;
    private final Dispatcher dispatcher;
    private final SynchronousAcknowledgementFactory acknowledgementFactory;

    /**
     * Default constructor that uses UTC as a reference clock
     * <p>
     * TODO is now clear that this class has way too many responsibilities. It should be split in
     * several classes
     */
    public DefaultSimEngine(
        SignatureService signature,
        Dispatcher dispatcher,
        SimConfig simConfig) {

        this(signature, simConfig, dispatcher, Clock.systemUTC());
    }

    /**
     * Constructor that expect a clock as a reference to compute date and time.
     *
     * @param signature  the signature service used to sign messages
     * @param simConfig  the domain configuration
     * @param dispatcher the object used to dispatch the message
     * @param clock      the reference clock
     *                   <p>
     *                   NOTE: this constructor is used only in tests
     */
    public DefaultSimEngine(
        SignatureService signature,
        SimConfig simConfig,
        Dispatcher dispatcher,
        Clock clock) {

        this.signature = notNull(signature, NullSignatureServiceEx.class);
        this.simConfig = notNull(simConfig, NullConfigEx.class);
        this.dispatcher = notNull(dispatcher, NullDispatcherEx.class);
        this.clock = notNull(clock, NullClockEx.class);

        // Every time there is a new it should be where all the constructions are happening
        this.acknowledgementFactory = new SynchronousAcknowledgementFactory();
    }

    @Override
    public <T extends Message> T prepare(T message, SendParam param) {
        notNull(param, NullSendParamEx.class);
        notNull(message.getSender(), NullSenderEx.class);

        message.setRequiresAck(param.isRequiresAck());
        message.setMessageID(param.getMessageId());
        message.setCorrelationID(computeCorrelationId(param.getCorrelationId(), param.getMessageId()));

        message.setCreationDateTime(now());

        // TODO improve signature to use <T extends Message> as a return type
        return (T) signature.sign(message);
    }

    @Override
    public Acknowledgement send(Message message) {
        try {
            DispatchResult sendResult = dispatcher.send(message, simConfig.destinationUrl());

            /* Understand if this can be managed in a better way
            if (!sendResult.isOK()) {
                throw new EndpointErrorEx();
            }
            */
            return sendResult.getResult();
        } catch (DispatcherException e) {
            throw new EndpointNotFoundEx();
        }
    }

    @Override
    public Acknowledgement receive(Message message) {
        notNull(message, NullMessageEx.class);
        notBlank(message.getMessageID(), EmptyMessageIdEx.class);

        // TODO The simulator should be able to receive and show a message
        // and to report errors of the message itself.
        if (message.getSender() == null) {
            throw new NullSenderEx();
        }

        Acknowledgement ack;
        try {
            signature.verify(message);
            ack = acknowledgementFactory.buildAck(message, SynchronousAcknowledgementType.SUCCESS, "");

        } catch (InvalidMessageSignatureEx | SignatureMarshalEx | SigningCACertInvalidSignatureEx eInvalidSignature) {
            ack = acknowledgementFactory.buildAck(message, SynchronousAcknowledgementType.INVALID_SIGNATURE, eInvalidSignature.getMessage());
        }

        return ack;

    }

    private String computeCorrelationId(String correlationId, String messageId) {
        if (isNullOrEmpty(correlationId)) {
            return messageId;
        }
        return correlationId;
    }

    private XMLGregorianCalendar now() {
        return toXMLGregorianCalendar(Date.from(clock.instant()));
    }
}
