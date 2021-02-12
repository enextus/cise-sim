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

package eu.cise.sim.api.messages.builders;

import eu.cise.datamodel.v1.entity.document.VesselDocument;
import eu.cise.datamodel.v1.entity.event.Event;
import eu.cise.datamodel.v1.entity.incident.Incident;
import eu.cise.servicemodel.v1.message.Message;
import eu.cise.servicemodel.v1.message.XmlEntityPayload;
import eu.cise.sim.api.messages.builders.incident.MaritimeSafetyIncidentBuilder;
import eu.cise.sim.api.messages.dto.incident.IncidentInfoDto;
import eu.cise.sim.api.messages.dto.incident.IncidentRequestDto;
import eu.cise.sim.api.messages.dto.incident.VesselInfoDto;
import eu.eucise.xml.DefaultXmlMapper;
import eu.eucise.xml.XmlMapper;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.Assert.assertNotNull;

public class MaritimeSafetyIncidentBuilderTest {

    @Test
    public void build() {

        IncidentRequestDto msgRequest = new IncidentRequestDto();

        IncidentInfoDto incidentInfoDto = new IncidentInfoDto();
        incidentInfoDto.setIncidentType("maritime");
        incidentInfoDto.setSubType("ElectricalGeneratingSystemFailure");
        incidentInfoDto.setLatitude("37.9333");
        incidentInfoDto.setLongitude("23.5301");

        VesselInfoDto vesselInfoDto = new VesselInfoDto();
        vesselInfoDto.setImoNumber("11");
        vesselInfoDto.setMmsi("22");
        vesselInfoDto.setVesselType("GeneralCargoShip");
        vesselInfoDto.setRole("Participant");

        msgRequest.setIncident(incidentInfoDto);
        msgRequest.getVesselList().add(vesselInfoDto);
     //   msgRequest.getContentList().add("Ciao sto lavorando di sabato, yeah !");

        MaritimeSafetyIncidentBuilder msgBuilder = new MaritimeSafetyIncidentBuilder();
        Incident incident = msgBuilder.build(msgRequest);

        XmlMapper prettyNotValidatingXmlMapper = new DefaultXmlMapper.PrettyNotValidating();
        String xml = prettyNotValidatingXmlMapper.toXML(incident);

        assertNotNull(xml);
    }

    //@Test
    public void checkContent() throws IOException {

        String fileDest ="/home/alex/Documenti/test.pdf";

        XmlMapper xmlMapper = new DefaultXmlMapper.PrettyNotValidating();
        String message =  readResource("messages/PULLREQUEST_with_CONTENT.xml");
        assertNotNull(message);

        Message pullReq = xmlMapper.fromXML(message);
        XmlEntityPayload payload = (XmlEntityPayload) pullReq.getPayload();
        List<Object>  ansiesList = payload.getAnies();
        for (Object ansie : ansiesList) {
            if (ansie instanceof Event) {
                Event event = (Event) ansie;
                List<Event.DocumentRel> documentRelList = event.getDocumentRels();
                for (Event.DocumentRel documentRel : documentRelList) {
                    VesselDocument vesselDocument = (VesselDocument) documentRel.getDocument();
                    byte[] pdfDoc = vesselDocument.getContent();
                    writeBytesToFileNio(pdfDoc, fileDest);
                }
            }
        }
    }


    private String readResource(String resourceName) throws IOException {
        Path path = Paths.get(getResourceURI(resourceName));
        return Files.readString(path);
    }

    private URI getResourceURI(String resourceDir) {
        try {
            return this.getClass().getClassLoader().getResource(resourceDir).toURI();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private static void writeBytesToFileNio(byte[] bFile, String fileDest) {

        try {
            Path path = Paths.get(fileDest);
            Files.write(path, bFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}