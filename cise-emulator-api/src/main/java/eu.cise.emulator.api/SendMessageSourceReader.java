package eu.cise.emulator.api;

import com.fasterxml.jackson.databind.JsonNode;

public class SendMessageSourceReader {


    public boolean asPayload(JsonNode json) {
        JsonNode sourceValue = json.at("/payload_template");
        boolean conditionExist = sourceValue.isValueNode();
        boolean conditionNotEmpty = !(sourceValue.textValue().equals(""));
        return conditionExist & conditionNotEmpty;
    }

    public String extractMessage(JsonNode json) {
        String actualTemplateHash = json.at("/message_template").textValue();
        // should do the necessary QUERY to filesystem
        return EXAMPLAR_TEMPLATE_MESSAGE_XML;
    }

    static final String EXAMPLAR_TEMPLATE_MESSAGE_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<ns4:Push xmlns:ns2=\"http://www.cise.eu/servicemodel/v1/authority/\" xmlns:ns4=\"http://www.cise.eu/servicemodel/v1/message/\" xmlns:ns3=\"http://www.cise.eu/servicemodel/v1/service/\">\n" +
            "\t<CorrelationID>476d949d-5aa4-44cc-8e20-c1a2288fe098</CorrelationID>\n" +
            "\t<CreationDateTime>2018-01-18T13:45:29.590+01:00</CreationDateTime>\n" +
            "\t<MessageID>2657a510-5469-429f-ad00-7c96daa7c7a2</MessageID>\n" +
            "\t<Priority>Low</Priority>\n" +
            "\t<Sender>\n" +
            "\t\t<SeaBasin>NorthSea</SeaBasin>\n" +
            "\t\t<ServiceID>de.sim1-node01.vessel.push.provider</ServiceID>\n" +
            "\t\t<ServiceOperation>Push</ServiceOperation>\n" +
            "\t\t<ServiceRole>Provider</ServiceRole>\n" +
            "\t\t<ServiceStatus>Online</ServiceStatus>\n" +
            "\t\t<ServiceType>VesselService</ServiceType>\n" +
            "\t</Sender>\n" +
            "\t<Recipient>\n" +
            "\t\t<SeaBasin>NorthSea</SeaBasin>\n" +
            "\t\t<ServiceID>de.sim2-node01.vessel.push.consumer</ServiceID>\n" +
            "\t\t<ServiceOperation>Push</ServiceOperation>\n" +
            "\t\t<ServiceRole>Consumer</ServiceRole>\n" +
            "\t\t<ServiceStatus>Online</ServiceStatus>\n" +
            "\t\t<ServiceType>VesselService</ServiceType>\n" +
            "\t</Recipient>\n" +
            "\t<Payload xsi:type=\"ns4:XmlEntityPayload\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" +
            "\t\t<InformationSecurityLevel>NonClassified</InformationSecurityLevel>\n" +
            "\t\t<InformationSensitivity>Green</InformationSensitivity>\n" +
            "\t\t<IsPersonalData>false</IsPersonalData>\n" +
            "\t\t<Purpose>NonSpecified</Purpose>\n" +
            "\t\t<EnsureEncryption>false</EnsureEncryption>\n" +
            "\t\t<Vessel>\n" +
            "\t\t\t<IMONumber>7710525</IMONumber>\n" +
            "\t\t</Vessel>\n" +
            "\t</Payload>\n" +
            "</ns4:Push>\n";

}
