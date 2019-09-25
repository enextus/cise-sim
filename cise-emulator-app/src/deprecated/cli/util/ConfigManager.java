package eu.cise.emulator.deprecated.cli.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.cise.emulator.deprecated.cli.CiseEmulatorConfiguration;
import eu.cise.emulator.deprecated.cli.candidate.CiseEmulatorConfigurationException;
import io.dropwizard.configuration.*;
import io.dropwizard.setup.Bootstrap;

import javax.validation.ValidatorFactory;
import java.io.FileNotFoundException;
import java.io.IOException;

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

    private CiseEmulatorConfiguration parseConfiguration(String configpath) throws ConfigurationParsingException, FileNotFoundException {
        CiseEmulatorConfiguration ciseEmulatorConfiguration = null;


        ConfigurationFactory<CiseEmulatorConfiguration> configurationFactory = configurationFactoryFactory.create(CiseEmulatorConfiguration.class, validatorFactory.getValidator(), objectMapper, "dw");
        try {
            ciseEmulatorConfiguration = (configpath != null ? configurationFactory.build(sourceProvider, configpath) : configurationFactory.build());
        } catch (ConfigurationException e) {
            CiseEmulatorConfigurationException errorconfig = new CiseEmulatorConfigurationException("incorrect file content found in provided location : " +
                    configpath.substring(configpath.length() - 50 < 0 ? 0 : configpath.length() - 50), e);

        } catch (IOException e) {
            CiseEmulatorConfigurationException errorconfig = new CiseEmulatorConfigurationException("file not found in provided location : " + configpath, e);
        }

        return ciseEmulatorConfiguration;
    }
}

