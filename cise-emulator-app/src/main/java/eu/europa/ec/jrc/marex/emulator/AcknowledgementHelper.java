package eu.europa.ec.jrc.marex.emulator;

import eu.cise.servicemodel.v1.message.Acknowledgement;
import eu.eucise.xml.XmlMapper;
import eu.europa.ec.jrc.marex.cli.ClientCustomCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AcknowledgementHelper {


    private static final Logger LOGGER = LoggerFactory.getLogger(AcknowledgementHelper.class);
    public AcknowledgementHelper() {
    }

    public String getAckCode(XmlMapper xmlMapper, String inicialContent) {

        Acknowledgement ackReturned = null;
        String correctedContent=increaseAckCodeWithSender(inicialContent);
        String AckCode = "ERROR", AckDetail = "unknown error"; // TODO: please replace with adequate default value for no ack received (network error?)
        try {
            ackReturned = (Acknowledgement) xmlMapper.fromXML(correctedContent);
            AckCode = ackReturned.getAckCode().toString();
            AckDetail = ackReturned.getAckDetail().toString();
        } catch (Exception e) {
            LOGGER.error("unable to evaluated the returned ACK : [" + correctedContent + "]", e);
        }
        return AckCode;
    }

    public String increaseAckCodeWithSender(String inicialContent) {
        String serviceSenderDescriptor = "<Sender>\n" +
                "        <ServiceID>cx.simlsa1-nodecx.vessel.push.provider</ServiceID>\n" +
                "        <ServiceOperation>Push</ServiceOperation>\n" +
                "</Sender>";

        int positionAckCodeTag = inicialContent.indexOf("<AckCode>");
        String correctedContent = inicialContent.substring(0, positionAckCodeTag)
                + serviceSenderDescriptor
                + inicialContent.substring(positionAckCodeTag);
        return correctedContent;
    }
}