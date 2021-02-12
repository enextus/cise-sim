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

import eu.cise.sim.engine.MessageProcessor;
import eu.cise.sim.exceptions.LoaderEx;
import eu.cise.sim.templates.Template;
import eu.cise.sim.templates.TemplateLoader;
import eu.eucise.xml.DefaultXmlMapper;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class DefaultTemplateAPITest {

    private DefaultTemplateAPI defaultTemplateAPI;
    private TemplateLoader templateLoader;

    @Before
    public void before() {
        templateLoader = mock(TemplateLoader.class);
        defaultTemplateAPI = new DefaultTemplateAPI(
                mock(MessageProcessor.class),
                templateLoader,
                new DefaultXmlMapper(), new DefaultXmlMapper.PrettyNotValidating());
    }

    @Test
    public void it_load_the_template_list() {
        defaultTemplateAPI.getTemplates();

        verify(templateLoader).loadTemplateList();
    }

    @Test
    public void it_returns_a_template_list() {
        List<Template> expectedTemplateList = asList(new Template("id-1", "name-1"),
                new Template("id-2", "name-2"));

        when(templateLoader.loadTemplateList()).thenReturn(expectedTemplateList);

        List<Template> actualTemplateList = defaultTemplateAPI.getTemplates().getResult();

        assertThat(actualTemplateList).hasSameElementsAs(expectedTemplateList);
    }

    @Test
    public void it_returns_a_ok_response_when_returning_a_list() {
        List<Template> expectedTemplateList = asList(new Template("id-1", "name-1"),
                new Template("id-2", "name-2"));

        when(templateLoader.loadTemplateList()).thenReturn(expectedTemplateList);

        assertThat(defaultTemplateAPI.getTemplates().isOk()).isTrue();
    }

    @Ignore
    @Test
    public void it_returns_a_ko_response_when_throwing_an_IOLoaderException() {
        when(templateLoader.loadTemplateList()).thenThrow(new LoaderEx());

        defaultTemplateAPI.getTemplates();

        assertThat(defaultTemplateAPI.getTemplates().isOk()).isFalse();
    }

}