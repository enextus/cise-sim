
package eu.cise.dispatcher.soap;

import eu.cise.servicemodel.v1.message.Acknowledgement;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for sendResponse complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="sendResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="return" type="{http://www.cise.eu/servicemodel/v1/message/}Acknowledgement" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "sendResponse", namespace = "http://www.cise.eu/accesspoint/service/v1/", propOrder = {
        "acknowledgement"
})
public class SendResponse {

    @XmlElement(name = "return")
    protected Acknowledgement acknowledgement;

    /**
     * Gets the value of the return property.
     *
     * @return possible object is
     * {@link Acknowledgement }
     */
    public Acknowledgement getReturn() {
        return acknowledgement;
    }

    /**
     * Sets the value of the return property.
     *
     * @param value allowed object is
     *              {@link Acknowledgement }
     */
    public void setReturn(Acknowledgement value) {
        this.acknowledgement = value;
    }

}
