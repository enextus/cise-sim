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

package eu.cise.sim.templates;


import eu.cise.sim.config.SimConfig;
import eu.cise.sim.exceptions.DirectoryNotFoundEx;
import eu.cise.sim.exceptions.LoaderEx;
import eu.cise.sim.exceptions.TemplateNotFoundEx;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class DefaultTemplateLoader implements TemplateLoader {

    private final SimConfig simConfig;

    public DefaultTemplateLoader(SimConfig simConfig) {
        this.simConfig = simConfig;
    }

    @Override
    public Template loadTemplate(String templateId) {
        return new Template(templateId, templateId, readFile(templateId));
    }

    @Override
    public List<Template> loadTemplateList() throws LoaderEx {
        try {
            return Files.list(messageTemplatePath(simConfig.messageTemplateDir()))
                    .filter(s -> s.toString().endsWith(".xml"))
                    .sorted()
                    .map(e -> e.toFile().getName())
                    .map(e -> new Template(e, e))
                    .collect(Collectors.toList());

        } catch (NoSuchFileException e) {
            throw new DirectoryNotFoundEx(e);
        } catch (IOException e) {
            throw new LoaderEx(e);
        }
    }

    // TODO add the support to read a file also if the path is absolute without
    //  using the emuConfig.messageTemplateDir
    private String readFile(String fileName) {
        try {
            return Files.readString(getFilePath(fileName));
        } catch (IOException e) {
            throw new TemplateNotFoundEx(fileName);
        }
    }

    private Path getFilePath(String fileName) {
        return Paths.get(simConfig.messageTemplateDir() + "/" + fileName);
    }

    private Path messageTemplatePath(String pathname) {
        return Paths.get(new File(pathname).getAbsolutePath());
    }
}
