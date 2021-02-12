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
import eu.cise.sim.io.DummyMessagePersistence;
import eu.cise.sim.io.MessagePersistence;
import eu.cise.sim.utils.Pair;

public class DefaultMessageProcessor implements MessageProcessor {

    private final SimEngine simEngine;
    private final MessagePersistence messagePersistence;

    public DefaultMessageProcessor(SimEngine simEngine, MessagePersistence messagePersistence) {
        this.simEngine = simEngine;
        this.messagePersistence = messagePersistence;
    }

    public DefaultMessageProcessor(SimEngine simEngine) {
        this.simEngine = simEngine;
        this.messagePersistence = new DummyMessagePersistence();
    }

    @Override
    public Message preview(Message message, SendParam param) {
        return simEngine.prepare(message, param);
    }

    @Override
    public Pair<Acknowledgement, Message> send(Message message, SendParam param) {

        Message preparedMessage = simEngine.prepare(message, param);
        Acknowledgement acknowledgement = simEngine.send(preparedMessage);

        messagePersistence.messageSent(preparedMessage);
        messagePersistence.messageReceived(acknowledgement);

        return new Pair<>(acknowledgement, preparedMessage);
    }

    @Override
    public Acknowledgement receive(Message message) {

        Acknowledgement acknowledgement =  simEngine.receive(message);

        messagePersistence.messageReceived(message);
        messagePersistence.messageSent(acknowledgement);

        return acknowledgement;
    }
}
