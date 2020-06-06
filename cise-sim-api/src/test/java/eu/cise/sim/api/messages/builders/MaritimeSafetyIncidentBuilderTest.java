package eu.cise.sim.api.messages.builders;

import eu.cise.datamodel.v1.entity.incident.Incident;
import eu.cise.sim.api.messages.dto.incident.IncidentInfoDto;
import eu.cise.sim.api.messages.dto.incident.IncidentRequestDto;
import eu.cise.sim.api.messages.dto.incident.VesselInfoDto;
import eu.eucise.xml.DefaultXmlMapper;
import eu.eucise.xml.XmlMapper;
import org.junit.Test;

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
        msgRequest.getContentList().add("Ciao sto lavorando di sabato, yeah !");

        MaritimeSafetyIncidentBuilder msgBuilder = new MaritimeSafetyIncidentBuilder();
        Incident incident = msgBuilder.build(msgRequest);

        XmlMapper prettyNotValidatingXmlMapper = new DefaultXmlMapper.PrettyNotValidating();
        String xml = prettyNotValidatingXmlMapper.toXML(incident);

        assertNotNull(xml);
        System.out.println(xml);
    }
}