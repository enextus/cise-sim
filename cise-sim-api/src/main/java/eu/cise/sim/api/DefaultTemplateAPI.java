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

import eu.cise.servicemodel.v1.message.Message;
import eu.cise.sim.api.representation.TemplateParams;
import eu.cise.sim.engine.MessageProcessor;
import eu.cise.sim.exceptions.LoaderEx;
import eu.cise.sim.templates.Template;
import eu.cise.sim.templates.TemplateLoader;
import eu.eucise.xml.XmlMapper;

import java.util.List;

public class DefaultTemplateAPI implements TemplateAPI {

    private final MessageProcessor messageProcessor;
    private final TemplateLoader templateLoader;
    private final XmlMapper xmlMapper;
    private final XmlMapper xmlMapperPrettyNotValidating;

    public DefaultTemplateAPI(
        MessageProcessor messageProcessor,
        TemplateLoader templateLoader,
        XmlMapper xmlMapper,
        XmlMapper xmlMapperPrettyNotValidating) {

        this.messageProcessor = messageProcessor;
        this.templateLoader = templateLoader;
        this.xmlMapper = xmlMapper;
        this.xmlMapperPrettyNotValidating = xmlMapperPrettyNotValidating;
    }

    @Override
    public ResponseApi<Template> preview(TemplateParams templateParams) {
        try {

            Template template = templateLoader.loadTemplate(templateParams.getTemplateId());

            Message preparedMessage = messageProcessor.preview(xmlMapper.fromXML(template.getTemplateContent()), templateParams.getSendParams());

            Template templateWithPreparedMessage = new Template(templateParams.getTemplateId(),
                                                                template.getTemplateName(),
                                                                xmlMapperPrettyNotValidating.toXML(preparedMessage));

            return new ResponseApi<>(templateWithPreparedMessage);
        } catch (Exception e) {
            return new ResponseApi<>(ResponseApi.ErrorId.FATAL, e.getMessage());
        }

    }

    @Override
    public ResponseApi<List<Template>> getTemplates() {
        try {
            return new ResponseApi<>(templateLoader.loadTemplateList());
        } catch (LoaderEx e) {
            return new ResponseApi<>(ResponseApi.ErrorId.FATAL, e.getMessage());
        }
    }
}
