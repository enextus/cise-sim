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

package eu.cise.sim.dropw.rest;

/*import eu.cise.sim.api.helpers.XmlFileDirectoryList;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;
*/
public class WebAPIFilesReferencesResourceTest {

    //File folder;

    /*
    @ClassRule
    public static final ResourceTestRule rest = ResourceTestRule.builder()
            .addResource(new WebAPIFilesReferencesResource())
            .bootstrapLogging(false)
            .build();

    @Before
    public void before() {
            //all test request a valid directory path
            folder = new File(this.getClass().getResource("/templateDir/myxml.xml").getFile()).toPath().getParent().toFile();
            System.out.println(folder.getAbsolutePath());

    }


    @Test
    public void it_retreive_3_files_with_in_the_directory() {
        AtomicInteger countFile = new AtomicInteger();

        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isFile()) {
                countFile.getAndIncrement();
            }
        }
        assertThat(countFile.get()).isEqualTo(3);
    }

    @Test
    public void it_retreive_2_xmlfiles_in_the_directory() {

        File[] files;
        AtomicInteger countFilexml = new AtomicInteger();


        try {
            Files.list(Paths.get(folder.getAbsolutePath()))
                    .filter(s -> s.toString().endsWith(".xml"))
                    .sorted()
                    .forEach(e -> countFilexml.getAndIncrement());
        } catch (IOException e) {
            e.printStackTrace();
        };

        assertThat(countFilexml.get()).isEqualTo(2);
    }
    @Test
    public void it_return_a_list_from_non_empty_directory() {

        XmlFileDirectoryList xmlFileDirectoryList= new XmlFileDirectoryList( folder.getAbsolutePath());
        ArrayList<String> listXmlFileNames= xmlFileDirectoryList.getXmlFileNames();
        assertThat(listXmlFileNames).isNotNull();
    }

    @Test
    public void it_return_exact_xmlfile_list_from_test_directory() {

        XmlFileDirectoryList xmlFileDirectoryList= new XmlFileDirectoryList( folder.getAbsolutePath());
        ArrayList<String> listXmlFileNames= xmlFileDirectoryList.getXmlFileNames();
        assertThat(listXmlFileNames).contains("myxml.xml");
        assertThat(listXmlFileNames).contains("my2dxml.xml");
        assertThat(listXmlFileNames.size()).isEqualTo(2);
    }
    */
}
