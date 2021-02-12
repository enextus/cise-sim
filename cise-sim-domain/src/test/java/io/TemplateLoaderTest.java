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

package io;

import eu.cise.sim.config.SimConfig;
import eu.cise.sim.exceptions.DirectoryNotFoundEx;
import eu.cise.sim.exceptions.TemplateNotFoundEx;
import eu.cise.sim.templates.DefaultTemplateLoader;
import eu.cise.sim.templates.Template;
import eu.cise.sim.templates.TemplateLoader;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.*;

public class TemplateLoaderTest {

    private TemplateLoader templateLoader;
    private SimConfig simConfig;

    @Before
    public void before() {
        simConfig = mock(SimConfig.class);
        templateLoader = new DefaultTemplateLoader(simConfig);

        when(simConfig.messageTemplateDir())
            .thenReturn(getAbsPathFromResourceDir("templateDir"));
    }

    @After
    public void after() {
        reset(simConfig);
    }

    @Test
    public void it_returns_a_list_of_template_loading_files_in_templates_dir() {
        List<Template> templateList = templateLoader.loadTemplateList();

        assertThat(templateList).isInstanceOf(List.class);
    }

    @Test
    public void it_loads_a_the_three_files_in_templates_dir() {
        List<Template> templateList = templateLoader.loadTemplateList();

        assertThat(templateList).hasSize(3);
    }

    @Test
    public void it_loads_a_file_named_COM_01_Vessel_a_xml() {
        List<Template> templateList = templateLoader.loadTemplateList();

        assertThat(templateList)
            .contains(new Template("COM_01_Vessel_a.xml", "COM_01_Vessel_a.xml"));
    }

    @Test
    public void it_returns_an_exception_when_requested_directory_doesNotExist() {
        when(simConfig.messageTemplateDir()).thenReturn("not-existing-dir");

        assertThatExceptionOfType(DirectoryNotFoundEx.class)
            .isThrownBy(() -> templateLoader.loadTemplateList());
    }

    @Test
    public void it_returns_a_templateId() {
        Template template = templateLoader.loadTemplate("COM_01_Vessel_a.xml");

        assertThat(template.getTemplateId()).isEqualTo("COM_01_Vessel_a.xml");
    }

    @Test
    public void it_returns_a_templateName() {
        Template template = templateLoader.loadTemplate("COM_01_Vessel_a.xml");

        assertThat(template.getTemplateName()).isEqualTo("COM_01_Vessel_a.xml");
    }

    @Test
    public void it_returns_a_templateContent() throws IOException {
        Template template = templateLoader.loadTemplate("COM_01_Vessel_a.xml");

        assertThat(template.getTemplateContent())
            .isEqualTo(readResource("templateDir/COM_01_Vessel_a.xml"));
    }

    @Test
    public void it_throw_an_exception_when_the_file_missing() {
        assertThatExceptionOfType(TemplateNotFoundEx.class)
            .isThrownBy(() -> templateLoader.loadTemplate("not_existing_file.xml"))
            .withMessageContaining("not_existing_file.xml");
    }

    @Test
    public void it_throw_an_exception_when_the_templateId_is_null() {
        assertThatExceptionOfType(TemplateNotFoundEx.class)
            .isThrownBy(() -> templateLoader.loadTemplate(null))
            .withMessageContaining("null");
    }

    private String readResource(String resourceName) throws IOException {
        Path path = Paths.get(getResourceURI(resourceName));

        return new String(Files.readAllBytes(path), UTF_8);
    }

    private String getAbsPathFromResourceDir(String resourceDir) {
        return new File(getResourceURI(resourceDir)).getAbsolutePath();
    }

    private URI getResourceURI(String resourceDir) {
        try {
            return this.getClass().getClassLoader().getResource(resourceDir).toURI();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}