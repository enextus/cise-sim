package eu.europa.ec.jrc.marex.candidate;

import eu.cise.signature.exceptions.ExceptionHandler;
import io.dropwizard.configuration.ConfigurationException;

public class CiseEmulatorConfigurationException extends CiseEmulatorException {

    public CiseEmulatorConfigurationException(String message) {
        super(message);
    }

    public CiseEmulatorConfigurationException(String message, Throwable e) {
        super(message, e);
    }
}

