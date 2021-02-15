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
import eu.cise.servicemodel.v1.message.Acknowledgement;
import eu.cise.servicemodel.v1.message.AcknowledgementType;
import eu.cise.servicemodel.v1.message.Message;
import eu.cise.signature.exceptions.InvalidMessageSignatureEx;
import eu.cise.signature.exceptions.SigningCACertInvalidSignatureEx;
import eu.cise.sim.engine.SyncAckFactory;
import eu.cise.sim.engine.SyncAckType;
import eu.cise.sim.api.dto.MessageTypeEnum;
import eu.cise.sim.api.helpers.SendParamsReader;
import eu.cise.sim.engine.MessageProcessor;
import eu.cise.sim.engine.SendParam;
import eu.cise.sim.exceptions.NullSenderEx;
import eu.cise.sim.templates.Template;
import eu.cise.sim.templates.TemplateLoader;
import eu.cise.sim.utils.Pair;
import eu.eucise.xml.XmlMapper;
import eu.eucise.xml.XmlNotParsableException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class DefaultMessageAPI implements MessageAPI {

    private final Logger logger = LoggerFactory.getLogger(MessageAPI.class);

    private final MessageProcessor engineMessageProcessor;
    private final XmlMapper xmlMapper;
    private final TemplateLoader templateLoader;
    private final SyncAckFactory synchronousAcknowledgementFactory = new SyncAckFactory();

    public DefaultMessageAPI(MessageProcessor engineMessageProcessor,
                             TemplateLoader templateLoader,
                             XmlMapper xmlMapper,
                             XmlMapper prettyNotValidatingXmlMapper) {

        this.engineMessageProcessor = engineMessageProcessor;
        this.xmlMapper = xmlMapper;
        this.templateLoader = templateLoader;
    }

    @Override
    public ResponseApi<MessageResponse>  send(String templateId, JsonNode params) {
        logger.debug("send is passed through api templateId: {}, params: {}", templateId, params);

        Template template = templateLoader.loadTemplate(templateId);
        String xmlContent = template.getTemplateContent();

        Message message = xmlMapper.fromXML(xmlContent);
        SendParam sendParam = new SendParamsReader().extractParams(params);
        return send(message, sendParam);
    }

    @Override
    public ResponseApi<MessageResponse>  send(Message message) {

            String messageId = UUID.randomUUID().toString();
            SendParam sendParam = new SendParam(false, messageId, messageId);
            return send(message, sendParam);
    }

    private  ResponseApi<MessageResponse> send(Message message, SendParam sendParam) {

        try {

            Pair<Acknowledgement, Message> sendResponse = engineMessageProcessor.send(message, sendParam);
            logAckError(sendResponse.getA());
            return new ResponseApi<>(new MessageResponse(sendResponse.getB(), sendResponse.getA()));

        } catch (Exception e) {
            logger.error("Error sending a message to destination.url", e);
            return new ResponseApi<>(ResponseApi.ErrorId.FATAL, e.getMessage());

        }
    }

    @Override
    public ResponseApi<String> receiveXML(String content) {
        logger.debug("receive is receiving through api : {}", content.substring(0, 200));
        Message message = xmlMapper.fromXML(content);
        ResponseApi<Acknowledgement> acknowledgement = receive(message);
        return new ResponseApi<>(xmlMapper.toXML(acknowledgement.getResult()));
    }

    @Override
    public ResponseApi<Acknowledgement> receive(Message message) {

        logger.info("Received message {} id[{}]", MessageTypeEnum.valueOf(message).getUiName(), message.getMessageID());

        try {

            return new ResponseApi<>(engineMessageProcessor.receive(message));

        } catch (InvalidMessageSignatureEx | SigningCACertInvalidSignatureEx eInvalidSignature) {
            return new ResponseApi<>(synchronousAcknowledgementFactory
                    .buildAck(message, SyncAckType.INVALID_SIGNATURE,
                            "" + eInvalidSignature.getMessage()));
        } catch (XmlNotParsableException eXmlMalformed) {
            return new ResponseApi<>(synchronousAcknowledgementFactory
                    .buildAck(message, SyncAckType.XML_MALFORMED,
                            "" + eXmlMalformed.getMessage()));
        } catch (NullSenderEx eSemantic) {
            return new ResponseApi<>(synchronousAcknowledgementFactory
                    .buildAck(message, SyncAckType.SEMANTIC,
                            "" + eSemantic.getMessage()));
        } catch (Exception eAny) {
            return new ResponseApi<>(synchronousAcknowledgementFactory
                    .buildAck(message, SyncAckType.INTERNAL_ERROR,
                            "Unknown Error : " + eAny.getMessage()));
        }
    }

    private void logAckError(Acknowledgement ack) {
        if (ack.getAckCode() != AcknowledgementType.SUCCESS) {
            logger.warn("ACK UNSECCESS : code[{}] detail[{}]", ack.getAckCode(), ack.getAckDetail());
        }
    }
}
