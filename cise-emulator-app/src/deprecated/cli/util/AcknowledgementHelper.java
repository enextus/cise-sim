package eu.cise.emulator.deprecated.cli.util;

import eu.cise.servicemodel.v1.message.Acknowledgement;
import eu.eucise.xml.XmlMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AcknowledgementHelper {


    private static final Logger LOGGER = LoggerFactory.getLogger(AcknowledgementHelper.class);

    public AcknowledgementHelper() {
    }

    public static String increaseAckCodeWithSender(String inicialContent) {
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

    public String getAckCode(XmlMapper xmlMapper, String inicialContent) {

        Acknowledgement ackReturned = null;
        String correctedContent = increaseAckCodeWithSender(inicialContent);
        String ackCode = "ERROR", ackDetail = "unknown error"; // TODO: please replace with adequate default value for no ack received (network error?)
        try {
            ackReturned = (Acknowledgement) xmlMapper.fromXML(correctedContent);
            ackCode = ackReturned.getAckCode().toString();
            ackDetail = ackReturned.getAckDetail().toString();
        } catch (Exception e) {
            LOGGER.error("unable to evaluated the returned ACK : [" + correctedContent + "]", e);
        }
        return ackCode;
    }
}
