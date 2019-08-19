package eu.europa.ec.jrc.marex.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.europa.ec.jrc.marex.CiseEmulatorConfiguration;
import io.dropwizard.configuration.ConfigurationFactory;
import io.dropwizard.configuration.ConfigurationFactoryFactory;
import io.dropwizard.configuration.ConfigurationParsingException;
import io.dropwizard.configuration.ConfigurationSourceProvider;
import io.dropwizard.setup.Bootstrap;

import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.FileNotFoundException;

public class ConfigManager {


    private final ValidatorFactory validatorFactory;
    private final ConfigurationFactoryFactory configurationFactoryFactory;
    private final ConfigurationSourceProvider sourceProvider;
    private final ObjectMapper objectMapper;

    public ConfigManager(Bootstrap bootstrap) {
        configurationFactoryFactory = bootstrap.getConfigurationFactoryFactory();
        sourceProvider = bootstrap.getConfigurationSourceProvider();
        validatorFactory = bootstrap.getValidatorFactory();
        objectMapper = bootstrap.getObjectMapper();
    }

    public CiseEmulatorConfiguration readExistCiseEmulatorConfiguration(String configpath) throws FileNotFoundException, ConfigurationParsingException {
        CiseEmulatorConfiguration emulatorConfig = this.parseConfiguration(configpath);
        return emulatorConfig;
    }

    private CiseEmulatorConfiguration parseConfiguration(String configpath) throws ConfigurationParsingException{
            CiseEmulatorConfiguration ciseEmulatorConfiguration = null;
            ConfigurationFactory<CiseEmulatorConfiguration> configurationFactory = configurationFactoryFactory.create(CiseEmulatorConfiguration.class, validatorFactory.getValidator(), objectMapper, "dw");
            try {
                ciseEmulatorConfiguration = (configpath != null ? configurationFactory.build(sourceProvider, configpath) : configurationFactory.build());
            } catch (Exception e){
                assert (e instanceof ConfigurationParsingException);
                throw (ConfigurationParsingException)e;
            }
            return ciseEmulatorConfiguration;
        }
    }

