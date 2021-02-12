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

package eu.cise.sim.api.history;

import eu.cise.servicemodel.v1.message.Message;
import eu.cise.sim.api.dto.MessageShortInfoDto;
import eu.cise.sim.io.MessagePersistence;
import eu.cise.sim.io.MessageStorage;
import eu.cise.sim.io.QueueMessageStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;


public class MemoryQueuedRepository implements MessagePersistence {

    private static final Logger  LOGGER = LoggerFactory.getLogger(MemoryQueuedRepository.class);

    public static final Boolean MSG_SENT = Boolean.TRUE;
    public static final Boolean MSG_RECV = Boolean.FALSE;

    private final MessageStorage<MessageShortInfoDto> historyMessageStorage;


    public MemoryQueuedRepository() {
        this.historyMessageStorage = new QueueMessageStorage<>();
    }

    @Override
    public void messageReceived(Message msgRecv) {
        String uuid = UUID.randomUUID().toString();
        historyMessageStorage.store(MessageShortInfoDto.getInstance(msgRecv, MSG_RECV, new Date(), uuid));
        LOGGER.info("messageReceived");
    }

    @Override
    public void messageSent(Message msgSent) {
        String uuid = UUID.randomUUID().toString();
        historyMessageStorage.store(MessageShortInfoDto.getInstance(msgSent, MSG_SENT, new Date(), uuid));
        LOGGER.info("messageSent");
    }

    public List<MessageShortInfoDto> getLatestMessages() {

        List<MessageShortInfoDto> messagePairList = new ArrayList<>();

        MessageShortInfoDto messagePair;
        while ((messagePair = historyMessageStorage.read()) != null) {
            if (historyMessageStorage.delete(messagePair)) {
                messagePairList.add(messagePair);
            }
        }
        return  messagePairList;
    }
}
