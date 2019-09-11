package eu.europa.ec.jrc.marex.transport;


import eu.cise.servicemodel.v1.message.Acknowledgement;
import eu.cise.servicemodel.v1.message.Message;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

public class CiseMessageRestServiceClient {
    private URL wsdlURL;
    private String serviceURI;
    private String localPartURI;



    public CiseMessageRestServiceClient(String urlString, String uriString, String localPart) throws MalformedURLException, URISyntaxException {
        wsdlURL = new URL(urlString);
        serviceURI = uriString;
        localPartURI = localPart;
    }

    public Acknowledgement send(Message message) {
        Acknowledgement resultAcknowledgement = new Acknowledgement();
        try {

        } catch (Exception e) {

        }
        return resultAcknowledgement;
    }
}

