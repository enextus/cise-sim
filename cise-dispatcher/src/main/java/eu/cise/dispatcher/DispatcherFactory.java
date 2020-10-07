package eu.cise.dispatcher;

import eu.cise.dispatcher.rest.RestDispatcher;
import eu.cise.dispatcher.soap.SoapDispatcher;
import eu.cise.sim.engine.Dispatcher;
import eu.cise.sim.engine.DispatcherType;

import eu.eucise.xml.XmlMapper;

public class DispatcherFactory {

    public Dispatcher getDispatcher(DispatcherType type, XmlMapper xmlMapper) {
        Dispatcher retval = null;
        switch (type) {
            case REST:
                retval = new RestDispatcher(xmlMapper);
                break;
            case SOAP:
                retval = new SoapDispatcher();
                break;
        }
        return retval;
    }
}
