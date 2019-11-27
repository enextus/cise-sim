package eu.cise.emulator;

import eu.cise.dispatcher.DispatcherType;
import org.aeonbits.owner.Config;
import org.aeonbits.owner.Config.Sources;
import org.aeonbits.owner.Preprocessor;

/**
 * This file is containing the emulator application configuration
 */
@Config.PreprocessorClasses({EmuConfig.TrimAndInsureBoolean.class})
@Sources({"file:${conf.dir}sim.properties",
        "classpath:sim.properties"})
public interface EmuConfig extends Config {

    @Key("service.participantid")
    String participantId();

    @Key("destination.endpoint-url")
    String endpointUrl();

    @Key("signature.keystore.filename")
    String keyStoreFileName();

    @Key("signature.keystore.password")
    String keyStorePassword();

    @Key("signature.privatekey.alias")
    String privateKeyAlias();

    @Key("signature.privatekey.password")
    String privateKeyPassword();

    @Key("template.messages.directory")
    String messageTemplateDir();

    @Key("validation.rule.date")
    @DefaultValue("false")
    boolean isDateValidationEnabled();

    @Key("transport.mode")
    @DefaultValue("REST")
    DispatcherType dispatcherType();

    @Key("version")
    String version();

    class TrimAndInsureBoolean implements Preprocessor {
        public String process(String input) {
            if (input.trim().toUpperCase().equals("TRUE")) {
                return "true";
            } else if (input.trim().toUpperCase().equals("FALSE")) {
                return "false";
            } else {
                return input.trim();
            }
        }
    }
}
