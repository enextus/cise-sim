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

package eu.cise.cli;

import eu.cise.sim.engine.MessageProcessor;
import eu.cise.sim.engine.SendParam;
import eu.cise.sim.engine.SimEngine;

public class UseCaseSendMessage {

    private final MessageProcessor messageProcessor;

    private final SimEngine simEngine;
    private final MessageLoader loader;

    public UseCaseSendMessage(SimEngine simEngine, MessageLoader loader, MessageProcessor messageProcessor) {
        this.simEngine = simEngine;
        this.loader = loader;
        this.messageProcessor = messageProcessor;
    }

    public void send(String filename, SendParam sendParam) {
        if (simEngine != null) {
            sendOriginal(filename, sendParam);
        } else {
            sendWithProcessor(filename, sendParam);
        }
    }

    public void sendWithProcessor(String filename, SendParam sendParam) {
        var message = loader.load(filename);
        messageProcessor.send(message, sendParam);
    }

    public void sendOriginal(String filename, SendParam sendParam) {
        var message = loader.load(filename);
        var preparedMessage = simEngine.prepare(message, sendParam);
        var acknowledgement = simEngine.send(preparedMessage);

        loader.saveSentMessage(message);
        loader.saveReturnedAck(acknowledgement);
    }
}
