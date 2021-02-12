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

package eu.cise.dispatcher.rest;

import eu.cise.servicemodel.v1.message.Message;
import eu.cise.sim.engine.DispatchResult;
import eu.cise.sim.engine.Dispatcher;
import eu.eucise.xml.XmlMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The RestDispatcher performs RESTful request to nodes or legacy systems. The current
 * implementation is just sending CISE Messages
 */
@SuppressWarnings({"WeakerAccess", "Unused"})
public class RestDispatcher implements Dispatcher {

    private final Logger logger;
    private final RestClient client;
    /**
     * NOTE: This mapper must be not validating
     */
    private final XmlMapper xmlMapper;

    /**
     * This constructor is called by the class for name
     *
     * @param prettyNotValidatingXmlMapper
     */
    @SuppressWarnings("unused")
    public RestDispatcher(XmlMapper prettyNotValidatingXmlMapper) {
        this(new JerseyRestClient(), prettyNotValidatingXmlMapper);
    }

    public RestDispatcher(RestClient client, XmlMapper xmlMapper) {
        this.client = client;
        this.xmlMapper = xmlMapper;
        this.logger = LoggerFactory.getLogger(RestDispatcher.class);
    }

    /**
     * The main responsibility of the class is to send messages by using a RESTful client.
     *
     * @param message message to be sent.
     * @param address gateway address to send the message to
     * @return a {@link DispatchResult} containing the dispatching results
     */
    @Override
    public DispatchResult send(Message message, String address) {
        String payload = xmlMapper.toXML(message);

        if (logger.isDebugEnabled()) {
            logger.debug("> sending message");
            logger.debug("> address: {}", address);
            logger.debug("> payload: \n{}\n", payload);
        } else {
            logger.info("Sending Rest message to " + address);
        }

        RestResult result = client.post(address, payload);

        if (logger.isDebugEnabled()) {
            logger.debug("< server response: {}", result);
        } else {
            logger.info("Server response: isOk[{}], code[{}], message[{}]", result.isOK(), result.getCode(), result.getMessage());
        }
        return new DispatchResult(result.isOK(), xmlMapper.fromXML(result.getBody()));
    }

}